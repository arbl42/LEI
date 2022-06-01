# ()
# ()()()
# (())(())(())

import ply.yacc as yacc

from parenthesis_lex import tokens

def p_grammar(p):
    """
    linguagem : lista_parenthesis

    lista_parenthesis : parenthesis lista_parenthesis
                      |

    parenthesis : PE parenthesis PD
    """


def p_error(p):
    print('Syntax error')
    parser.success = False

parser = yacc.yacc()

for line in sys.stdin:
    parser.success = True
    parser.parse(line)
    if parser.success:
        print('Parsing completed')