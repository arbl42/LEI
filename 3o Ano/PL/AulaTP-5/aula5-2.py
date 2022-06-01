import ply.lex as lex


# Lista de Tokens: Obrigatoria

tokens = [ # o nome tem de ser obrigatoriamente 'tokens'
        'ON',
        'OFF',
        'EQUAL',
        'NUMBER'
]

flag = False
soma = 0

t_ignore = ' \t\n' # nao e uma expressao regular
                    # e uma lista de caracteres que se forem encontrados nao vao entrar para a lista de tokens

# ER -> Acao

def t_ON(t): #recebe um token; o nome destas funçoes tem de começar obrigatoriamente por t_
    r'[Oo][Nn]'
    global flag #indica a variavel global, se nao assume que flag e uma variavel local da funcao
    flag = True
    #print('Soma ativada')
    return t

def t_OFF(t):
    r'[Oo][Ff][Ff]'
    global flag
    flag = False
    #print('Soma desativada')
    return t

def t_EQUAL(t):
    r'='
    #print('Soma atual:', soma)
    return t

def t_NUMBER(t):
    r'\d+'
    t.value = int(t.value)
    if flag:
        global soma
        soma = soma + t.value
    return t

def t_error(t):
    print('Erro no caracter ' + t.value)


lexer = lex.lex()

data = 'on 1 2 3=off 1 2 =on 1 1='

lexer.input(data)

for token in lexer:
    print(token)

'''
while True:
    token = lexer.token()
    if not token:
        break
    print(token)
'''
print('Soma Total: ', soma)