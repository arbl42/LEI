import ply.lex as lex

# Lista de tokens: Obrigatoria
tokens = [
    'ON',
    'OFF',
    'EQUAL',
    'NUMBER'
]

flag = False
soma = 0
#newlines = 0

# ER -> AÇÃO
def t_ON(t):
    r'[Oo][Nn]'
    global flag 
    flag = True
    print('Soma ativada!')
    return t


def t_OFF(t):
    r'[Oo][Ff][Ff]'
    global flag
    flag = False
    print('Soma desativada!')
    return t

def t_EQUAL(t):
    r'='
    print('Soma atual:', soma)
    return t


def t_NUMBER(t):
    r'\d+'
    t.value = int(t.value)
    if flag:
        global soma
        soma = soma + t.value
    return t

#def t_space(t):
#    r'\ '
#    #print("Encontrei espaço!")
#    pass
#
#
#def t_newline(t):
#    r'\n'
#    global newlines
#    newlines += 1

t_ignore = ' \t\n' # nao é uma expressão regular

def t_error(t):
    print('Erro no caracter ' + t.value)

lexer = lex.lex()

data = 'on 1 2 3=off\n 1 2 =on 1 1='

lexer.input(data)

for token in lexer:
    #print(token)
    pass


"""
while True:
    token = lexer.token()
    if not token:
        break
    print(token)
"""

print('Soma Total:', soma)