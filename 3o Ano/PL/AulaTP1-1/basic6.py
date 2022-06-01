import re
# Split string by the specified re:

print("Split com limites, de uma linha, por uma RE: ")
print("Informação dum aluno: A81234; Ana Dias; 12; 10; 14; 11; 15")
inputFromUser = input(">> ")
while inputFromUser != "":
  lista = re.split(r';', inputFromUser, maxsplit=2, flags=0)
  notas = re.split(r';', lista[2])
  soma = 0
  for n in notas:
      soma = soma + int(n)
  med = soma/len(notas)
  print("O aluno com número ", lista[0], " e nome ", lista[1], " tem média: ", med) 
  inputFromUser = input(">> ")

