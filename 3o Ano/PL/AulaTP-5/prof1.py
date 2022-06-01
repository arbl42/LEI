"""
1
1.0 + abc
1 + 2.0 - 4
2*3/b
"""
import ply.lex as lex
import sys

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

t_ID = r'\w+'

#t_SOMA = r'\+'

#t_SUB = r'-'

#t_MULT = r'\*'

#t_DIV = r'/'


def t_FLOAT(t):
    r'\d+\.\d{1,2}'
    t.value = float(t.value)
    return t


def t_INT(t):
    r'\d+'
    t.value = int(t.value)
    return t

t_ignore = ' \t\n' # nao é uma expressão regular

def t_error(t):
    print('Erro no caracter ' + t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()

for line in sys.stdin:
    lexer.input(line)
    for token in lexer:
        print(token)

#def t_ID(t):
#    r'\w+'
#    return t