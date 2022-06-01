#include "estado.h"
#include "stack.h"
#include "interpretador.h"

int main() {
    ESTADO e = {0};
    STACK s = NULL;

    printf("Para comecar a jogar contra outro jogador executar o comando: N <Peca>\n");
    printf("Para comecar a jogar contra um bot executar o comando: A <Peca> <Nivel>\n");
    interpretador(&s,e);
    return 0;
}