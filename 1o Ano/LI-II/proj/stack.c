/**
 * @file stack.c
 * @brief Histórico de estados.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#include <stdlib.h>
#include "stack.h"

/**
 * @brief Insere um estado no topo da stack.
 *
 * A função começa por criar uma zona de alocação de memória e testa se a stack está completa (se já não existe memória
 * para armazenar mais estados). Se esse for o caso, a função retorna 0.
 * Caso contrário, é armazenado um novo estado de jogo (a stack é atualizada) e a função retorna 1.
 *
 * @param s Apontador para o topo da stack.
 * @param e Estado atual do jogo.
 * @return 1 caso a função tenha sido executada corretamente, isto é, a stack possua mais um membro no inicio, 0 caso
 * contrário.
 */
int push(STACK* s,ESTADO e){
    STACK temp = malloc(sizeof(struct Node));                   //criar um novo nodo temp e alocar memoria
    if (!temp) return 0;                                        //verificar se a stack esta cheia “out-of-memory”
    temp->estado = e;                                           //introduzir os dados no respetivo campo em temp
    temp->next = *s;                                            //direcionar o apontador s para temp
    *s = temp;                                                  //tornar temp o topo da stack
    return 1;
}

/**
 * @brief Indica o estado que se encontra no topo da stack (o último que foi armazenado).
 * @param s Stack usada para o "histórico" dos estados do jogo.
 * @param e Apontador para o sitio onde o estado vai ser armazenado.
 * @return 0 caso a stack esteja vazia, 1 caso contrário.
 */
int peek(STACK s,ESTADO* e){
    if(!s) return 0;                                            //verificar se esta vazia
    *e = s->estado;
    return 1;
}

/**
 * @brief Caso exista mais que um elemento na stack, retira o último estado armazenado (topo da stack).
 *
 * Esta função é utilizada no comando U ("Undo" jogada). Se a stack apenas tiver um estado armazenado (isto é,
 * o estado inicial), nada se altera, isto de modo a não apagar o estado inicial da stack caso o utilizador tente
 * voltar mais atrás
 *
 * @param s Apontador para a stack.
 * @return 0 caso a stack não tenha ou tenha apenas um elemento, 1 caso contrário.
 */
int pop(STACK *s){
    if(!(*s) || !((*s)->next)) return 0;                        //verifica se a stack tem mais que um elemento
    STACK temp = (*s)->next;                                    //temp fica a apontar para o nodo seguinte
    free(*s);                                                   //liberta a memoria do atual topo
    *s = temp;                                                  //topo passa a ser temp
    return 1;
}

/**
 * @brief Função que mostra todo o conteúdo da stack.
 *
 * Esta função é apenas utilizada em testes.
 *
 * @param s Stack usada para o "histórico" dos estados do jogo.
 */
void display(STACK s){
    while (s) {                                                 //percorrer stack enquanto esta não é vazia
        printa(s->estado);                                      //imprime o valor do nodo
        s = s->next;                                            //direciona o apontador para next
    }
}

/**
 * @brief Função que apaga todo o conteúdo da stack.
 *
 * Esta função é utilizada quando se começa um novo jogo, ou se carrega um outro ficheiro já anteriormente guardado.
 *
 * @param s Apontador para stack
 */
void resetStack (STACK *s) {
    while (*s) {
        STACK temp = (*s)->next;
        free(*s);
        *s = temp;
    }
    *s = NULL;
}

