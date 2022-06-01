%--- ficha11 ---
%
%--a. tipo de problema para estado unico

%-- b------

%-- grafo--
move(s,a,2).
move(a, b, 2).
move(b, c, 2).
move(c, d, 3).
move(d, t, 3).
move(t, g, 2).
move(g, f, 2).
move(f, e, 5).
move(e, s, 2).

% Add no fim para testar
% move(b,f,2).

estima(s,10).
estima(e,7).
estima(a,5).
estima(b,4).
estima(c,4).
estima(d,3).
estima(f,4).
estima(g,2).
estima(t,0).

inicial(s).

final(t).

%----- c-- pesquisa em profundidade primeiro

adjacente(Nodo,ProxNodo):- move(Nodo,ProxNodo,_).
adjacente(Nodo,ProxNodo):- move(ProxNodo,Nodo,_).


resolve_pp(Nodo,[Nodo|Caminho]):-
 profundidadeprimeiro1(Nodo,[Nodo],Caminho).

profundidadeprimeiro1(Nodo,_,[]):-
 goal(Nodo).

profundidadeprimeiro1(Nodo, Historico,[ProxNodo|Caminho]):-
 adjacente(Nodo,ProxNodo),
 nao(membro(ProxNodo,Historico)),
 profundidadeprimeiro1(ProxNodo,[ProxNodo|Historico], Caminho).



%---- d--primeiro em profundidade como um prob. de estados multiplos

resolve_pp_em(Origem,Destino,Caminho):-
 profundidade(Origem,Destino,[Origem],Caminho).

profundidade(Destino,Destino,H,D):-inverso(H,D).

profundidade(Origem,Destino, Historico,C):-
 adjacente_h(Origem,Prox),
 nao(membro(Prox,Historico)),
 profundidade(Prox,Destino,[Prox|Historico],C).




adjacente_h(Nodo,ProxNodo):- move(Nodo,ProxNodo,_).
adjacente_h(Nodo,ProxNodo):- move(ProxNodo,Nodo,_).






%-- pesquisa em profundidade com o custo associado


adjacente1(Nodo,ProxNodo):- move(Nodo,ProxNodo,_).
adjacente1(Nodo,ProxNodo):- move(ProxNodo,Nodo,_).


resolve_pp_c(Nodo,[Nodo|Caminho],C):-
 profundidadeprimeiro(Nodo,[Nodo],Caminho,C).

profundidadeprimeiro(Nodo,_,[],0):-goal(Nodo).

profundidadeprimeiro(Nodo, Historico,[ProxNodo|Caminho],C):-
 adjacente2(Nodo,ProxNodo,C1),
 nao(membro(ProxNodo,Historico)),
 profundidadeprimeiro(ProxNodo,[ProxNodo|Historico], Caminho,C2), C is C1+C2.

adjacente2(Nodo,ProxNodo,C):- move(Nodo,ProxNodo,C).
adjacente2(Nodo,ProxNodo,C):- move(ProxNodo,Nodo,C).



%------ g ----- pesquisa Gulosa - - -  - - - -
%Testar:
%resolve_gulosa(s,C).

adjacente3([Nodo|Caminho]/Custo/_, [ProxNodo,Nodo|Caminho]/NovoCusto/Est) :- 
    move(Nodo,ProxNodo,PassoCusto),\+ member(ProxNodo,Caminho),
    NovoCusto is Custo + PassoCusto,
    estima(ProxNodo, Est).

agulosa(Caminhos, Caminho) :-
    obtem_melhor_g(Caminhos,Caminho),
    Caminho = [Nodo|_]/_/_,goal(Nodo).
   

agulosa(Caminhos, SolucaoCaminho) :-
    obtem_melhor_g(Caminhos, MelhorCaminho),
    seleciona(MelhorCaminho, Caminhos, OutrosCaminhos),
    expande_gulosa(MelhorCaminho, ExpCaminhos),
    append(OutrosCaminhos, ExpCaminhos, NovoCaminhos),
    agulosa(NovoCaminhos, SolucaoCaminho).


expande_gulosa(Caminho, ExpCaminhos) :-
    findall(NovoCaminho, adjacente3(Caminho,NovoCaminho), ExpCaminhos).


resolve_gulosa(Nodo, Caminho/Custo) :-
    estima(Nodo,Estima),
    agulosa([[Nodo]/0/Estima], InvCaminho/Custo/_),
    inverso(InvCaminho, Caminho).



obtem_melhor_g([Caminho], Caminho) :- !.
obtem_melhor_g([Caminho/Custo1/Est1,_/Custo2/Est2|Caminhos], MelhorCaminho) :-
    Est1 =< Est2, !,
    obtem_melhor_g([Caminho1/Custo1/Est1|Caminhos], MelhorCaminho).
obtem_melhor_g([_|Caminhos], MelhorCaminho) :-
    obtem_melhor_g(Caminhos, MelhorCaminho).




%------ h -----pesquisa informada A *----- - - -  - - - - 
% Testar:
% resolve_aestrela(s,C).

aestrela(Caminhos, Caminho) :-
    obtem_melhor(Caminhos,Caminho),
    Caminho = [Nodo|_]/_/_,goal(Nodo).
   

aestrela(Caminhos, SolucaoCaminho) :-
    obtem_melhor(Caminhos, MelhorCaminho),
    seleciona(MelhorCaminho, Caminhos, OutrosCaminhos),
    expande_aestrela(MelhorCaminho, ExpCaminhos),
    append(OutrosCaminhos, ExpCaminhos, NovoCaminhos),
    aestrela(NovoCaminhos, SolucaoCaminho).


expande_aestrela(Caminho, ExpCaminhos) :-
    findall(NovoCaminho, adjacente3(Caminho,NovoCaminho), ExpCaminhos).


resolve_aestrela(Nodo, Caminho/Custo) :-
    estima(Nodo,Estima),
    aestrela([[Nodo]/0/Estima], InvCaminho/Custo/_),
    inverso(InvCaminho, Caminho).



obtem_melhor([Caminho], Caminho) :- !.
obtem_melhor([Caminho/Custo1/Est1,_/Custo2/Est2|Caminhos], MelhorCaminho) :-
    Custo1 + Est1 =< Custo2 + Est2, !,
    obtem_melhor([Caminho1/Custo1/Est1|Caminhos], MelhorCaminho).
obtem_melhor([_|Caminhos], MelhorCaminho) :-
    obtem_melhor(Caminhos, MelhorCaminho).



%---------- i -------------------- - -  - - - - - - 
% A pesquisa gulosa nao da o caminho de menos custo, ao contrario
% da pesquisa informada A*


%-------------------------- Auxiliares -----------------  -- -- - -- - 

% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

membro(X, [X|_]).
membro(X, [_|Xs]):-
	membro(X, Xs).


%----------------------------- --- - - - - - - - - - - - - - - -

minimo([(P,X)],(P,X)).
minimo([(Px,X)|L],(Py,Y)) :- minimo(L,(Py,Y), X>Y.
minimo([(Px,X)|L],(Px,X)) :- minimo(L,(Py,Y), X=<Y.

%----------------------------- - - - - - - -  -  -   -  - -  - - 

seleciona(E, [E|Xs], Xs).
seleciona(E, [X|Xs], [X|Ys]) :- seleciona(E,Xs,Ys).

%----------------------------  - - - -- -  - - -- --   -  - -  -

inverso([], Xs, Xs).
inverso([X|Xs], Ys, Zs) :-
    inverso(Xs, [X|Ys], Zs).
