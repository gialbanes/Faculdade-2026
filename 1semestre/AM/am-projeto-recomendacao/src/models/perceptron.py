class Perceptron:
    def __init__(self, weights=None, bias=0.1):
        default_weights = {'energy': 0.8, 'loudness': 0.2}
        self.weights = weights if weights is not None else default_weights
        self.bias = bias

    def _normalizar_loudness(self, loudness):
        """Normaliza loudness de dB (-60..0) para escala comparável."""
        return (loudness + 10) / 10

    def predict(self, energy, loudness):
        """
        Antes:  z = (energy * w1) + (loudness_norm * w2) + bias
        """

        z = np.dot(X, W) + bias

        loudness_norm = self._normalizar_loudness(loudness)

        # Monta o vetor de entradas
        X = np.array([energy, loudness_norm])

        # Produto escalar: substitui a soma manual
        z = np.dot(X, self.weights) + self.bias

        prediction = 1 if z >= 0.5 else 0

        return {
            "prediction": prediction,
            "activation": float(z),
            "normalized_loudness": float(loudness_norm),
        }