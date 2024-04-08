import numpy as np
import sys
import pandas as pd
import matplotlib.pyplot as plt

class MLP:
    def __init__(self, input_size, hidden, output_size):
        self.train_data :pd.DataFrame= None
        self.train_labels :pd.DataFrame= None
        self.test_data:pd.DataFrame=None

        self.relevant_columns = ['TYPE', 'PRICE', 'BATH', 'PROPERTYSQFT', 'STATE', 'ADMINISTRATIVE_AREA_LEVEL_2',
                    'LOCALITY', 'SUBLOCALITY', 'STREET_NAME', 'LATITUDE', 'LONGITUDE']

        self.input_size = input_size # of features
        self.hidden = hidden# # neurons in hidden layer
        self.output_size = output_size
        self.layers = len(hidden) + 1 #[self.input_size, self.hidden,1]

        self.initialize_weights()
        # self.biases = self.initialize_biases(self.layers)

    def initialize_weights(self):
        self.weights = {}
        self.biases = {}
        
        # Initialize weights and biases for hidden layers
        layer_sizes = [self.input_size] + self.hidden + [self.output_size]
        for i in range(1, self.layers + 1):
            self.weights[f"W{i}"] = np.random.randn(layer_sizes[i], layer_sizes[i-1]) * np.sqrt(1.0 / layer_sizes[i-1])
            self.biases[f"b{i}"] = np.random.randn(layer_sizes[i], 1) * np.sqrt(1.0 / layer_sizes[i-1])
        # weights = {}
        # for i in range(1, len(layers)):
        #     input_size = layers[i-1]
        #     output_size = layers[i]
        #     weights[f"W{i}"] = np.random.randn(output_size, input_size) * np.sqrt(1.0/input_size)
        # return weights

    # def initialize_biases(self,layers):
    #         biases = {}
    #         for i in range(1, len(layers)):
    #             output_size = layers[i]
    #             biases[f"b{i}"] = np.random.randn(output_size, 1) * np.sqrt(1.0/self.input_size)
    #         return biases


    # define activation functions
    def relu(self,Z):
        return np.maximum(0, Z)

    def sigmoid(self,Z):
        return 1 / (1 + np.exp(-Z))

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
         # X: Input data (batch_size x input_size)
        activations = X.T  # Transpose X to match weight dimensions

        # List to store intermediate activations for each layer (including input)
        layer_activations = [activations]

        # Forward pass through each layer (hidden layers and output layer)
        for i in range(1, self.layers + 1):
            # Calculate z = W_i * a_{i-1} + b_i
            z = np.dot(self.weights[f"W{i}"], activations) + self.biases[f"b{i}"]

            # Apply activation function (ReLU for hidden layers, linear for output layer)
            if i == self.layers:
                activations = z  # Output layer (linear activation)
            else:
                activations = self.relu(z)  # Hidden layers (ReLU activation)

            # Store the activations for this layer
            layer_activations.append(activations)

        return activations, layer_activations

    def compute_loss(self, predictions, targets):
        # predictions: Output predictions (batch_size x output_size)
        # targets: True labels (batch_size x output_size)
        loss = np.mean((predictions - targets) ** 2)
        return loss
    
    def relu(self, x):
        return np.maximum(0, x)  # ReLU activation function
    
    def train(self, X_train, y_train, epochs, learning_rate, batch_size):
      for epoch in range(epochs):
            for batch_start in range(0, len(X_train), batch_size):
                # Get mini-batch
                X_batch = X_train[batch_start:batch_start+batch_size]
                y_batch = y_train[batch_start:batch_start+batch_size]

                # Forward pass
                predictions, layer_activations = self.forward_pass(X_batch)

                # Compute loss
                loss = self.compute_loss(predictions, y_batch)

                # Backpropagation
                gradients = self.backpropagation(predictions, y_batch, layer_activations)

                # Update weights and biases
                self.update_parameters(gradients, learning_rate)

            # Print training progress (optional)
            print(f"Epoch {epoch+1}/{epochs}, Loss: {loss}")
    
    def mean_squared_error(self, predictions, targets):
        return np.mean((predictions - targets) ** 2)
    
    def backpropagation(self, predictions, targets, layer_activations):
        # Compute gradients and update weights using backpropagation
        # Implement gradient computation and weight updates here
        
        batch_size = predictions.shape[0]
        gradients = {}

        # Compute gradient of loss with respect to output layer activations
        dLoss_dOutput = 2 * (predictions - targets) / batch_size

        # Backpropagate through each layer (from output to input)
        for i in range(self.layers, 0, -1):
            # Compute gradients for weights and biases of layer i
            activations_prev = layer_activations[i-1]

            # Gradient of loss with respect to weights of layer i
            gradients[f"dW{i}"] = np.dot(dLoss_dOutput, activations_prev.T)

            # Gradient of loss with respect to biases of layer i
            gradients[f"db{i}"] = np.sum(dLoss_dOutput, axis=1, keepdims=True)

            if i > 1:
                # Compute gradient of loss with respect to activations of previous layer
                dLoss_dActivations_prev = np.dot(self.weights[f"W{i}"].T, dLoss_dOutput)

                # Compute gradient of loss with respect to output of previous layer (before activation)
                dLoss_dZ = dLoss_dActivations_prev * self.relu_derivative(layer_activations[i-1])

                # Update dLoss_dOutput for next iteration (previous layer)
                dLoss_dOutput = dLoss_dZ

        return gradients
    
    def relu_derivative(self, x):
        # Derivative of ReLU activation function
        return np.where(x > 0, 1, 0)
    
    def update_parameters(self, gradients, learning_rate):
    # gradients: Dictionary of gradients computed during backpropagation
    # learning_rate: Learning rate for gradient descent

        for i in range(1, self.num_layers + 1):
            # Update weights and biases for each layer
            self.weights[f"W{i}"] -= learning_rate * gradients[f"dW{i}"]
            self.biases[f"b{i}"] -= learning_rate * gradients[f"db{i}"]


    def read_data(self, train=None, labels=None, test=None):
        # train_data
        self.train_data = pd.read_csv('data/train_data1.csv')
        self.train_label = pd.read_csv('data/train_label1.csv')
        self.test_data = pd.read_csv('data/test_data1.csv')
        
        # remove irrelevant columns
        cols = ['BROKERTITLE', 'MAIN_ADDRESS', 'FORMATTED_ADDRESS']
        filt_data =  self.train_data.drop(cols, axis=1)
        self.train_data = filt_data
        return self.train_data, self.train_label, self.test_data

if __name__ == "__main__":
    input_size = 13
    hidden = [20,10]
    output_size = 1

    epochs = 50
    learning_rate = 0.001
    batch_size = 32

    model = MLP(input_size, hidden, output_size)
    train_data, train_label, test_data = model.read_data()
    
    # losses = model.train(train_data, train_label, epochs, learning_rate, batch_size)
