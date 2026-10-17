import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.metrics import accuracy_score, confusion_matrix


# 1. carregar o arquivo CSV
df = pd.read_csv("desempenho_estudantes.csv")
print('\nPrimeiros registros:')
print(df.head())
print('\nTamanho da base: ', df.shape)
print('\nValores ausentes: ')
print(df.isnull().sum())

# 2. selecionar as variáveis úteis para a mineração 
variaveis = ["horas_estudo_semana", "frequencia_percentual", "atividades_entregues", "media_exercicios", "participacao_aulas", "acesso_plataforma_semana", "falta_mes"]

x = df[variaveis].copy()
y = df["situacao_final"]

# entradas (x) -> hora, frequência, atividades, média, participação, acesso e faltas 
# algoritmo de classificação -> Decision Tree
# saídas (y) -> desempenho_satisfatorio ou precisa_atencao 

# 3. separar os dados para o treinamento e teste 
X_treino, X_teste, y_treino, y_teste = train_test_split(x, y, test_size=0.25, random_state=42, stratify=y)

# 180 registros 
# 25% para teste -> 45 registros -> são registros não usados no treinamento 
# 75% para aprendizagem -> 135 registros -> são registros usados no treinamento
# modelo: aprender padrões
# treinamento: aprender com exemplos anteriores 
# teste: verificar se o aprendizadofunciona em exemplos reservados 

# pré processar usando apenas informações do treinamento - tratamento de infos ausentes
medianas = X_treino.median()
X_treino = X_treino.fillna(medianas)
X_teste = X_teste.fillna(medianas)

# 5. Criar e treinar a Árvore de decisão

modelo = DecisionTreeClassifier(max_depth=3, min_samples_leaf=5, random_state=42)

modelo.fit(X_treino, y_treino)

# 6. Fazer previsões e avaliar o modelo

previsoes = modelo.predict(X_teste)

acuracia = accuracy_score(y_teste, previsoes)

matriz = confusion_matrix(
    y_teste,
    previsoes,
    labels=["Precisa de Atenção", "Desempenho_satisfatorio"],
)

print(f"\nAcurácia do modelo: {acuracia:.2f}")

# 7. Descobrir quais variáveis mais influenciaram as decisões

importancias = pd.Series(modelo.feature_importances_, index=variaveis)

print("\nImportancia das variaveis:")

print(importancias.sort_values(ascending=False).round(3))


# 8. Visualizar o padrão aprendido

plt.figure(figsize=(18, 8))

plot_tree(
    modelo,
    feature_names=variaveis,
    class_names=modelo.classes_,
    filled=True,
    rounded=True,
    fontsize=9,
)

plt.title("Árvore de Decisão - Desempenho dos Estudantes")

plt.tight_layout()

plt.show()


# 9. Usar o modelo em um novo caso

novo_aluno: pd.DataFrame = pd.DataFrame(
    [
        {
            "horas_estudo_semana": 2.0,
            "frequencia_percentual": 65.0,
            "atividades_entregues": 10,
            "media_exercicios": 8.0,
            "participacao_aulas": 2,
            "acesso_plataforma_semana": 3,
            "falta_mes": 6,
        }
    ]
)

previsao_novo_aluno = modelo.predict(novo_aluno)

print(f"\nPrevisão para o novo aluno: {previsao_novo_aluno[0]}")