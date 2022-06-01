/**
 * @file interpretador.h
 * @brief Interpretador do jogo.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#ifndef PROJ_INTERPRETADOR_H
#define PROJ_INTERPRETADOR_H

#include "estado.h"
#include "stack.h"

ESTADO interpretar(STACK *s,ESTADO e, char linha[]);

void print_prompt(ESTADO e);

void interpretador(STACK *s,ESTADO e);

#endif //PROJ_INTERPRETADOR_H