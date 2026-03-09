import numpy as np


class PerceptronNumpy:
    """
    Perceptron refatorado com NumPy (Semana 03).
    Mesma lógica, mas com vetores e produto escalar.
    """

    def __init__(self, weights=None, bias=0.1):
        # Pesos padrão: [energy, loudness]
        # Mesmos valores da semana anterior, agora como vetor NumPy
        default_weights = [0.8, 0.2]

        if weights is not None:
            self.weights = np.array(weights, dtype=float)
        else:
            self.weights = np.array(default_weights, dtype=float)

        self.bias = bias
            
    # Predição para VÁRIAS músicas de uma vez. Cada item é [energy, loudness]
    def predict_batch(self, lista_de_musicas):
        X = np.array(lista_de_musicas, dtype=float)

        # Normaliza a coluna de loudness (coluna 1)
        X[:, 1] = (X[:, 1] + 10) / 10

        # Multiplicação Matriz-Vetor: UMA linha faz o trabalho de N iterações!
        Z = np.dot(X, self.weights) + self.bias

        # Função de ativação vetorizada (sem loop!)
        predictions = (Z >= 0.5).astype(int)

        # Monta a lista de resultados
        return {
            "prediction": predictions,
            "activation": Z,
            "normalized_loudness": X[:, 1],
        }