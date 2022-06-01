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

def p_error(p):
    print('Syntax error')

parser = yacc.yacc()

file = open("listaC.txt")

for line in file:
    parser.parse(line)

file.close()




