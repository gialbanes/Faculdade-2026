import nltk

avaliacao = ["O produto é excelente. Eu compraria de novo", "Chegou rápido o produto!"]
vocabulario = []
vocabulario_sem_repetidas = []

# essa etapa é importante no sistema de PLN (trabalhar com array e nao matriz) pois na matriz eu tenho que percorrer duas vezes, pq é um array dentro de outro array 
for texto in avaliacao:
    palavras = nltk.word_tokenize(texto)
    print(palavras)
    for palavra in palavras:
        vocabulario.append(palavra)

print(vocabulario)
print(len(vocabulario))

# método set remove os termos repetidos 
vocabulario_sem_repetidas = set(vocabulario)
print(vocabulario_sem_repetidas)
print(len(vocabulario_sem_repetidas))