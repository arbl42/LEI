# ()
# ()()()
# (())(())(())

import ply.lex as lex

tokens = ['PE', 'PD']

t_PE = r'\('
t_PD = r'\)'

t_ignore = ' \n\t'

def t_error(t):
    print('Illegal caracter' + t.value[0])

lexer = lex.lex()