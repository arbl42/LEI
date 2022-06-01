
# parsetab.py
# This file is automatically generated. Do not edit.
# pylint: disable=W,C,R
_tabversion = '3.10'

_lr_method = 'LALR'

_lr_signature = "ID NUMcalculadora : comandos\n    comandos : comandos comando\n             | \n    comando : '$' IDcomando : '?' IDcomando : '!' expressaocomando : ID '=' expressaocomando : '!' '!'expressao : expressao '+' termoexpressao : expressao '-' termoexpressao : termotermo : termo '*' fatortermo : termo '/' fatortermo : fatorfator : NUMfator : IDfator : '(' expressao ')'"
    
_lr_action_items = {'$':([0,2,3,8,10,11,12,13,14,15,16,18,24,25,26,27,28,],[-3,4,-2,-4,-5,-8,-6,-11,-14,-15,-16,-7,-9,-10,-12,-13,-17,]),'?':([0,2,3,8,10,11,12,13,14,15,16,18,24,25,26,27,28,],[-3,6,-2,-4,-5,-8,-6,-11,-14,-15,-16,-7,-9,-10,-12,-13,-17,]),'!':([0,2,3,7,8,10,11,12,13,14,15,16,18,24,25,26,27,28,],[-3,7,-2,11,-4,-5,-8,-6,-11,-14,-15,-16,-7,-9,-10,-12,-13,-17,]),'ID':([0,2,3,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,24,25,26,27,28,],[-3,5,-2,8,10,16,-4,16,-5,-8,-6,-11,-14,-15,-16,16,-7,16,16,16,16,-9,-10,-12,-13,-17,]),'$end':([0,1,2,3,8,10,11,12,13,14,15,16,18,24,25,26,27,28,],[-3,0,-1,-2,-4,-5,-8,-6,-11,-14,-15,-16,-7,-9,-10,-12,-13,-17,]),'=':([5,],[9,]),'NUM':([7,9,17,19,20,21,22,],[15,15,15,15,15,15,15,]),'(':([7,9,17,19,20,21,22,],[17,17,17,17,17,17,17,]),'+':([12,13,14,15,16,18,23,24,25,26,27,28,],[19,-11,-14,-15,-16,19,19,-9,-10,-12,-13,-17,]),'-':([12,13,14,15,16,18,23,24,25,26,27,28,],[20,-11,-14,-15,-16,20,20,-9,-10,-12,-13,-17,]),')':([13,14,15,16,23,24,25,26,27,28,],[-11,-14,-15,-16,28,-9,-10,-12,-13,-17,]),'*':([13,14,15,16,24,25,26,27,28,],[21,-14,-15,-16,21,21,-12,-13,-17,]),'/':([13,14,15,16,24,25,26,27,28,],[22,-14,-15,-16,22,22,-12,-13,-17,]),}

_lr_action = {}
for _k, _v in _lr_action_items.items():
   for _x,_y in zip(_v[0],_v[1]):
      if not _x in _lr_action:  _lr_action[_x] = {}
      _lr_action[_x][_k] = _y
del _lr_action_items

_lr_goto_items = {'calculadora':([0,],[1,]),'comandos':([0,],[2,]),'comando':([2,],[3,]),'expressao':([7,9,17,],[12,18,23,]),'termo':([7,9,17,19,20,],[13,13,13,24,25,]),'fator':([7,9,17,19,20,21,22,],[14,14,14,14,14,26,27,]),}

_lr_goto = {}
for _k, _v in _lr_goto_items.items():
   for _x, _y in zip(_v[0], _v[1]):
       if not _x in _lr_goto: _lr_goto[_x] = {}
       _lr_goto[_x][_k] = _y
del _lr_goto_items
_lr_productions = [
  ("S' -> calculadora","S'",1,None,None,None),
  ('calculadora -> comandos','calculadora',1,'p_calculadora','calculadora_yacc.py',6),
  ('comandos -> comandos comando','comandos',2,'p_comandos','calculadora_yacc.py',14),
  ('comandos -> <empty>','comandos',0,'p_comandos','calculadora_yacc.py',15),
  ('comando -> $ ID','comando',2,'p_comando_declaracao','calculadora_yacc.py',20),
  ('comando -> ? ID','comando',2,'p_comando_leitura','calculadora_yacc.py',24),
  ('comando -> ! expressao','comando',2,'p_comando_escrita','calculadora_yacc.py',30),
  ('comando -> ID = expressao','comando',3,'p_comando_atribuicao','calculadora_yacc.py',34),
  ('comando -> ! !','comando',2,'p_comando_informacao','calculadora_yacc.py',38),
  ('expressao -> expressao + termo','expressao',3,'p_expressao_mais','calculadora_yacc.py',46),
  ('expressao -> expressao - termo','expressao',3,'p_expressao_menos','calculadora_yacc.py',50),
  ('expressao -> termo','expressao',1,'p_expressao_termo','calculadora_yacc.py',54),
  ('termo -> termo * fator','termo',3,'p_termo_mult','calculadora_yacc.py',58),
  ('termo -> termo / fator','termo',3,'p_termo_div','calculadora_yacc.py',62),
  ('termo -> fator','termo',1,'p_termo','calculadora_yacc.py',70),
  ('fator -> NUM','fator',1,'p_fator_num','calculadora_yacc.py',74),
  ('fator -> ID','fator',1,'p_fator_id','calculadora_yacc.py',78),
  ('fator -> ( expressao )','fator',3,'p_fator_expressao','calculadora_yacc.py',85),
]