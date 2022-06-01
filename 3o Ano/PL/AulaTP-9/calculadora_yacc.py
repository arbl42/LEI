import ply.yacc as yacc

from calculadora_tex import tokens

def p_calculadora(p):
    "calculadora : comandos"


#----------------------comandos-------------------------------------- - - - - -  - -  - 


def p_comandos(p):
    """
    comandos : comandos comando
             | 
    """


def p_comando_declaracao(p):
    "comando : '$' ID"
    p.parser.variaveis.update({p[2]: 0})

def p_comando_leitura(p):
    "comando : '?' ID"
    v = input('Introduza um valor inteiro: ')
    p.parser.variaveis[p[2]] = int(v)


def p_comando_escrita(p):
    "comando : '!' expressao"
    print(p[2])

def p_comando_atribuicao(p):
    "comando : ID '=' expressao"
    p.parser.variaveis[p[1]] = p[3]

def p_comando_informacao(p):
    "comando : '!' '!'"
    print(p.parser.variaveis)


#----------------------expressoes-------------------------------------- - - - - -  - -  - 


def p_expressao_mais(p):
    "expressao : expressao '+' termo"
    p[0] = p[1] + p[3]

def p_expressao_menos(p):
    "expressao : expressao '-' termo"
    p[0] = p[1] - p[3]

def p_expressao_termo(p):
    "expressao : termo"
    p[0] = p[1]

def p_termo_mult(p):
    "termo : termo '*' fator"
    p[0] = p[1] * p[3]

def p_termo_div(p):
    "termo : termo '/' fator"
    if p[3] == 0:
        print('Erro. Divisao por 0. Assumindo valor zero..')
        p[0] = 0
    else:
        p[0] = int(p[1] / p[3])

def p_termo(p):
    "termo : fator"
    p[0] = p[1]

def p_fator_num(p):
    "fator : NUM"
    p[0] = p[1]

def p_fator_id(p):
    "fator : ID"
    if p[1] not in p.parser.variaveis:
        print(f'Aviso: Variavel nao declarada: {p[1]}. Iniciando a variavel a 0..')
        p.parser.variaveis[p[1]] = 0
    p[0] = p.parser.variaveis[p[1]]

def p_fator_expressao(p):
    "fator : '(' expressao ')'"
    p[0] = p[2]

def p_error(p):
    print("Syntax error")

parser = yacc.yacc()  
parser.variaveis = {}  

import sys

for line in sys.stdin:
    parser.parse(line)
    #print('Resultado : ' + str(resultado))