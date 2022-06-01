import re

#execute with 'python3 -i problemas.py'

def busca(ER):
    fraseFonte = input(">> ")
    while fraseFonte != "":
            y = re.search(ER, fraseFonte)
            if(y): 
                print("VÁLIDO")   
            else:
                print("INVÁLIDO")
    fraseFonte = input(">> ")

#Problema 1
def problema1():
    busca(r'^[_.][0-9]+(?i:[a-z]){3,}_?$')


#Problema 2
def problema2():
    f = open("ips.txt", "r")
    
    for linha in f:
        y = re.search(r'((2[0-4][0-9]|25[0-5]|1[0-9][0-9]|[1-9][0-9])\.){3}(2[0-4][0-9]|25[0-5]|1[0-9][0-9]|[1-9][0-9])', linha)
        x = re.search(r'([0-9a-f]{1,4}:){7}[0-9a-f]{1,4}', linha)
        if y:
            print("IPv4",end=" - ")
        elif x:
            print("IPv6",end=" - ")  
        else:
            print("Erro",end=" - ")
        
        print(linha,end="")
    
    f.close()    


def problema3():
    f = open("coords.txt", "r")

    print("----------------inicio match------------------")

    for linha in f:
        c = re.search(r'^\(((\+|\-)?(([0-8][0-9](\.[0-9]+)?)|90(\.0+)?)), ((\+|\-)?((1[0-7][0-9](\.[0-9]+)?)|180(\.0+)?))\)$', linha)
        if c:
            print("Valido")
        else:
            print("Invalido")            

    f.close()
    print("-----------------fim match-------------------")
    

def problema4():
    with open("text.txt","r") as file:
        string = file.read().replace('\n',' ')

    print("----------------inicio match------------------")
    print(string)
    
    pattern = r'(?:[0-9]{4}-){3}(?:[0-9]{4})|(?:[0-9]{4}:){3}(?:[0-9]{4})|(?:[0-9]{4}\.\.\.){3}(?:[0-9]{4})'
    #pattern2 = r'\d{2}(?P<sep>-|:|\.\.\.)(?:(?:\d{2})(?:(?P=sep)|$)){3}'
    lista = re.findall(pattern, string)
    
    print(lista)

    print("-----------------fim match-------------------")
    

