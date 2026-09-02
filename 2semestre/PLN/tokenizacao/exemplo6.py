import nltk

avaliacao = ["O produto é excelente. Eu compraria de novo", "Chegou rápido o produto!"]
vocabulario = []

# essa etapa é importante no sistema de PLN (trabalhar com array e nao matriz) pois na matriz eu tenho que percorrer duas vezes, pq é um array dentro de outro array 
for texto in avaliacao:
    frases = nltk.sent_tokenize(texto)
    print(frases)
    for frase in frases:
        vocabulario.append(frase)

print(vocabulario)