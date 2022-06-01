/**
 * @file comando.h
 * @brief Definições dos comandos disponíveis ao utilizador.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#ifndef PROJ_COMANDO_H
#define PROJ_COMANDO_H

#include "estado.h"
#include "stack.h"

ESTADO cmd_N (STACK *s,ESTADO e,char arg);

ESTADO cmd_L (STACK *s,ESTADO e,char arg[]);

void cmd_E (ESTADO e,char arg[]);

ESTADO cmd_J (STACK *s,ESTADO e,int l,int c);

void cmd_S (ESTADO e);

void cmd_H (ESTADO e);

ESTADO cmd_U (STACK *s,ESTADO e);

ESTADO cmd_A (STACK *s,ESTADO e,char arg,int level);

void cmd_C (int n,char arg[], char nomeant[]);

#endif //PROJ_COMANDO_H
