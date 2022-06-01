/**
 * @file interpretador.c
 * @brief Interpretador do jogo.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#include <stdlib.h>
#include <ctype.h>
#include "comando.h"

///Tamanho maximo de uma string
#define MAX_BUFFER 1024

/**
 * @brief Função que lê os comandos digitados pelo utilizador.
 *
 * Esta função tem como objetivo organizar melhor as escolhas dos comandos, tornando-se numa espécie de menu, que
 * guarda os dados diretamente escritos pelo utilizador na linha de comandos.
 *
 * @param s Apontador para a pilha de estados que apareceram durante o correr do jogo atual, ordenados do mais recente
 * para o mais antigo.
 * @param e Estado atual do jogo.
 * @param linha Comando digitado pelo utilizador.
 * @return e Estado do jogo depois das mudanças (se existentes) efetuados pelo comando.
 */
ESTADO interpretar(STACK *s,ESTADO e, char linha[]) {
    int n;
    char cmd[MAX_BUFFER];                                       //Comando a chamar
    char arg[MAX_BUFFER];                                       //Strings para guardar argumentos se necessários
    char campeonato[MAX_BUFFER];                                //Nome do ficheiro a guardar comando C, caso nada
                                                                // mais seja idicado
    int arg1, arg2;                                             //Inteiros para guardar argumentos se necessários
    sscanf(linha, "%s", cmd);
    switch (toupper(cmd[0])) {
        case 'N':
            n = sscanf(linha,"%s %s", cmd, arg);
            if(n>=2) e = cmd_N (s,e,toupper(arg[0]));
            else printf("Argumentos incorretos/em falta!\n");
            break;
        case 'L':
            n = sscanf(linha,"%s %s", cmd, arg);
            if(n>=2) e = cmd_L (s,e,arg);
            else printf("Argumento incorreto/em falta!\n");
            break;
        case 'E':
            n = sscanf(linha,"%s %s", cmd, arg);
            if(n>=2) cmd_E (e,arg);
            else printf("Argumento incorreto/em falta!\n");
            break;
        case 'J':
            n = sscanf(linha,"%s %d %d", cmd,&arg1,&arg2);
            if(n>=3) e = cmd_J(s,e,arg1-1,arg2-1);
            else printf("Argumentos incorretos/em falta!\n");
            break;
        case 'S':
            cmd_S(e);
            break;
        case 'H':
            cmd_H(e);
            break;
        case 'U':
            e = cmd_U(s,e);
            break;
        case 'A':
            n = sscanf(linha,"%s %s %d", cmd, arg, &arg1);
            if(n>=3) e = cmd_A (s,e,toupper(arg[0]),arg1);
            else printf("Argumentos incorretos/em falta!\n");
            break;
        case 'C':
            n = sscanf(linha,"%s %s",cmd,arg);
            cmd_C(n,arg,campeonato);
            break;
        case 'Q':
            exit(0);
        default:
            printf("Comando invalido\n");
            break;
    }
    return e;
}

/**
 * @brief Imprime no ecrã a peça que vai jogar, de modo a tornar a utilização do programa mais clara.
 * @param e Estado atual do jogo
 */
void print_prompt(ESTADO e){
    switch (e.peca){
        case VALOR_X:
            printf("Reversi X> ");
            break;
        case VALOR_O:
            printf("Reversi O> ");
            break;
        default:
            printf("Reversi ?> ");
            break;
    }
}

/**
 * @brief Função que chama continuamente as funções 'interpretar' e 'print_prompt'.
 * @param s Apontador para a pilha de estados que apareceram durante o correr do jogo atual, ordenados do mais recente
 * para o mais antigo.
 * @param e Estado atual do jogo.
 */
void interpretador(STACK *s,ESTADO e) {
    char linha[MAX_BUFFER];
    print_prompt(e);
    while (fgets(linha, MAX_BUFFER, stdin)) {
        e = interpretar(s,e,linha);
        print_prompt(e);
    }
}