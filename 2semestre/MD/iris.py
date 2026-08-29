import pandas as pd
import matplotlib.pyplot as plt 
from sklearn.datasets import load_iris
from sklearn.tree import DecisionTreeClassifier
from sklearn.tree import plot_tree

# métricas utilizadas para avaliar o modelo 
from sklearn.metrics import accuracy_score, confusion_matrix, ConfusionMatrixDisplay, classification_report
from sklearn.model_selection import train_test_split

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
print("Transformação dos dados em tabela")
print('='*70)

# criar um dataframe usando os dados do dataset 
dados = pd.DataFrame(
    iris.data, 
    columns=[
        "Comprimento_Sepala",
        "Largura_Sepala",
        "Comprimento_Petala",
        "Largura_Petala"
    ]
)

# adicionar as colunas ao dataframe com nomes padronizados
dados["codigo_especie"] = iris.target
dados["nome_especie"] = dados["codigo_especie"].map({
    0: 'setosa',
    1: 'versicolor',
    2: 'virginica'
})

# mostrar os primeiros registros 
print('\nPrimeiros registros:')
print(dados.head())

# tamanho do dataset 
print('\n')
print('='*70)
print("Tamanho do dataset")
print('='*70)

print("\nQuantidade de linhas:", dados.shape[0])
print("Quantidade de colunas:", dados.shape[1])

# quantidade de flores por espécie 
print('\n')
print('='*70)
print("Quantidade de flores por espécie")
print('='*70)

quantidade_especie = dados["nome_especie"].value_counts()
print("\nQuantidade de flores por espécie são: ")
print(quantidade_especie)

# gráfico - quantidade de flores 
quantidade_especie.plot(kind="bar")
plt.title("Quantidade de flores por espécie")
plt.xlabel("Espécies")
plt.ylabel("Quantidade de flores")
plt.xticks(rotation=0)
plt.tight_layout()
plt.show()

# estatística descritiva 
print('\n')
print('='*70)
print("Estatística descritiva")
print('='*70)

print("\nA tabela abaixo irá mostrar valores da média, mínimo, máximo, desvio padrão e outras informações estatísticas\n")

print(
    dados[[
        "codigo_especie",
        "Comprimento_Sepala",
        "Largura_Sepala",
        "Comprimento_Petala",
        "Largura_Petala"
    ]].describe()
)

# média das medidas por espécie
print('\n')
print('='*70)
print("Média das medidas por espécie")
print('='*70)

medias = dados.groupby("nome_especie")[[
        "Comprimento_Sepala",
        "Largura_Sepala",
        "Comprimento_Petala",
        "Largura_Petala"
]].mean()

print("\n")
print(medias)

# visualização dos dados
print('\n')
print('='*70)
print("Visualização dos dados")
print('='*70)

print("\nMostrar gráfico se existem grupos diferentes por espécie")

# percorrer cada espécie 
for especie in dados["nome_especie"].unique():

    # filtrar apenas registros da especie atual
    dados_especie = dados[
        dados["nome_especie"] == especie
    ]

    # adicionar os pontos do gráfico 
    plt.scatter(
        dados_especie["Comprimento_Petala"],
        dados_especie["Largura_Petala"],
        label=especie
    )

    # configuração do gráfico 
    plt.title("Relação entre comprimento e largura")
    plt.xlabel("Comprimento da pétala (cm)")
    plt.ylabel("Largura da pétala (cm)")
    plt.legend()
    plt.grid()
    plt.show()

# definindo variaveis X e Y 
print("\n")
print('='*70)
print("Definindo entradas e respostas")
print('='*70)

# X contem as características utilizadas pelo algoritmo para realizar o aprendizado 
x = dados[[
    "Comprimento_Sepala",
    "Largura_Sepala",
    "Comprimento_Petala",
    "Largura_Petala"
]]

# Y contem a resposta correta retornando a espécie da flor 
y = dados["codigo_especie"]
print("\nX representa os dados utilizados como ENTRADA")

print(
    "\nComprimento_Sepala", 
    "\nLargura_Sepala",
    "\nComprimento_Petala",
    "\nLargura_Petala"
)

print("\nY representa a resposta que queremos prever")
print("\n Y = espécie da flor")

# separando treinamento e teste
print("\n")
print('='*70)
print("Separando treinamento e teste")
print('='*70)

# iremos utilizar 70% dos dados para treinamento e 30% para testes 
x_treino, x_teste, y_treino, y_teste = train_test_split(
    x,
    y,
    test_size=0.30,
    random_state=42,
    stratify=y
)

