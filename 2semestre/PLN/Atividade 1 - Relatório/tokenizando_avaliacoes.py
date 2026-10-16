import nltk
from nltk.corpus import stopwords
from nltk.stem import RSLPStemmer, WordNetLemmatizer
import pandas as pd

# Garantir o download dos recursos necessários do NLTK
recursos = [
    'punkt',
    'punkt_tab',
    'stopwords',
    'rslp',
    'wordnet',
    'omw-1.4',
    'averaged_perceptron_tagger',
    'averaged_perceptron_tagger_eng'
]
for recurso in recursos:
    nltk.download(recurso, quiet=True)

avaliacao = [
    "Amei e simplesmente lindo já uso dessa marca e tenho preferência por ter problema na coluna e ser indicado pelo ortopedista para minha atividades físicas e no dia a dia!.",
    "O tênis é de boa qualidade, compre, confortável e bonito, muito bom para caminhadas e corridas.",
    "O tênis mais perfeito do mundo, só gosto dessa marca e esse superou minhas expectativas, parece que estou descalça com tanto conforto… eu amei. Pedi meu numero mesmo que eu calço que é 35.",
    "Adoro a asics, tenis extremamente confortáveis. Apenas sempre busco adquirir com reforço na frente, pois tenho o pé largo e rasgo as laterais com facilidade.",
    "Achei pelo preço que era original, mas não é. Bem mais inferior. Só não devolvi pq precisava no dia, mas fiquei decepcionada. Usei e não tem a qualidade do original.",
    "Muito bonito e de qualidade porém eu achei que o 38 ia servir mas ficou pegando um pouco no dedão do pé.",
    "A costura dele na frente não é tão boa, infelizmente eu tenho um igualzinho só muda a cor, mas descosturou em menos de um ano, eu queria muito outro que comprei da mesma marca.",
    "Gastei, confortável e bonito. Mas pelo preço acho que o tecido da frente deveria ser reforçada. O meu rasgou muito rápido.",
    "O tênis é super escorregadio, não parece original, muito diferente do que comprei na loja.",
    "Um pouco duro e pesado.",
    "Tenis extremamente duro, desconfortável.",
    "Achei que não é original. A qualidade do que já possuo é diferente.",
    "Muito inferior, pelo valor; furou nos os dois pés, bem no dedinho. 1 mês de usou.",
    "Não ficou confortável, é meu número, mas parece que ficou pequeno. Eu queria pra andar o dia inteiro, mas com tempo acaba machucando.",
    "O tênis da asics é lindo, mas é muito duro. Não dá para ficar com ele por muito tempo porque machuca o pé. Bonito sim, confortável não.",
    "Achei ele duro. Tenho outros na mesma faixa de preço que são mais macios.",
    "Lamentável. Dois meses de caminhada leve, já furou o tecido. Da pra desconfiar da autenticidade. Baixa qualidade para um asics. Tá mais pra xingling. Não recomendo!!!!!.",
    "Produto ruim, não tem boa qualidade com pouco tempo de uso, sem nem ter sido lavado já está danificando o tecido. Estou contrariada, não recomendo.",
    "Produto nao é original. Nao fica macio no pé. Por isso o custo benefício é menor. Eu preciso de um tenis macio, confortável. Mas esteticamente é bonito.",
    "Nem um pouco macio. Nao gostei, me arrependi de ter pago tão caro num produto que não é nada confortável."
]

# 1. Tokenização de frases
frases_tokenizadas = []
for texto in avaliacao:
    frases = nltk.sent_tokenize(texto)
    for frase in frases:
        frases_tokenizadas.append(frase)

print("1. Quantidade de frases tokenizadas:", len(frases_tokenizadas))
print("Frases tokenizadas:")
print(frases_tokenizadas)

print("\n---\n")

# 2. Tokenização de palavras
palavras_tokenizadas = []
for frase in frases_tokenizadas:
    palavras = nltk.word_tokenize(frase.lower())
    for palavra in palavras:
        palavras_tokenizadas.append(palavra)

print("2. Total de palavras tokenizadas:", len(palavras_tokenizadas))
print("Palavras tokenizadas:")
print(palavras_tokenizadas)

print("\n---\n")

# 3. Obtenção do Vocabulário Único
vocabulario_unico = sorted(list(set(palavras_tokenizadas)))

print("3. Quantidade de palavras únicas no vocabulário:", len(vocabulario_unico))
print("Vocabulário único:")
print(vocabulario_unico)
    
print("\n---\n")

# 4. Remoção de Stop Words, Pontuação e Números (Limpeza de ruídos)
stop_words = set(nltk.corpus.stopwords.words('portuguese'))

# isalpha() remove números (ex: 35, 38, 1) e pontuações/caracteres especiais, mantendo letras
palavras_sem_stopwords = [
    palavra for palavra in vocabulario_unico 
    if palavra.isalpha() and palavra not in stop_words
]

print("4. Palavras restantes após remover ruídos (pontuação, números, caracteres especiais) e stop words:", len(palavras_sem_stopwords))
print("Palavras sem stop words:")
print(palavras_sem_stopwords)

print("\n---\n")

# 5. Lematização com WordNet
lematizador = nltk.stem.WordNetLemmatizer()

lemas = []
for palavra in palavras_sem_stopwords:
    lemas.append(lematizador.lemmatize(palavra))

print("5. Quantidade de lemas:", len(lemas))
print("\nLemas gerados:")
print(lemas)

print("\n---\n")

# 6. Radicalização (Stemming) com RSLP
# O RSLP (Removedor de Sufixos da Língua Portuguesa) extrai a raiz dos termos em português
stemmer = nltk.stem.RSLPStemmer()

radicais = []
for palavra in palavras_sem_stopwords:
    radicais.append(stemmer.stem(palavra))

print("6. Quantidade de radicais gerados:", len(radicais))
print("\nRadicais gerados:")
print(radicais)

print("\n---\n")

# 7. POS Tagging (Part-of-Speech Tagging)
# Classificação morfossintática dos termos únicos (substantivos, adjetivos, verbos, etc.)
pos_tags_vocabulario = nltk.pos_tag(palavras_sem_stopwords)

print("7. Quantidade de termos com POS Tagging:", len(pos_tags_vocabulario))
print("\nPOS Tags geradas:")
print(pos_tags_vocabulario)

print("\n---\n")

# 8. Divisão de cada avaliação em palavras (tokens limpos por avaliação)
def limpar_e_tokenizar(texto: str) -> list[str]:
    tokens = nltk.word_tokenize(str(texto).lower())
    return [t for t in tokens if t.isalpha() and t not in stop_words]

print("\n---\n")

# 9. Criação da Base de Dados a partir da lista 'avaliacao'
classificacoes = [
    "Positiva", "Positiva", "Positiva", "Positiva",
    "Positiva", "Positiva", "Positiva", "Positiva",
    "Neutra", "Neutra", "Neutra", "Neutra",
    "Negativa", "Negativa", "Negativa", "Negativa",
    "Negativa", "Negativa", "Negativa", "Negativa"
]

dados_base = []
for idx, (texto, sentimento) in enumerate(zip(avaliacao, classificacoes), start=1):
    termos = limpar_e_tokenizar(texto)
    dados_base.append({
        "id": idx,
        "avaliacao": texto,
        "classificacao": sentimento,
        "termos_limpos": termos,
        "radicais": [stemmer.stem(t) for t in termos],
        "lemas": [lematizador.lemmatize(t) for t in termos],
        "pos_tags": nltk.pos_tag(termos)
    })

df_avaliacoes = pd.DataFrame(dados_base)

print("9. Base de Dados criada com sucesso a partir da lista 'avaliacao':")
print(f"Total de avaliações: {len(df_avaliacoes)}")
print("\nDistribuição das classificações:")
print(df_avaliacoes["classificacao"].value_counts().to_string())

print("\nVisualização tabular da Base de Dados (primeiras 5 linhas):")
colunas_exibir = ["id", "classificacao", "termos_limpos", "radicais"]
print(df_avaliacoes[colunas_exibir].head(5).to_string(index=False))

arquivo_saida = "base_avaliacoes.csv"
arquivo_xlsx = "base_avaliacoes.xlsx"
df_avaliacoes.to_csv(arquivo_saida, index=False, sep=",", encoding="utf-8-sig")
df_avaliacoes.to_excel(arquivo_xlsx, index=False, sheet_name="avaliacoes")
print(f"\n[OK] Base de dados gerada e salva com sucesso nos arquivos '{arquivo_saida}' e '{arquivo_xlsx}'!")
print("\n---\n")

# 10. Obter todos os termos limpos de todas as avaliações
todos_termos_limpos = [termo for termos in df_avaliacoes["termos_limpos"] for termo in termos]

# Vocabulário -> features 
vocabulario = sorted(list(set(todos_termos_limpos)))

print(f"10. Tamanho do vocabulário único (entradas): {len(vocabulario)}")
print("\n---\n")

# 11. Implementar Bag Of Words
matriz = []
for termos_avaliacao in df_avaliacoes["termos_limpos"]:
    vetor_binario = [1 if palavra in termos_avaliacao else 0 for palavra in vocabulario]
    matriz.append(vetor_binario)
