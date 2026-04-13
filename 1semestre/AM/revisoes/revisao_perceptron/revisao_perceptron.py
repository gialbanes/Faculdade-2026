class PerceptronSimples:
    def __init__(self, peso_renda=0.3, peso_historico=0.8, bias=-7.0):
        # Perceptron simples para avaliar se um cliente é bom ou mau pagador, baseado em renda mensal e histórico de pagamento
        self.peso_renda = peso_renda
        self.peso_historico = peso_historico
        self.bias = bias 

    def prever(self, renda, historico):
        # Equação de decisão
        z = (renda * self.peso_renda) + (historico * self.peso_historico) + self.bias 

        # Função de ativação
        if z >= 0:
            return "Aprovado"
        else: 
            return "Reprovado"

