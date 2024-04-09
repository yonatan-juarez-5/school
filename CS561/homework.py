import numpy as np
import sys
import pandas as pd
import matplotlib.pyplot as plt
import warnings
warnings.filterwarnings("ignore", category=RuntimeWarning)

class MLP:
    def __init__(self, input_size, hidden, output_size, epochs=1000, learning_rate=0.1,batch_size = 32):
        self.train_data :pd.DataFrame= None
        self.train_labels :pd.DataFrame= None
        self.test_data:pd.DataFrame=None

        self.relevant_columns = ['TYPE', 'PRICE', 'BATH', 'PROPERTYSQFT', 'STATE', 'ADMINISTRATIVE_AREA_LEVEL_2',
                    'LOCALITY', 'SUBLOCALITY', 'STREET_NAME', 'LATITUDE', 'LONGITUDE']

        self.input_size = input_size
        self.hidden_size = hidden
        self.output_size = output_size
        self.learning_rate = learning_rate
        self.num_epochs = epochs
        self.batch_size = batch_size

        self.train_losses = []
        self.test_losses = []
        self.train_accuracies = []
        self.test_accuracies = []

    # Initialize weights and biases for the network
        self.weights1 = np.random.randn(self.input_size, self.hidden_size)
        self.bias1 = np.zeros((1, self.hidden_size))
        self.weights2 = np.random.randn(self.hidden_size, self.output_size)
        self.bias2 = np.zeros((1, self.output_size))

    # define activation functions
    def relu(self,Z):
        return np.maximum(0, Z)

    def sigmoid(self,Z):
        return 1 / (1 + np.exp(-Z))
    
    def sigmoid_derivative(self, x):
        return x * (1 - x)

    # Initialize Parameters

    def linear(self, Z):
        return Z

    def initialize_parameters(self, layer_dims):
        parameters = {}
        L = len(layer_dims) - 1  # Number of layers excluding input layer

        for l in range(1, L + 1):
            parameters[f"W{l}"] = np.random.randn(layer_dims[l], layer_dims[l - 1]) * 0.01
            parameters[f"b{l}"] = np.zeros((layer_dims[l], 1))

        return parameters

    def forward_pass(self, X):
        # Forward pass through the network
        self.hidden_output = self.sigmoid(np.dot(X, self.weights1) + self.bias1)
        self.output = self.sigmoid(np.dot(self.hidden_output, self.weights2) + self.bias2)
        return self.output
    
    def backward_pass(self, X, y):
        # Backward pass and update weights and biases
        error = y - self.output
        delta_output = error * self.sigmoid_derivative(self.output)
        
        error_hidden = delta_output.dot(self.weights2.T)
        delta_hidden = error_hidden * self.sigmoid_derivative(self.hidden_output)
        
        # Update weights and biases
        self.weights2 += self.hidden_output.T.dot(delta_output) * self.learning_rate
        # self.bias2 += np.sum(delta_output, axis=0, keepdims=True) * self.learning_rate
        self.bias2 += delta_output.sum(axis=0).values.reshape(1, -1) * self.learning_rate
        self.weights1 += X.T.dot(delta_hidden) * self.learning_rate
        # self.bias1 += np.sum(delta_hidden, axis=0, keepdims=True) * self.learning_rate
        self.bias1 += delta_hidden.sum(axis=0).values.reshape(1, -1) * self.learning_rate


    def train(self, X_train, y_train, X_test, y_test):
        num_samples_train = X_train.shape[0]
        num_samples_test = X_test.shape[0]
        
        for epoch in range(self.num_epochs):
            # Shuffle the indices of the training dataset
            indices_train = np.random.permutation(num_samples_train)

            # Split data into mini-batches for training
            for i in range(0, num_samples_train, self.batch_size):
                X_batch_train = X_train.iloc[indices_train[i:i+self.batch_size]]
                y_batch_train = y_train.iloc[indices_train[i:i+self.batch_size]]

                # Forward pass and backward pass for the mini-batch
                self.forward_pass(X_batch_train)
                self.backward_pass(X_batch_train, y_batch_train)

            # Calculate train accuracy and loss
            train_predictions = self.predict(X_train)
            train_loss = np.mean(np.square(y_train - train_predictions))
            train_accuracy = np.mean(np.round(train_predictions) == y_train)
            self.train_losses.append(train_loss)
            self.train_accuracies.append(train_accuracy)

            # Calculate test accuracy and loss
            test_predictions = self.predict(X_test)
            test_loss = np.mean(np.square(y_test - test_predictions))
            test_accuracy = np.mean(np.round(test_predictions) == y_test)
            self.test_losses.append(test_loss)
            self.test_accuracies.append(test_accuracy)

            # Print and optionally plot results after each epoch
            print(f"Epoch {epoch + 1}: Train Loss = {train_loss:.4f}, Train Accuracy = {train_accuracy:.4f}, "
                f"Test Loss = {test_loss:.4f}, Test Accuracy = {test_accuracy:.4f}")


    def predict(self, X):
        # Perform prediction using the trained model
        return np.round(self.forward_pass(X))
    
    def read_data(self, train=None, labels=None, test=None):
        # train_data
        self.train_data = pd.read_csv('data/train_data1.csv')
        self.train_label = pd.read_csv('data/train_label1.csv')
        self.test_data = pd.read_csv('data/test_data1.csv')
        self.test_label = pd.read_csv('data/test_label1.csv')
        
        # remove irrelevant columns
        # cols = ['BROKERTITLE', 'MAIN_ADDRESS', 'FORMATTED_ADDRESS']
        # filt_data =  self.train_data.drop(cols, axis=1)
        # min_price = self.train_data['PRICE'].min()
        # max_price = self.train_data['PRICE'].max()
        # test_max = self.test_data['PRICE'].max()
        # test_min = self.test_data['PRICE'].min()
        # self.train_data['PRICE'] = (self.train_data['PRICE'] - min_price) / (max_price - min_price) 
        # self.test_data['PRICE'] = (self.test_data['PRICE'] - test_min)/(test_max-test_min)
        # self.train_data = filt_data
        return self.train_data, self.train_label, self.test_data, self.test_label

if __name__ == "__main__":
    input_size = 5
    hidden = 10
    output_size = 1

    epochs = 100
    learning_rate = 0.01
    batch_size = 32

    model = MLP(input_size, hidden, output_size, epochs, learning_rate, batch_size)
    train_data, train_label, test_data, test_label  = model.read_data()
    # print(train_data['PRICE'].head())
    columns_2_drop = ['BROKERTITLE', 'TYPE', 'ADDRESS',
       'STATE', 'MAIN_ADDRESS', 'ADMINISTRATIVE_AREA_LEVEL_2', 'LOCALITY',
       'SUBLOCALITY', 'STREET_NAME', 'LONG_NAME', 'FORMATTED_ADDRESS',
       ]

    x_train = train_data.drop(columns_2_drop, axis=1)
    x_test = test_data.drop(columns_2_drop, axis=1)
    print(x_train.shape, x_test.shape, train_label.shape, test_label.shape)
    # normalized PRICE feature
    model.train(x_train, train_label, x_test, test_label)

#     # Make predictions on the training data
#     y_pred = model.predict(test_data)
   
#    # Plot train and test accuracy over epochs
    plt.figure(figsize=(10, 6))
    plt.plot(np.arange(1, epochs+1), model.train_accuracies, label='Train Accuracy')
    plt.plot(np.arange(1, epochs+1), model.test_accuracies, label='Test Accuracy')
    plt.xlabel('Epoch')
    plt.ylabel('Accuracy')
    plt.title('Train and Test Accuracy over Epochs')
    plt.legend()
    plt.grid(True)
    plt.show()