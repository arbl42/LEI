import ply.lex as lex

tokens = [
    'ID',
    'FLOAT',
    'INT',
    'STR'
]

literals = [':',';','*']

t_ID = r'\w+'

def t_STR(t):
    r'\"[^\"]+\"'
    t.value = t.value[1:-1]
    return t

def t_FLOAT(t): #colocar float antes do int: caso contrario interpreta todos os valores como inteiros
    r'\d+\.\d+'
    t.value = float(t.value)
    return t

def t_INT(t):
    r'\d+'
    t.value = int(t.value)
    return t

t_ignore = ' \n\t'

def t_error(t):
    print('Illegal caracter' + t.value[0])
    t.lexer.skip()

lexer = lex.lex()






