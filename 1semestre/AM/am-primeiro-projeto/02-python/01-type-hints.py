# type annotations - módulo para indicar o tippo de dado esperado 
# não é obrigatório, mas ajuda a melhorar a legibilidade do código e a detectar erros em tempo de desenvolvimento
# pode ser usado com ferramentas de análise de código como numpy, para verificar se os tipos estão corretos
# typo são boas práticas, mas não são obrigatórios

from typing import Final

string: str = "Python"
inteiro: int = 20
ponto_flutuante: float = 3.14
booleano: bool = True
lista: list = [1, 2, 3, 4, 5]
lista_numeros: list[int] = [1, 2, 3, 4, 5]

# constantes não existem em python 
# PEP8 - convenção para indicar uma constante é usar letras maiúsculas

MAX_TENTATIVAS: Final[int] = 5