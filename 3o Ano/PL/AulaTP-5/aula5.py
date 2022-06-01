import re
import sys


def somador1():
    flag = False
    soma = 0
    for line in sys.stdin:
        if re.search(r'on', line):
            flag=True
            print('Soma ativada')
        elif re.search(r'off', line):
            flag=False
            print('Soma desativada')
        elif re.search(r'=', line):
            print('Soma atual: ', soma)
        elif m := re.search(r'[0-9]+', line):
            if flag:
                soma += int(m.group())

    print('Soma total:', soma)               


def somador2():

    for line in sys.stdin:
        elementos = re.findall(r'on|off|\d+|=', line) #\d represenda digitos
        print(elementos)

        flag = False
        soma = 0
        for e in elementos:
            if e == 'on':
                flag=True
                print('Soma ativada')
            elif e == 'off':
                flag=False
                print('Soma desativada')
            elif e == '=':
                print('Soma atual: ', soma)
            else:
                if flag:
                    soma += int(e)

    print('Soma total: ', soma)

