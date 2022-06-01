import ply.yacc as yacc

from listaCompras_lex import tokens


def p_grammar(p):
    """
    lista : categorias
    
    categorias : categoria
               | categoria categorias

    categoria : nome ':' produtos

    nome : ID

    produtos : produto
             | produto produtos

    produto : '*' INT ';' STR ';' FLOAT ';' INT
    """

def p_lista(p):
    "lista : categorias"
    print('\n{' + p[1] + '\n}')

def p_categorias_categoria(p):
    "categorias : categoria"
    p[0] = p[1]

def p_categorias_categorias(p):
    "categorias : categorias categoria"
    p[0] = p[1] + ',' + p[2]

def p_categoria(p):
    "categoria : nome ':' produtos"
    p[0] = f'\t"{p[1]}" : [\n{p[3]}\n\t]'

def p_nome(p):
    "nome : ID"
    p[0] = p[1]    

def p_produtos_produto(p):
    "produtos : produto"
    p[0] = p[1]


def p_produtos_produtos(p):
    "produtos : produtos produto"
    p[0] = p[1] + ',\n' + p[2]

def p_produto(p):
    "produto : '*' INT ';' STR ';' FLOAT ';' INT"    
    p[0] = f'{{\t\t\t"id" : {p[2]},\n\t\t\t"nome" : "{p[4]}",\n\t\t\t"preco" : {p[6]},\n\t\t\t"quantidade" : {p[8]}\n}}'

def p_error(p):
    print('Syntax error')

parser = yacc.yacc()

file = open("listaC.txt")

for line in file:
    parser.parse(line)

file.close()




