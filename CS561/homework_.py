import numpy as np
import sys
import pandas as pd
import matplotlib.pyplot as plt
import warnings
warnings.filterwarnings("ignore", category=RuntimeWarning)
import time
import math
from sklearn.preprocessing import LabelEncoder

class MLP:
    def __init__(self, input_size, h1, h2, output_size, enc_size, epochs=1000, learning_rate=0.1,batch_size = 32):
        self.batch_size = batch_size
        self.learning_rate = learning_rate
        self.num_epochs = epochs
        
        # h1 = 256
        # h2 = 128
        self.wb = {
            'w1':np.random.randn(h1, input_size) * np.sqrt(2.0 / input_size),
            'b1':np.random.randn(h1,1) ,
            'w2':np.random.randn(h2, h1) * np.sqrt(2.0 / h1),
            'b2':np.random.randn(h2,output_size) ,
            'w3':np.random.randn(enc_size, h2) * np.sqrt(2.0 / h2),
            'b3':np.random.randn(enc_size,output_size)
        }

        self.train_acc = []
        self.train_loss = []

    def sigmoid(self,x):
        return 1.0/(1.0 + np.exp(-x))
    
    def sigmoid_backward(self, dA, Z):
        sig = self.sigmoid(Z)
        return dA * sig * (1 - sig)
    
    def softmax(self, x):
        exps = np.exp(x - x.max())
        return exps / np.sum(exps, axis=0)
    
    def cross_entropy_loss(self, Y, out):
        m = Y.shape[1]
        cost = (-1 / m) * np.sum(np.multiply(Y, np.log(out)) + np.multiply(1 - Y, np.log(1 - out)))
        cost = np.squeeze(cost)
        return cost
    
    def accuracy(self, X, y):
        predictions = []

        cache = self.forward_pass(X)
        output = cache['a3']
        pred = np.argmax(output, axis=0)
        predictions.append(pred == np.argmax(y, axis=0))
        
        return np.mean(predictions), predictions
        

    def forward_pass(self, X):
        cache = dict()
        
        cache['z1'] = np.dot(self.wb['w1'], X) + self.wb['b1']
        cache['a1'] = self.sigmoid(cache['z1'])
        cache['z2'] = np.dot(self.wb['w2'], cache['a1']) + self.wb['b2']
        cache['a2'] = self.sigmoid(cache['z2'])
        cache['z3'] = np.dot(self.wb['w3'], cache['a2']) + self.wb['b3']
        cache['a3'] = self.softmax(cache['z3'])
        
        return cache
    
    def back_prop(self, X, y, cache):
        m = X.shape[1]
        dz3 = cache['a3'] - y
        
        m3 = cache["a2"].shape[1]
        dw3 = np.dot(dz3, cache["a2"].T) / m
        db3 = np.sum(dz3, axis=1, keepdims=True) / m
        
        da2 = np.dot(self.wb['w3'].T, dz3)
        dz2 = self.sigmoid_backward(da2, cache['z2'])
        
        m2 = cache['a1'].shape[1]
        dw2 = np.dot(dz2, cache['a1'].T) / m
        db2 = np.sum(dz2, axis=1, keepdims=True) / m
        
        da1 = np.dot(self.wb['w2'].T, dz2)
        dz1 = self.sigmoid_backward(da1, cache['z1'])
        
        # gradients of first layer
        m1 = X.shape[1]
        dW1 = np.dot(dz1, X.T) / m
        db1 = np.sum(dz1, axis=1, keepdims=True) / m
    
        gradients = {'dw3':dw3, 'db3':db3, 'dw2':dw2, 'db2':db2, 'dw1':dW1, 'db1':db1}
        
        return gradients

# gradient checking? compute backward pass output, pertrube weights
    def get_mini_batches(self, X, y, batch_size):
        m = X.shape[1]
        mini_batches = list()
        num_batches = math.floor(m/batch_size)
        for i in range(0, num_batches):
            mb_X = X[:, i * batch_size : (i+1) * batch_size]
            mb_y = y[:, i * batch_size : (i+1) * batch_size]
            mini_batch = (mb_X, mb_y)
            mini_batches.append(mini_batch)

        # end case
        if m % batch_size != 0:
            mb_X = X[:, batch_size * math.floor(m / batch_size) : m]
            mb_y = y[:, batch_size * math.floor(m / batch_size) : m]
            mini_batch = (mb_X, mb_y)
            mini_batches.append(mini_batch)

        return mini_batches
    
    def train(self, X, Y):
        
        for epoch in range(self.num_epochs):
            # shuffle data
            random = np.arange(len(X[1]))
            np.random.shuffle(random)
            X_shuffle = X[:, random]
            Y_shuffle = Y[:, random]
            mini_batches = self.get_mini_batches(X_shuffle, Y_shuffle, self.batch_size)
            x_acc = 0
            x_loss = 0
            for batch in mini_batches:
                x_batch, y_batch = batch
                cache = self.forward_pass(x_batch)
                grads = self.back_prop(x_batch, y_batch, cache) 
                
                # update parameters
                self.wb['w1'] = self.wb['w1'] - (self.learning_rate * grads['dw1'])
                self.wb['b1'] = self.wb['b1'] - (self.learning_rate * grads['db1'])
                self.wb['w2'] = self.wb['w2'] - (self.learning_rate * grads['dw2'])
                self.wb['b2'] = self.wb['b2'] - (self.learning_rate * grads['db2'])
                self.wb['w3'] = self.wb['w3'] - (self.learning_rate * grads['dw3'])
                self.wb['b3'] = self.wb['b3'] - (self.learning_rate * grads['db3'])

            train_accuracy, _= self.accuracy(X, Y)
            train_loss = self.compute_loss(X, Y)  # Calculate training loss

            self.train_acc.append(train_accuracy)
            self.train_loss.append(train_loss)
            print(f'Epoch {epoch}: Training accuracy:  {train_accuracy}, Train loss: {train_loss}')
    
    def compute_loss(self, X, Y):
        # Forward pass to compute predictions
        cache = self.forward_pass(X)
        A3 = cache['a3']  # Final layer activation (predictions)
        
        # Compute Mean Squared Error (MSE) loss
        m = X.shape[1]  # Number of training examples
        loss = np.mean(np.square(A3 - Y))  # MSE loss
        return loss

def read_data(x=None, y=None, test=None, y_test=None, cmd_arg = False):
        category_mapping = {
                        'Condo for sale': 0,
                        'House for sale': 1,
                        'Co-op for sale': 2,
                        'Townhouse for sale': 3,
                        'Multi-family home for sale': 4,
                        'For sale': 5,
                        'Land for sale': 6,
                        'Foreclosure': 7,
                        'Pending': 8,
                        'Contingent': 9,
                        'Coming Soon': 10,
                        'Mobile house for sale': 11
        }
        if cmd_arg:
            train_data = pd.read_csv(x)
            train_label = pd.read_csv(y)
            test_data = pd.read_csv(test)
            test_label = pd.read_csv(y_test)
        
        else:
            train_data = pd.read_csv('data/train_data5.csv')
            train_label = pd.read_csv('data/train_label5.csv')
            test_data = pd.read_csv('data/test_data5.csv')
            test_label =  None#pd.read_csv('data/test_label5.csv')
        
        # filt_data =  self.train_data.drop(cols, axis=1)
        min_price = train_data['PRICE'].min()
        max_price = train_data['PRICE'].max()
        test_max = test_data['PRICE'].max()
        test_min = test_data['PRICE'].min()
        train_data['PRICE'] = (train_data['PRICE']) / (max_price) 
        test_data['PRICE'] = (test_data['PRICE'])/(test_max)

        # train_data['PROPERTYSQFT'] = (train_data['PROPERTYSQFT']) / (train_data['PROPERTYSQFT'].max()) 
        # test_data['PROPERTYSQFT'] = (test_data['PROPERTYSQFT'])/(test_data['PROPERTYSQFT'].max())

        # train_data['BATH'] = (train_data['BATH']) / (train_data['BATH'].max()) 
        # test_data['BATH'] = (test_data['BATH'])/(train_data['BATH'].max())

        train_data['TYPE_encoded'] = train_data['TYPE'].map(category_mapping)
        test_data['TYPE_encoded'] = test_data['TYPE'].map(category_mapping)

        # encode sublocality
        label_encoder = LabelEncoder()
        train_data['SUBLOCALITY_encoded'] = label_encoder.fit_transform(train_data['SUBLOCALITY'])
        train_data['SUBLOCALITY_encoded'].unique()

        test_data['SUBLOCALITY_encoded'] = label_encoder.fit_transform(test_data['SUBLOCALITY'])
        test_data['SUBLOCALITY_encoded'].unique()

        train_data['LOCALITY_encoded'] = label_encoder.fit_transform(train_data['LOCALITY'])
        train_data['LOCALITY_encoded'].unique()

        test_data['LOCALITY_encoded'] = label_encoder.fit_transform(test_data['LOCALITY'])
        test_data['LOCALITY_encoded'].unique()

        train_data['PRICE_X2'] = np.power(train_data['PRICE'], 2)
        train_data['PRICE_X2'] = train_data['PRICE_X2']/train_data["PRICE_X2"].max()

        test_data['PRICE_X2'] = np.power(test_data['PRICE'], 2)
        test_data['PRICE_X2'] = test_data['PRICE_X2']/test_data["PRICE_X2"].max()

        # self.train_data = filt_data
        return train_data, train_label, test_data, test_label

if __name__ == "__main__":
    read_cmd_arg = False
    x = None
    y = None
    x_test = None
    y_test = None
   
    if len(sys.argv) > 1:
        x = sys.argv[1]
        y = sys.argv[2]
        x_test = sys.argv[3]
        y_test = sys.argv[4]

    # model paramters
    input_size = 9
    h1 = 512
    h2 = 256
    output_size = 1

    epochs = 100
    learning_rate = 0.001
    batch_size = 32
    

    train_data, train_label, test_data, test_label  = read_data(x, y, x_test, y_test, read_cmd_arg)

    columns_2_drop = ['BROKERTITLE', 'TYPE', 'ADDRESS','STATE', 'MAIN_ADDRESS',
                      'ADMINISTRATIVE_AREA_LEVEL_2', 'LOCALITY','SUBLOCALITY', 
                      'STREET_NAME', 'LONG_NAME', 'FORMATTED_ADDRESS']
    
    x_train = train_data.drop(columns_2_drop, axis=1)
    X = x_train.T.values
    Y = train_label.T.values
    X_test = test_data.drop(columns_2_drop, axis=1).T.values
    
    # exit(1)
    

    Y_enc = np.zeros((Y.size, Y.max()+1))
    Y_enc[np.arange(Y.size), Y] = 1
    Y_enc = Y_enc.T

    model = MLP(input_size, h1, h2, output_size, Y_enc.shape[0], epochs, learning_rate, batch_size)
    model.train(X, Y_enc)

    test_label =  pd.read_csv('data/test_label5.csv')
    Y_test = test_label.values.T
    
    cache = model.forward_pass(X_test)
    output = cache['a3']
    pred = np.argmax(output, axis=0)

    pred_df = pd.DataFrame(pred)
    pred_df.columns = ['BEDS']
    pred_df.to_csv('output.csv', header=True, index=None)

    Yt_enc = np.zeros((Y_test.size, Y_test.max()+1))
    Yt_enc[np.arange(Y_test.size), Y_test] = 1
    Yt_enc = Yt_enc.T
    
    test_acc, pred_list = model.accuracy(X_test, Yt_enc)
    print(test_acc)
    # print(pred_list)

    # epochs = list(range(1, len(model.train_acc) + 1))
    # # Plot training accuracy vs. epoch
    # plt.figure(figsize=(8, 6))  # Set the figure size (width, height)
    # # plt.plot(epochs, model.train_acc,model.train_loss,  marker='o', linestyle='-', color='b', label='Training Accuracy')  # Plot training accuracy
    # plt.plot(epochs, model.train_acc, marker='o', linestyle='-', color='b', label='Training Accuracy')
    # plt.plot(epochs, model.train_loss, marker='x', linestyle='--', color='r', label='Training Loss')

    # plt.title('Training Accuracy, Loss vs. Epoch')  # Set the title of the plot
    # plt.xlabel('Epoch')  # Set the label for the x-axis
    # plt.ylabel('Value')  # Set the label for the y-axis
    # plt.xticks(epochs)  # Set the x-axis ticks to display epoch numbers
    # plt.grid(True)  # Display gridlines on the plot
    # plt.legend()  # Display legend
    # plt.show()  # Show the plot