import re

def conversao1(): #DOM parsing

    f = open("relatorio.xml")
    content = f.read()

    content = re.sub(r'<\?xml version="1.0" encoding= "(.+)"\?>',r'<!DOCTYPE html>\n<html>\n<head>\n   <meta charset="\1">\n</head> ', content)
    content = re.sub(r'(</?)relatorio>',r'\1body>', content)
    content = re.sub(r'(</?)titulo(>)',r'\1h1\2', content)
    content = re.sub(r'(</?)data(>)',r'\1h3\2\n<hr>', content)
    content = re.sub(r'<autores>',r'<h3>Autores</h3><ul>', content)
    content = re.sub(r'</autores>',r'</ul>', content)
    content = re.sub(r'(</?)autor(>)',r'\1li\2', content)
    content = re.sub(r'</?nome>',r'',content)
    content = re.sub(r'<numero>',r'(',content)
    content = re.sub(r'</numero>',r') ',content)
    content = re.sub(r'<email>',r': ',content)
    content = re.sub(r'</email>',r' ',content)
    content = re.sub(r'<resumo>',r'<h3>Resumo</h3><p>', content)
    content = re.sub(r'</resumo>',r'</p>', content)
    
    print(content)
    f.close()


def conversao2(): #SAX parsing
    
    f = open('relatorio.xml')
    for line in f:
        if re.search(r'<\?xml version="1.0" encoding=".+"\?>', line):
            encoding = re.search(r'enconding="(.+)"', line).group(1)
            print(f'<!DOCTYPE html>\n<html>\n<head>\n   <meta charset="{encoding}">\n</head>')
        
        elif re.search(r'<relatorio>', line):
            print('<body>')
        
        elif re.search(r'</relatorio>', line):
            print('</body>\n</html>')    
        
        elif m := re.search(r'<titulo>(.*)</titulo>', line):
            print(f'<h1>{m.group(1)}</h1>')
        
        elif m := re.search(r'<data>(.*)</data>', line):
            print(f'<h2>{m.group(1)}</h2><hr>')

        elif re.search(r'<autores>', line):
            print('<ul>')
        
        elif re.search(r'</autores>', line):
            print('</ul>')    
            
        elif re.search(r'<autor>', line):
            print('<li>')
        
        elif re.search(r'</autor>', line):
            print('</li>')

        elif m := re.search(r'<nome>(.*)</nome>', line):
            print(m.group(1))

        elif m := re.search(r'<numero>(.*)</numero>', line):
            print('(' + m.group(1) + ')')

        elif m := re.search(r'<email>(.*)</email>', line):
            print(':' + m.group(1))    

        elif re.search(r'<resumo>', line):
            print('<h3>Resumo:</h3><p>')    

        elif re.search(r'</resumo>', line):
            print('</p>')

        else:
            m = re.search(r'.+', line)
            print(m.group())

    f.close()

def conversao3():
    f = open('relatorio.xml')

    for line in f:        
        m = re.search(r'(<[a-z]+>)?([^<]*)(</[a-z]+>)?', line.strip())
        print(m.groups())
        
        for g in m.groups():
            if g is None:
                pass
            elif g == '<relatorio>':
                print('<body>')
            elif g == '</relatorio>':
                print('</body>')
            elif g == '<titulo>':
                print('<h1>')
            elif g == '</titulo>':
                print('</h1>')
            elif g == '<data>':
                print('<h2>')
            elif g == '</data>':
                print('</h2>')
            elif g == '<autores>':
                print('<ol>')
            elif g == '</autores>':
                print('</ol>')
            elif g == '<autor>':
                print('<li>')
            elif g == '</autor>':
                print('</li')
            elif g == '<nome>':
                pass
            elif g == '</nome>':
                pass
            elif g == '<numero>':
                print('(')
            elif g == '</numero>':
                print(')')
            elif g == '<email>':
                print(': ')
            elif g == '</email>':
                pass
            elif g == '<resumo>':
                print('<p>')
            elif g == '</resumo>':
                print('</p') 
            else:
                print(g)    
            
    f.close()    