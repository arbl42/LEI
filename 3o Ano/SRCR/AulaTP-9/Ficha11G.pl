%--- ficha11 ---
%
%--a. tipo de problema para estado único

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

% move(b,f,2).

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

profundidade(Destino,Destino,H,D):-inverso(H,D) .

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





