import ply.lex as lex
import sys

'''
1.0 + aBC
1 + 2.0 - 4
2 * 3 / b 
'''

tokens = [
    #'SOMA',
    #'SUB',
    #'MULT',
    #'DIV',
    'INT',
    'FLOAT',
    'ID'
]


literals = ['+', '-', '*', '/']

# e necesssario ter cuidado com a ordem das regras dos tokens. Normalmente as mais especificas
# definem se primeiro, isto porque o python da match por ordem da mesma forma que o '|' nas ER's
# Exemplo: a|b|c|d -> primeiro vai tentar dar match ao a. Se nao der match passa ao b e assim sucessivamente

t_ID = r'\w+' #\w representa todas as letras - maiusculas e minusculas
                # quando so se pretende fazer return do token pode se fazer desta maneira

#t_SOMA = r'\+'
#t_SUB = r'-'
#t_MULT = r'\*'
#t_DIV = r'/'



def t_FLOAT(t): # Exemplo: float e mais especifico que Int, convem ser definido primeiro
    r'\d+\.\d{1,2}'
    t.value = float(t.value)
    return t

def t_INT(t):
    r'\d+'
    t.value = int(t.value)
    return t


#def t_ID(t):
#    r'\w+'
#    return t


t_ignore = ' \t\n'

def t_error(t):
    print('Erro no caracter ' + t.value[0])
    t.lexer.skip(1) # avança para o token seguinte

lexer = lex.lex()

for line in sys.stdin:
    lexer.input(line)
    for token in lexer:
        print(token)



   