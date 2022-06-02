/**
 * @file stack.h
 * @brief Histórico de estados.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#ifndef PROJ_STACK_H
#define PROJ_STACK_H

#include "estado.h"

///@brief Pilha de estados(stack) ordenados cronologicamente, do mais recente para o mais antigo.
typedef struct Node {
    ESTADO estado;      ///<Estado a guardar
    struct Node* next;  ///<Próximo nodo
} *STACK;

int push(STACK* s,ESTADO e);

int peek(STACK s,ESTADO* e);

int pop(STACK *s);

void display(STACK s);

void resetStack (STACK *s);

#endif //PROJ_STACK_H