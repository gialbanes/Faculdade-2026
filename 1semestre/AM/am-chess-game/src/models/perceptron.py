class PerceptronXadrez:
    def __init__(self, weights=None, bias=-0.0): 
        # White positivo (ajuda a vencer), Black negativo (atrapalha as brancas)
        default_weights = {'white_rating': 1.0, 'black_rating': -1.0}
        self.weights = weights if weights is not None else default_weights
        self.bias = bias

    def predict(self, white_rating, black_rating):
        # Normalização simples (Min-Max): 
        # Considerando que os ratings no dataset variam entre aprox. 800 e 2800
        w_norm = (white_rating - 800) / (2800 - 800)
        b_norm = (black_rating - 800) / (2800 - 800)

        # Cálculo Z 
        w_weight = self.weights.get('white_rating', 0.0)
        b_weight = self.weights.get('black_rating', 0.0)
        
        linear_output = (w_norm * w_weight) + (b_norm * b_weight) + self.bias

        # Ativação (Função Degrau)
        # Se o resultado for positivo, as Brancas têm vantagem matemática
        prediction = 1 if linear_output >= 0 else 0

        return {
            "prediction": "White Wins" if prediction == 1 else "Black Wins",
            "activation_value": linear_output,
            "inputs_normalized": {"white": w_norm, "black": b_norm}
        }