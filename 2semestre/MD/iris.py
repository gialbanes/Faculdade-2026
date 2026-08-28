# Dataset Iris
# Utilizado para demonstrar, passo a passo como um algoritmo de Machine Learning pode aprender padrões e classificar flores de 2 espécies 

import pandas as pd
import matplotlib.pyplot as plt 
from sklearn.datasets import load_iris
from sklearn.tree import DecisionTreeClassifier
from sklearn.tree import plot_tree

# métricas utilizadas para avaliar o modelo 
from sklearn.metrics import accuracy_score, confusion_matrix, ConfusionMatrixDisplay, classification_report

# carregamento do dataset 
iris = load_iris()
print('\n')
print('='*70)
print('Dataset Iris')
print('='*70)
print('\nDataset carregado com sucesso!')

# conhecendo o dataset
print('\nQuantidade de registros:')
print(len(iris.data))

# nomes das características
print('\nNomes das características utilizadas para analisar cada flor:')
for caracteristicas in iris.feature_names:
    print("=", caracteristicas)

# nome das espécies 
print('\nEspécies existentes:')
for especie in iris.target_names:
    print("=", especie)

# visualizando uma flor 
print('\n')
print('='*70)
print('Exemplo uma flor')
print('='*70)

print('\nMedidas da primeira flor:')
print("Comprimento da sépala:", iris.data[0][0], "cm")
print("Largura da sépala:", iris.data[0][1], "cm")
print("Comprimento da pétala:", iris.data[0][2], "cm")
print("Largura da pétala:", iris.data[0][3], "cm")

# código da espécie 
codigo_especie = iris.target[0]
print('\nCódigo da espécie:', codigo_especie)

# traduzindo o código para o nome da espécie
print('\nNome da espécie:', iris.target_names[codigo_especie])

# transformando dados em tabelas 
print('\n')
print('='*70)
print("Tranformação dos dados em tabela")
print('='*70)

# criar um dataframe usando os dados do daraset 
dados = pd.DataFrame(
    iris.data, 
    columns=[
        "Comprimento_Sepala",
        "Largura_Sepala",
        "Comprimento_Petala",
        "Largura_Petala"
    ]
)

# adicionar a coluna 'especie' ao dataframe

dados["codigo especie"] = iris.target
dados["nome especie"] = dados["codigo especie"].map({
    0: 'setosa',
    1: 'versicolor',
    2: 'virginica'
})

print(dados.head(10))

# mostrar os primeiros registros 
print('\nPrimeiros registros:')
print(dados.head())

# tamanho do dataset 
print('\n')
print('='*70)
print("Tamanho do dataset")
print('='*70)

print("\nQuantidade de linhas:", dados.shape[0])
print("\nQuantidade de colunas:", dados.shape[1])

# quantidade de flores por espécie 
print('\n')
print('='*70)
print("Quantidade de flores por espécie")
print('='*70)

quantidade_especie = dados["Nome_Especie"].value_counts()
print("\nQuantidade de flores por espécie são: ")
print(quantidade_especie)

# gráfico - quantidade de flores 
quantidade_especie.plot(kind="bar")
plt.title("Quantidade de flores por espécie")
plt.xlabel("Espécies")
plt.ylabel("Quantidade de flores")
plt.xticks(rotation=0)
plt.tight_layout()
plt.show
