import ply.lex as lex

tokens = ['ON','OFF','EQUAL','NUMBER']

states = [
    ('soma', 'exclusive')
]

def t_NUMBER(t): # OU t_INITIAL_NUMBER
    r'\d+'
    pass

def t_ON(t):
    r'(?i:on)'
    t.lexer.begin('soma')
    print('SOMA ESTA ATIVA.')

#t_ignore = '\n\t'

#def t_error(t):
#    print('Illegal character: ', t.value[0])
#    t.lexer.skip(1)



def t_soma_NUMBER(t):
    r'\d+'
    t.lexer.soma += int(t.value)

def t_soma_OFF(t):
    r'(?i:off)'
    t.lexer.begin('INITIAL')
    print('SOMA DESATIVADA.')


def t_ANY_EQUAL(t): #ANY - aplicada a qualquer estado do states
    r'='
    print('Soma atual: ',t.lexer.soma)

t_ANY_soma_ignore = '\n\t'

def t_ANY_error(t): 
    print('Illegal character at soma: ', t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()

#Estado
lexer.soma = 0

for line in sys.stdin:
    lexer.input(line)
    for tok in lexer:
        pass
    
