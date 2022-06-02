/**
 * @file estado.h
 * @brief Definição do estado i.e. tabuleiro. Representação matricial do tabuleiro.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#ifndef PROJ_ESTADO_H
#define PROJ_ESTADO_H

#include <stdio.h>

///@brief Estrutura que armazena a pontuação de ambos jogadores.
typedef struct score {
    int X; ///<Número de pecas do jogador X
    int O; ///<Número de pecas do jogador O
} SCORE;

///@brief Lista de posições (linha,coluna).
typedef struct posicao {
    int l,c;
    struct posicao* next;
} *POSICAO;

///@brief Definição de valores possiveis no tabuleiro.
typedef enum {VAZIA, VALOR_X, VALOR_O} VALOR;

///@brief Estrutura que armazena o estado do jogo.
typedef struct estado {
    VALOR peca;         ///< Peça do jogador que vai jogar.
    VALOR grelha[8][8]; ///< Tabuleiro de jogo.
    char modo;          ///< Modo de jogo. 0-> manual, 1->contra bot lvl 1, 2->contra bot lvl 2, 3->contra bot lvl 3.
} ESTADO;

int pecaVal (char p);

ESTADO tabInicial(ESTADO e);

void printa(ESTADO e);

ESTADO ler1linha(ESTADO e,FILE *f);

ESTADO lerTabuleiro(ESTADO e,FILE *f);

void escrever1linha(ESTADO e,FILE *f);

void escreverTabuleiro(ESTADO e,FILE *f);

int inTab (int l, int c);

VALOR pecaSim (VALOR v);

int posPossivel (ESTADO e, int l, int c);

int posPossivelDir(ESTADO e,int l,int c, int lv, int cv);

ESTADO virarPecas (ESTADO e, int l, int c);

ESTADO virarPecasDir (ESTADO e, int l, int c, int lv, int cv);

ESTADO jogar (ESTADO e,int l, int c);

ESTADO skipJogvsJog (ESTADO e);

ESTADO skipJogvsBot (ESTADO e);

ESTADO skipBotvsJog (ESTADO e);

void fim (ESTADO e);

int numJogPos (ESTADO e);

SCORE contagemScore (ESTADO e);

void acabarCamp(ESTADO e, char *file);

POSICAO newLP (int l,int c,POSICAO next);

POSICAO listaPos (ESTADO e);

void displayLP (POSICAO p);

void free1LP (POSICAO *p);

void freeLP (POSICAO p);

#endif //PROJ_ESTADO_H