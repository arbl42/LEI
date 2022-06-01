import re


def ex1():  
    f = open('emd.csv') 
    """
    for line in f:
    
        m = re.search(r'^(.+),(.+),(.+),(.+),(.+),(.+),(.+),(.+),(.+),(.+),(.+),(.+),(.+)$',line)

        if m:
            primeiro = m.group(4)
            ultimo = m.group(5)
            cidade = m.group(8)

            print(primeiro, ultimo, cidade)
    """

    for i, line in enumerate(f):
        
        if i == 0: #ignora a primeira linha
            continue

        campos = re.split(r',',line)
        print(f'Nome: {campos[3]} {campos[4]}, Cidade: {campos[7]}')
    f.close()
    

def ex2():
    f = open('emd.csv')
    next(f)

    clubes = set()

    for line in f:

        campos = re.split(r',', line)
        clube = campos[-4]
        clubes.add(clube)
    
    clubes = sorted(clubes)

    print(clubes)
    print(len(clubes))

    f.close()

def ex3():
    f = open('emd.csv')
    next(f)

    mods = set()

    for line in f:
        campos = re.split(r',', line)
        clube = campos[-5]
        mods.add(clube)
    
    mods = sorted(mods)

    print(mods)
    print(len(mods))

    f.close()

def ex4():
    with open('emd.csv') as file:
        next(file)

        d = { 'M': 0, 'F': 0}

        for line in file:
            campos = re.split(r',', line)
            genero = campos[6]
            d[genero] += 1   #substitui um if a comparar se o genero e F ou 
    print(f'Masculino: {d["M"]}')
    print(f'Feminino: {d["F"]}')

    """
        m = 0
        f = 0 
        for line in file:
            campos = re.split(r',', line)
            genero = campos[6]
            if genero == 'F':
                f += 1
            else:
                m += 1     
    print(f'Masculino: {m}')
    print(f'Feminino: {f}')
    """

def ex5():
    with open('emd.csv') as file:
        next(file)

        federados = 0
        aprovados = 0

        for line in file:

            campos = re.split(r',', line.strip()) #o strip tira tudo que e blank spaces, \n no inicio e fim da linha
            [fed, aprov] = campos[-2:] #vai buscar os ultimos 2 elementos da lista 

            if fed:
                federados +=1    
            if aprov:
                aprovados +=1
        

    print('Federados: ' + str(federados))
    print('Aprovados: ' + str(aprovados))

def ex6():
    with open('emd.csv') as file:
        next(file)

        anos = {}

        for line in file:
            campos = re.split(r',', line)
            genero = campos[6]
            
            if genero != 'F':
                continue

            data = re.split(r'-', campos[2]) # a data tem formato ano/mes/dia
            ano = data[0]

            if ano in anos:
                anos[ano] += 1
            else:
                anos[ano] = 1
    
    print(dict(sorted(anos.items(), key=lambda p: p[0]))) # o dict transforma a lista num dicionario
                                                         # a parte do lambda funciona como uma flag: 
                                                         # o dicionario vai ser ordenado atraves das chaves               

def ex7():
    with open('emd.csv') as file:
        next(file)

        atletas = []
        for line in file:
            campos = re.split(r',', line)
            if campos[8] == 'Atletismo':
                nome_completo = campos[3] + ' ' + campos[4]
                atletas.append((nome_completo, int(campos[5])))
    
    atletas.sort(key=lambda p: (p[1],p[0]))
    print(atletas)       
