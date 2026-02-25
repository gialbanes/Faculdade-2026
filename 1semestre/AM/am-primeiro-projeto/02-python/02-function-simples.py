# identação é fundamental, ela define o escopo do código
def add_item(carrinho, item, preco):
    carrinho[item] = preco
    print(f"{item} => {preco} adicionado ao carrinho")

    if (preco > 100):
        print("Cupom de desconto disponível")

meu_carrinho = {}
add_item(meu_carrinho, "Notebook", 1500)
add_item(meu_carrinho, "Mouse", 50)
print(meu_carrinho)
