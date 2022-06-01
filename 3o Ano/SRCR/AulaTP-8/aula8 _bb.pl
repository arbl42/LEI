%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica 
% Resolucao de problemas de pesquisa (Ficha 10)


%---------------------------------  dados do problema ---------

% estado inicial
inicial(jarros(0, 0)).

% estados finais
final(jarros(4, _)).
final(jarros(_, 4)).

% transicoes possiveis transicao: EixOpXEf

transicao(jarros(V1, V2), encher(1), jarros(8, V2)):- V1 < 8.
transicao(jarros(V1, V2), encher(2), jarros(V1, 5)):- V2 < 5.


transicao(jarros(V1, V2), encher(1, 2), jarros(NV1, NV2)):- 
	V1 > 0,
	NV1 is max(V1 - 5 + V2, 0), 
	NV1 < V1, 
	NV2 is V2 + V1 - NV1.

transicao(jarros(V1, V2), encher(2, 1), jarros(NV1, NV2)):- 
	V2 > 0,
	NV2 is max(V2 - 8 + V1, 0), 
	NV2 < V2, 
	NV1 is V1 + V2 - NV2.

trasicao(jarros(V1,V2),vazio(1), jarros(0,V2)):- V1 > 0.
trasicao(jarros(V1,V2),vazio(2), jarros(V1,0)):- V2 > 0.

%--------------- d) --------------  - - - - - 
% resolvedf(S), escrever(S).
%exemplo resultado S = [encher(1), encher(2), vazio(1), encher(2,1), encher(2), encher(2,1), vazio(1), encher(2,1), encher(...)[...]]

resolvedf(Solucao) :-
	inicial(InicialEstado),
	resolvedf(InicialEstado,[InicialEstado],Solucao).

resolvedf(Estado,_,[]) :-
	final(Estado),!.

resolvedf(Estado,Historico,[Move|Solucao]) :-
	trasicao(Estado, Move, Estado1),
	nao(membro(Estado1,Historico)),
	resolvedf(Estado1, [Estado1|Historico], Solucao).



todos(L):- findall(S,C),(resolvedf(S), length(S,C), L). %escreve as solucoes todas possiveis

melhorSolucao(L):- findall(S,C), (resolvedf(S), length(S,C), L), minimo(L,(S,Custo)). %a melhor solucao vai ser a que tiver menos transicoes, isto e, a que tiver menor length



%--------------- e) --------------  - - - - - 

%VER PRINTS

%--- listas diferenças
%ua representaçao alternativa para listas que permite escrever predicados de processamento
%de listas muito eficiente
%Leitura: [e1,e2,e3|X]-X representa a lista [e1,e2,e3]
%			caso base: X-X representa a lista vazia

%resolvebf(Solucao):-
%	inicial(InicialEstado),
%	resolvebf([(InicialEstado,[])])




%breadth-first alternativo
%
%resolve_bfs(NodeS,NodeD,Sol):-
%	breadthfirst([[NodeS]], NodeD, Sol).
%
%breadthfirst([[Node|Path]|_], Node, [Node|Path]).
%
%breadthfirst([Path|Paths], NodeD, Sol):-
%	extend(Path,NewPaths),
%	conct(Paths,NewPaths,Paths1),
%	breadthfirst(Paths1,NodeD,Sol).
%
%extend([Node|Path]m NewPaths):-



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

membro(X, [X|_]).
membro(X, [_|Xs]):-
	membro(X, Xs).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado escrever: Questao -> {V,F}

escrever([]).
escrever([X|L]):- write(X), nl, escrever(L).

escrever2([]). %para escrever a soluçao e o respetivo custo
escrever2([(X,Y)|L]):- write(X), write(' - '), write(Y), nl,  escrever2(L).

% nl -> new line

%----------------------------- --- - - - - - - - - - - - - - - -

minimo([(P,X)],(P,X)).
minimo([(Px,X)|L],(Py,Y)) :- minimo(L,(Py,Y), X>Y.
minimo([(Px,X)|L],(Px,X)) :- minimo(L,(Py,Y), X=<Y.
