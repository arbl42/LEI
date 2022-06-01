import ply.lex as lex

file = open('example1.txt')

tokens = ['ON','OFF','CHAR']

states = [
    ('comment', 'exclusive')
]


def t_ANY_ON(t):
    r'(/\*)'
    t.lexer.push_state('comment') #push state para encontrar comentarios dentro de comentarios
    #print('COMENTARIO ENCONTRADO.')

def t_comment_OFF(t):
    r'(\*/)'
    t.lexer.pop_state() # pop state para voltar ao estado anterior
    #print('COMENTARIO ELIMINADO.')


def t_CHAR(t):
    r'.|\n' 
    print(t.value,end='')

def t_comment_CHAR(t):
    r'.|\n' 
    pass


#t_ANY_comment_ignore = '\n\t' - nao se quer ignorar nenhum caracter

def t_ANY_error(t): 
    print('Illegal character: ', t.value[0])
    t.lexer.skip(1)

lexer = lex.lex()


for line in file:
    lexer.input(line)
    for tok in lexer:
        pass

file.close()