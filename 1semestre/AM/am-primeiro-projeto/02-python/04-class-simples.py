class CarrinhoDeCompras:
    def __init__(self):
        self.itens = {}

    def adcionar_item(self, item, preco):
        self.itens[item] = preco
        print(f"{item} => R${preco:.2f} adicionado ao carrinho ")

    def calcular_total(self):
        total = sum(self.itens.values())
        return total
    
# como usar a classe
if __name__ == "__main__":
    print("Bem vindo à Amazon")
    print("-" * 50)

    # criação do carrinho 
    meu_carrinho = CarrinhoDeCompras()

    # adicionando item 
    meu_carrinho.adcionar_item("Notebook", 1500)
    meu_carrinho.adcionar_item("Mouse", 50)

    # total do carrinho 
    print("-" * 50)
    valor_total = meu_carrinho.calcular_total()
    print(f"O valor total da compra é: R${valor_total:.2f}")