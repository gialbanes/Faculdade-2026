import nltk

texto = "No meio do caminho tinha uma pedra tinha uma pedra no meio do caminho tinha uma pedra no meio do caminho tinha uma pedra. " \
"Nunca me esquecerei desse acontecimento na vida de minhas retinas tão fatigadas. Nunca me esquecerei que no meio do caminho tinha uma pedra " \
"tinha uma pedra no meio do caminho no meio do caminho tinha uma pedra."

palavras = []

# word_tokenize é uma função do nltk que separa as palavras do texto, transformando em um array de palavras
palavras = nltk.word_tokenize(texto.lower())

# lematização é o processo de reduzir uma palavra à sua forma base ou raiz, conhecida como lema. Por exemplo, as palavras "correr", "correu" e "correndo" têm o mesmo lema "correr". 
# WordNetLemmatizer é uma classe do NLTK que fornece métodos para lematizar palavras em inglês. Ele utiliza o WordNet, um banco de dados lexical que organiza palavras em conjuntos de 
# sinônimos (synsets) e fornece informações sobre suas relações semânticas.
lematizador = nltk.stem.WordNetLemmatizer()

lemas = []

for palavra in palavras:
    lemas.append(lematizador.lemmatize(palavra)) # .lemmatize() é o método que realiza a lematização da palavra, retornando o lema correspondente.

print(lemas)