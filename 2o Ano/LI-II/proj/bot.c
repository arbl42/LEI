/**
 * @file bot.c
 * @brief Definições dos ro'bot's.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#include <stdlib.h>
#include "bot.h"

///Número de linhas e colunas do tabuleiro.
#define TABSIZE 8

/**
 * @brief Função que chama o bot de nível a ser utilizado e efetua a jogada na posição que este "decide".
 * @param e Estado atual.
 * @return Estado depois da jogada do ro'bot'.
 */
ESTADO botJogar (ESTADO e){
    POSICAO j;
    ESTADO temp = e;                                            //Estado temporario onde vai ser alterada a proxima
    temp.peca = pecaSim(e.peca);                                // peca para poder mudar as respetivas pecas na grelha
    switch(e.modo){                                             //Escolher qual o bot a jogar
        case '1':
            j = bot1(temp);
            break;
        case '2':
            j = bot2(temp);
            break;
        case '3':
            j = bot3(temp);
            break;
        default:                                                //Caso seja manual ou invalido,
            return e;                                           // nao fazer nada
    }
    if(!j) return e;                                            //Nunca deve acontecer, mas para prevenir
    e = jogar(temp,j->l,j->c);                                  //Faz a jogada propriamente dita
    e.peca = pecaSim(e.peca);                                   //Peca volta ao original
    printf("Jogada do bot:\n");
    printa(e);                                                  //Mostrar tabuleiro
    e = skipBotvsJog(e);                                        //Verificar se salta jogada ou ate acaba
    return e;
}

/**
 * @brief Jogada que o ro'bot' de nível 1 escolhe.
 *
 * Este ro'bot' escolhe uma jogada à sorte de entre as possiveis.
 *
 * @param e Estado atual.
 * @return Posição que o ro'bot' "escolheu".
 */
POSICAO bot1(ESTADO e){
    int max = numJogPos(e);
    if(!max) return NULL;                                       //Parar caso nao haja jogadas
    int r = rand_between(0,max);                                //Escolher uma jogada "random"
    POSICAO lp = listaPos(e);
    int i;
    for(i=0;i<r;i++) free1LP(&lp);                              //Ir ate jogada escolhida
    POSICAO j = newLP(lp->l,lp->c,NULL);                        //Devolver jogada escolhida
    freeLP(lp);                                                 //Libertar o resto da lista
    return j;
}

/**
 * @brief Função que devolve um inteiro de entre um intervalo de valores.
 * @param min Limite inferior do intervalo.
 * @param max Limite superior do intervalo.
 * @return Valor entre o mínimo(incluindo) e o máximo dados.
 */
int rand_between(int min, int max){
    return rand() % (max-min) + min;
}

/**
 * @brief Jogada que o ro'bot' de nível 2 escolhe.
 *
 * Este ro'bot' escolhe a jogada que lhe dá mais pontuação, tendo por base uma tabela predefinida.
 *
 * @param e Estado atual.
 * @return Posição que o ro'bot' "escolheu".
 */
POSICAO bot2(ESTADO e){
    POSICAO lp = listaPos(e);                                   //Posicoes possiveis
    if(!lp) return NULL;                                        //Parar caso nao haja jogadas
    POSICAO jog = newLP(lp->l,lp->c,NULL);                      //Melhor jogada
    int max = bot2worth(lp,e);                                  //Pontuacao da melhor jogada (por agora, a primeira)
    free1LP(&lp);                                               //Retirar a primeira jogada, pois ja foi testada
    while(lp){                                                  //Percorrer lista
        if(max<bot2worth(lp,e)){                                //Alterar melhor jogada, ao encontrar uma ainda melhor
            max = bot2worth(lp,e);
            jog->l = lp->l;
            jog->c = lp->c;
        }
        free1LP(&lp);                                           //Libertar jogada testada
    }
    return jog;                                                 //Devolver melhor jogada
}

/**
 * @brief Faz a diferença de pontuações do ro'bot' e do jogador, tendo em conta uma jogada, e utilizando como forma
 * de medir a pontuação, uma tabela predefinida (tabVal).
 * @param p Lista de posições possiveis, sendo apenas a primeira testada.
 * @param e Estado antes da jogada a ser testada.
 * @return Diferença de pontuações depois da jogada.
 */
int bot2worth(POSICAO p,ESTADO e){
    if(p) e = jogar(e,p->l,p->c);                               //Faz a jogada a testar
    const int tabVal[8][8] = {{120,-20, 20,  5,  5, 20,-20,120},//Matriz com os valores de cada posicao
                              {-20,-40, -5, -5, -5, -5,-40,-20},
                              { 20, -5, 15,  3,  3, 15, -5, 20},
                              {  5, -5,  3,  3,  3,  3, -5,  5},
                              {  5, -5,  3,  3,  3,  3, -5,  5},
                              { 20, -5, 15,  3,  3, 15, -5, 20},
                              {-20,-40, -5, -5, -5, -5,-40,-20},
                              {120,-20, 20,  5,  5, 20,-20,120}};
    int x=0,o=0;                                                //Contadores para as pecas
    int i,j;
    for(i=0;i<TABSIZE;i++){
        for(j=0;j<TABSIZE;j++){
            if(e.grelha[i][j]==VALOR_X) x+= tabVal[i][j];       //Contar pontuacao da peca x
            else if(e.grelha[i][j]==VALOR_O) o += tabVal[i][j]; //Contar pontuacao da peca o
        }
    }
    if(e.peca == VALOR_X) return x-o;                           //Devolver diferenca, consoante a peca pretendida
    return o-x;                                                 // (proxima peca)
}

/**
 * @brief Jogada que o ro'bot' de nível 3 escolhe.
 *
 * Este ro'bot' escolhe a jogada tendo em conta um algoritmo minMax, ou seja, de modo em que no pior dos casos, a
 * jogada posterior realizada pelo oponente leve à menor perda possível (tendo por base a tabela do bot 2).
 *
 * @param e Estado atual.
 * @return Jogada que o ro'bot' escolheu.
 */
POSICAO bot3(ESTADO e){
    if(!numJogPos(e)) return NULL;                              //Se nao ha jogada para
    rTTree r = buildrTTree(e);                                  //Constroi a arvore de jogadas
    menor2jogada(&r);                                           //Reduz tamanho da arvore
    return escolhaminMax(r);                                    //Devolve a escolha
}

/**
 * @brief Constrói uma "árvore" de posicões.
 *
 * Coloca em cada nodo uma posição possivel ao jogador atual e coloca como lista de posições, as posições possiveis
 * ao próximo jogador caso fosse efetuada a jogada na posição do nodo.
 *
 * @param e Estado atual.
 * @return Árvore criada.
 * @see trepadeira
 */
TTree buildTTree (ESTADO e){
    TTree t, *p = &t;
    POSICAO lp = listaPos(e);                                   //Lista de posicoes para o proprio
    while(lp){
        *p = malloc(sizeof(struct trepadeira));                 //Informacoes da posicao no nodo
        (*p)->posicao = newLP(lp->l,lp->c,NULL);
        ESTADO temp = jogar(e,lp->l,lp->c);                     //Simular jogada
        temp.peca = pecaSim(temp.peca);
        (*p)->lp=listaPos(temp);                                //Lista das posicoes derivadas da simulacao
        p = &((*p)->prox);
        free1LP(&lp);                                           //Liberta a posicao(inicial) ja copiada e simulada
    }
    *p = NULL;
    return t;
}

/**
 * @brief Constrói uma "árvore" de posições, iniciada por um estado.
 *
 * Apenas põe o estado recebido no inicio e chama a função buildTTree.
 *
 * @param e Estado atual.
 * @return Árvore criada.
 * @see raizTrepadeira, builtTTree
 */
rTTree buildrTTree (ESTADO e){
    rTTree r;
    r.estado = e;
    r.prox = buildTTree(e);
    return r;
}

/**
 * @brief Reduz uma lista de posições a uma só posição, posição esta que é considerada a melhor (segundo a pontuação
 * do bot 2) caso seja efetuada uma jogada prévia (também indicada).
 * @param e Estado atual.
 * @param jog1 'Jogada prévia'.
 * @param jog2 Antes: Lista de todas as jogadas possíveis. Depois: Lista apenas com a melhor jogada.
 */
void maiorScorePos (ESTADO e, POSICAO jog1, POSICAO *jog2){
    if(!(*jog2)) return;
    e = jogar(e,jog1->l,jog1->c);                               //Mudar o estado para caso o jogador atual jogue
    e.peca = pecaSim(e.peca);                                   // nesta posicao
    int max = -512;
    POSICAO jog = malloc(sizeof(struct posicao));               //Jogada a qual vai ficar reduzida a lista de jogadas
    jog->next = NULL;                                           // do oponente (melhor escolha para o oponente)
    while(*jog2){
        int c = bot2worth(*jog2,e);                             //Pontuacao segundo tabela do bot 2
        if(max < c){                                            //Se for melhor, mudar dados da lista resultado
            max = c;
            jog->l = (*jog2)->l;
            jog->c = (*jog2)->c;
        }
        free1LP(jog2);                                          //Libertar jogada do oponente ja testada
    }
    *jog2 = jog;                                                //Colocar a melhor jogada na lista das jogadas do
                                                                // jogador 2(vazia)
}

/**
 * @brief Reduz o tamanho (caso seja maior que 1) da lista de posições contida em cada nodo de uma árvores de posições.
 *
 * Chama a função 'maiorScorePos' para todas as sub-árvores 'trepadeira'.
 *
 * @param r Antes: Árvore em que cada sub-árvore 'trepadeira' possui uma lista com todas as posições possiveis
 * derivadas. Depois: Árvore em a lista de posições foi reduzida a uma posição.
 * @see maiorScorePos
 */
void menor2jogada (rTTree *r){
    TTree *t = &(r->prox);                                      //Apontador para a primeira arvore 'trepadeira'
    while(*t) {
        maiorScorePos(r->estado,(*t)->posicao,&((*t)->lp));     //Reduzir tamanho da 'trepadeira' apontada
        t = &((*t)->prox);                                      //Repetir para a proxima
    }
}

/**
 * @brief Decide de entre as folhas de uma árvore 'raizTrepadeira' reduzida (depois da atuação de 'menor2jogada')
 * qual a que implica a uma perda menor/ganho maior (se possivel), jogando na árvore 'trepadeira' em que esta folha
 * está contida.
 * @param r Árvore 'raizTrepadeira' a analisar.
 * @return Jogada escolhida.
 */
POSICAO escolhaminMax (rTTree r){
    ESTADO e = r.estado;
    POSICAO jog = malloc(sizeof(struct posicao));               //Posicao a devolver
    jog->next = NULL;
    int max = -1024;
    TTree *t = &(r.prox);                                       //Apontador para a primeira arvore 'trepadeira'
    while(*t){
        ESTADO temp=jogar(e,(*t)->posicao->l,(*t)->posicao->c); //Simular jogar na posicao apontada
        temp.peca = pecaSim(temp.peca);                         //Mudar peca para a proxima jogada
        int bonus;                                              //Bonus caso faca saltar jogada
        if((*t)->lp) {
            temp = jogar(temp,(*t)->lp->l,(*t)->lp->c);         //Jogar a jogada seguinte, se existente
            bonus = 0;                                          //Nao salta jogada, nao ha bonus
        }
        else bonus = 64;                                        //Bonus por saltar jogada
        temp.peca = pecaSim(temp.peca);
        int c = bot2worth(NULL,temp) + bonus;                   //Diferenca de pecas da prespetiva do #1 jogador
        if (max < c){                                           //Se for melhor, altera os dados da posicao a devolver
            max = c;
            jog->l = (*t)->posicao->l;
            jog->c = (*t)->posicao->c;
        }
        free1TTree(t);                                          //Liberta ramo testado e avanca para o proximo
    }
    return jog;                                                 //Devolve jogada escolhida
}

/**
 * @brief Liberta todo o espaço ocupado por um nodo de uma árvore 'trepadeira', ficando assim a apontar para o nodo
 * seguinte.
 * @param t Árvore a libertar.
 */
void free1TTree (TTree *t){
    if(!(*t)) return;                                           //Se nao ha nada para libertar para
    freeLP((*t)->posicao);                                      //Libertar componentes
    freeLP((*t)->lp);
    TTree temp = (*t)->prox;
    free(*t);
    *t = temp;                                                  //Por a apontar para o proximo
}