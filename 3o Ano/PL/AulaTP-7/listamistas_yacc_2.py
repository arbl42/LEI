import ply.yacc as yacc

from lmistas_lex import tokens

def p_inv_funcao(p):
    "inv_funcao : ID lista_mista"
    print('Parsing completed')
    print('Palavras: ', p.parser.pal)
    print('Numeros: ', p.parser.num)

def p_lista_mista(p):
    "lista_mista : PE conteudo PD"

def p_conteudo_empty(p):
    "conteudo : "
    pass

def p_conteudo_elementos(p):
    "conteudo : elementos"
    pass

def p_elementos_elemento(p):
    "elementos : elemento"
    pass

def p_elementos_virg(p):
    "elementos : elemento VIRG elementos"
    pass

def p_elemento_ID(p):
    "elemento : ID"
    parser.pal += 1

def p_elemento_NUM(p):
    "elemento : NUM"
    parser.num += 1


def p_error(p):
    print('Syntax error')

parser = yacc.yacc()
parser.pal=0
parser.num=0


for line in sys.stdin:
    parser.parse(line)
    parser.pal=0
    parser.num=0