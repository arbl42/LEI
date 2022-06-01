import ply.yacc as yacc
from lmistas_lex import tokens

def p_inv_funcao(p):
    "inv_funcao : ID lista_mista"
    #print('Parsing completed')
    #print('Palavras: ', p.parser.pal)
    #print('Numeros: ', p.parser.num)
    #print('Soma total dos números: ', p.parser.soma)
    #print('Lista de palavras: ', p.parser.lista_pal)
    p[0] = p[2]

def p_lista_mista(p):
    "lista_mista : PE conteudo PD"
    p[0] = p[2]

def p_conteudo_empty(p):
    "conteudo : "
    p[0] = []
    

def p_conteudo_elementos(p):
    "conteudo : elementos"
    p[0] = p[1]
    

def p_elementos_elemento(p):
    "elementos : elemento"
    p[0] = [p[1]]
    

def p_elementos_virg(p):
    "elementos : elementos VIRG elemento"
    p[0] = p[1] + [p[3]]

def p_elemento_ID(p):
    "elemento : ID"
    p[0] = p[1]
    #p.parser.pal += 1
    #p.parser.lista_pal.append(p[1])

def p_elemento_NUM(p):
    "elemento : NUM"
    p[0] = p[1]
    #p.parser.num += 1
    #valor = p[1]
    #p.parser.soma += valor

def p_error(p):
    print('Syntax error')

parser = yacc.yacc()
#parser.pal=0
#parser.num=0
#parser.soma=0
#parser.lista_pal = []

import sys

for line in sys.stdin:
    result = parser.parse(line)
    print(result)
    #parser.pal=0
    #parser.num=0
    #parser.soma=0
    #parser.lista_pal = []
