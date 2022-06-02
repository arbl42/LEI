/**
 * @file bot.h
 * @brief Definições dos ro'bot's.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho -A89504
*/

#ifndef PROJ_BOT_H
#define PROJ_BOT_H

#include "estado.h"

/**
 * @brief Lista dinâmica em que cada nodo é constituido por uma posição, uma lista de posições e o apontador para a
 * próxima lista.
 *
 * Esta estrutura de dados é uma "Lista de Listas", isto é, a lista exterior contem uma Posição e uma lista de
 * Posições (lista interior).
 *
 * A estrutura é utilizada de modo a que todos os elementos da lista interior de cada nodo sejam as posições das
 * jogadas possiveis ao oponente caso seja efetuada a jogada na posicao indicada no nodo.
 */
typedef struct trepadeira{
    POSICAO posicao;         ///< Jogada a realizar pela pessoa
    POSICAO lp;              ///< Todas as jogadas do oponente possiveis derivadas da primeira pelo próprio
    struct trepadeira *prox; ///< Outras jogadas que o próprio pode fazer
} *TTree;

/**
 * @brief Adiciona um Estado inicial à estrutura 'trepadeira'.
 * @see trepadeira
 */
typedef struct raizTrepadeira{
    ESTADO estado;          ///< Estado inicial
    TTree prox;             ///< Possibilidades da próxima jogada, seguidas das possibilidades derivadas
} rTTree;

ESTADO botJogar (ESTADO e);

POSICAO bot1(ESTADO e);

int rand_between(int min, int max);

POSICAO bot2(ESTADO e);

int bot2worth(POSICAO p,ESTADO e);

POSICAO bot3(ESTADO e);

TTree buildTTree (ESTADO e);

rTTree buildrTTree (ESTADO e);

void maiorScorePos (ESTADO e, POSICAO jog1, POSICAO *jog2);

void menor2jogada (rTTree *r);

POSICAO escolhaminMax (rTTree r);

void free1TTree (TTree *t);

#endif //PROJ_BOT_H