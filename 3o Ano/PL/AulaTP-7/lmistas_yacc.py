import ply.yacc as yacc
import sys


tokens = [
    'ID',
    'NUM',
    'PE',
    'PD',
    'VIRG'
]


def p_grammar(p):
    """
    inv:funcao : ID lista_mista

    lista_mista : PE conteudo PD

    conteudo :
              | elementos

    elementos : elemento
              | elemento VIRG elementos

    elemento : ID
             | NUM
             |       
    """
    pass

def p_error(p):
    print('Syntax error')
    parser.success = False

parser = yacc.yacc()

for line in sys.stdin:
    parser.success = True
    parser.parse(line)
    if parser.success:
        print('Parsing completed')

