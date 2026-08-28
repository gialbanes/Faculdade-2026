import pandas as pd

dados = {
    'nome':['Ana','Bruno','Carla','Diego','Eva'],
    'idade':[28,35,41,22,30],
    'salario':[4500,7800,9200,3100,5600],
    'departamento':['TI','Vendas','TI','Marketing','Vendas']
}

df = pd.DataFrame(dados)

# Ver o DataFrame Completo
print(df)

# Ver as primeiras linhas
print(df.head())

# Estatística básica das colunas
print(df.describe())

# Filtro por condição
print(df[df['idade']>30])