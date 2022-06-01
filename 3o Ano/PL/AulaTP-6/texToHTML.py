import ply.lex as lex
import sys

tokens = [
    'BOLD',
    'ITALIC',
    'UNDERLINE',
    'SECTION',
    'SUBSECTION',
    'SUBSUBSECTION',
    'ENUMERATE',
    'ITEMIZE',
    'ITEM',
    'TEXT',
]

states = [
    ('bold', 'inclusive'),
    ('italic', 'inclusive'),
    ('underline', 'inclusive'),
    ('section', 'inclusive'),
    ('subsection', 'inclusive'),
    ('subsubsection', 'inclusive'),
    ('enumerate', 'inclusive'),
    ('itemize', 'inclusive'),
    ('item', 'inclusive'),
    ('text', 'inclusive'),
]

def t_BOLD(t):
    r'\\textbf\{'
    print('<b>', end='')
    t.lexer.push_state('bold')

def t_bold_end(t):
    r'\}'
    print('</b>', end='')
    t.lexer.pop_state()

def t_ITALIC(t):
    r'\\textit\{'
    print('<i>', end='')
    t.lexer.push_state('italic')

def t_italic_end(t):
    r'\}'
    print('</i>', end='')
    t.lexer.pop_state()

def t_UNDERLINE(t):
    r'\\underline\{'
    print('<u>', end='')
    t.lexer.push_state('underline')

def t_end_underline(t):
    r'\}'
    print('</u>', end='')
    t.lexer.pop_state()

def t_SECTION(t):
    r'\\section\{'
    print('<s>', end='')
    t.lexer.push_state('section')

def t_end_section(t):
    r'\}'
    print('</s>', end='')
    t.lexer.pop_state()

def t_SUBSECTION(t):
    r'\\subsection\{'
    print('<ss>', end='')
    t.lexer.push_state('subsection')

def t_end_subsection(t):
    r'\}'
    print('</ss>', end='')
    t.lexer.pop_state()

def t_SUBSUBSECTION(t):
    r'\\subsubsection\{'
    print('<sss>', end='')
    t.lexer.push_state('subsubsection')

def t_end_subsubsection(t):
    r'\}'
    print('</sss>', end='')
    t.lexer.pop_state()

def t_BENUMERATE(t):
    r'\\begin\{enumerate\}'
    print('<ol>', end='')
    t.lexer.push_state('enumerate')

def t_end_benumerate(t):
    r'\\end\{enumerate\}'
    print('</ol>', end='')
    t.lexer.pop_state()
    
def t_BITEMIZE(t):
    r'\\begin\{itemize\}'
    print('<ul>', end='')
    t.lexer.push_state('itemize')

def t_end_bitemize(t):
    r'\\end\{itemize\}'
    print('</ul>', end='')
    t.lexer.pop_state()

def t_ITEM(t):
    r'\\item\ +'
    print('<li>', end='')
    t.lexer.push_state('item')

def t_end_item(t):
    r'\n'
    print('</li>', end='')
    t.lexer.pop_state()
    

def t_TEXT(t):
    r'.|\n'
    print(t.value, end='')


def t_ANY_error(t): 
    print('Illegal character: ', t.value[0])
    t.lexer.skip(1)


lexer = lex.lex()
for line in sys.stdin:
    lexer.input(line)
    for tok in lexer:
        pass 