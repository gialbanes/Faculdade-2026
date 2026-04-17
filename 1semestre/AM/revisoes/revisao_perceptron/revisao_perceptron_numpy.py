import numpy as np

class PerceptronNumpy:
    def __init__(self, pesos, bias):
        self.weights = np.array(pesos)
        self.bias = bias 

    def predict(self, matriz_clientes):
        # processa todos os clientes com np.dot 
        z = np.dot(matriz_clientes, self.weights) + self.bias
        # o retorno é true ou false, e o astype converte para 1 ou 0
        return (z >= 0).astype(int)


