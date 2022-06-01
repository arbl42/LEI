/**
 * @file estado.c
 * @brief Definição do estado i.e. tabuleiro. Representação matricial do tabuleiro.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#include <stdlib.h>
#include <string.h>
#include "estado.h"
#include "bot.h"
#include "comando.h"

///Número de linhas e colunas do tabuleiro
#define TABSIZE 8

/**
 * @brief Verifica se um caracter corresponde a alguma peça dos Jogadores.
 * @param p Caracter(maiúsculo) a verificar.
 * @return 1 se o caracter for 'X' ou 'O', 0 caso contrário.
 */
int pecaVal (char p){
    if (p!='X' && p!='O') {
        printf("Erro: Peca invalida\n");
        return 0;
    }
    return 1;
}

/**
 * @brief Recomeça o tabuleiro de modo a ter apenas as peças iniciais.
 * @param e Estado atual.
 * @return Estado com as peças iniciais.
 */
ESTADO tabInicial(ESTADO e){
    int i,j;
    for(i=0;i<TABSIZE;i++) for(j=0;j<TABSIZE;j++) e.grelha[i][j] = VAZIA;
    e.grelha[3][4] = VALOR_O;
    e.grelha[4][3] = VALOR_O;
    e.grelha[3][3] = VALOR_X;
    e.grelha[4][4] = VALOR_X;
    return e;
}

/**
 * @brief Imprime no ecrã o tabuleiro.
 * @param e Estado atual.
 */
void printa(ESTADO e) {
    int i,j;
    char c;
    printf("  ");
    for (j=1;j < TABSIZE+1; j++) printf("%d ",j);               //mostrar indices das colunas
    putchar('\n');
    for (i = 0; i < TABSIZE; i++) {
        printf("%d ",i+1);                                      //mostrar indices das linhas
        for (j = 0; j < TABSIZE; j++) {
            switch (e.grelha[i][j]) {
                case VALOR_O:
                    c = 'O';
                    break;
                case VALOR_X:
                    c = 'X';
                    break;
                default:
                    c = '-';
                    break;
            }
            printf("%c ", c);
        }
        printf("\n");
    }
}

/**
 * @brief Lê a primeira linha de um ficheiro, guardando os dados nos sitios adequados.
 * @param e Estado onde vai colocar os novos dados lidos (modo/nível e peça).
 * @param f Apontador para o inicio do ficheiro de onde vai ler.
 * @return Estado com os novos dados.
 */
ESTADO ler1linha(ESTADO e,FILE *f){
    char c = fgetc(f);                                          //Modo
    e.modo = c == 'M' ? '0' : 'A';                              //Modo 'A' apenas temporario
    fgetc(f);                                                   //Ignorar espaco
    c = fgetc(f);                                               //Proxima Peca
    e.peca = c == 'X' ? VALOR_X : VALOR_O;
    if(e.modo != '0') {
        fgetc(f);                                               //Ignorar espaco
        e.modo = fgetc(f);                                      //Modo (Nivel)
    }
    while(c!='\n') c = fgetc(f);                                //Ignorar mudanca de linha
    return e;                                                   //Devolver estado
}

/**
 * @brief Lê o tabuleiro de um ficheiro, guardando os dados nos sitios adequados.
 * @param e Estado onde vai colocar os novos dados lidos (tabuleiro).
 * @param f Apontados para a segunda linha(inicio do tabuleiro) do ficheiro onde vai ler.
 * @return Estado com os novos dados.
 */
ESTADO lerTabuleiro(ESTADO e,FILE *f){
    char c;
    int a,b;
    for(a=0;a<TABSIZE;a++) {
        for(b=0;b<TABSIZE;b++) {
            switch (fgetc(f)){
                case 'X':
                    e.grelha[a][b] = VALOR_X;
                    break;
                case 'O':
                    e.grelha[a][b] = VALOR_O;
                    break;
                default:
                    e.grelha[a][b] = VAZIA;
                    break;
            }
            c = fgetc(f);                                       //Avancar espaco ou mudanca de linha
        }
        while(c!='\n' && c!=EOF) c = fgetc(f);                  //Avancar caso haja mais algo na linha
    }
    return e;
}

/**
 * @brief Escreve os dados correspondentes à primeira linha num ficheiro.
 * @param e Estado que contem os dados (modo/nível e peça) a escrever.
 * @param f Apontador para o inicio do ficheiro onde se vai escrever.
 */
void escrever1linha(ESTADO e,FILE *f){
    char proxpeca = e.peca == VALOR_X ? 'X' : 'O';              //#1 peca a jogar
    if(e.modo == '0') fprintf(f,"M %c\n",proxpeca);             //Se manual: M <peca>
    else fprintf(f,"A %c %c\n",proxpeca,e.modo);                //Se automático: A <peca> <nivel>
}

/**
 * @brief Escreve os dados correspondentes ao tabuleiro num ficheiro.
 * @param e Estado que contem os dados (tabuleiro) a escrever.
 * @param f Apontador para a segunda linha (inicio do tabuleiro) onde se vai escrever.
 */
void escreverTabuleiro(ESTADO e,FILE *f){
    int l,c;
    for(l=0;l<TABSIZE;l++) {
        for(c=0;c<TABSIZE;c++) {
            switch (e.grelha[l][c]){
                case VALOR_X:
                    fputc('X',f);
                    break;
                case VALOR_O:
                    fputc('O',f);
                    break;
                default:
                    fputc('-',f);
                    break;
            }
            if (c<TABSIZE-1)fputc(' ',f);                       //Intervalo de espacos
            else fputc('\n',f);                                 //O ultimo nao leva espaco mas muda de linha
        }
    }
}

/**
 * @brief Verifica se uma posição pertence ao tabuleiro.
 * @param l Linha (de 0 a 7) da posição.
 * @param c Coluna (de 0 a 7) da posição.
 * @return 1 caso pertença, 0 caso contrário.
 */
int inTab (int l, int c) {
    return (l >= 0 && l < TABSIZE && c >= 0 && c < TABSIZE);
}

/**
 * @brief Indica a peça simétrica (contrária).
 * @param v Peça atual.
 * @return 'X' se a peça dada for 'O', 'O' se a peça dada for 'X', Vazia se nenhum dos anteriores.
 */
VALOR pecaSim (VALOR v) {
    if (v==VALOR_O) return VALOR_X;
    if (v==VALOR_X) return VALOR_O;
    return VAZIA;
}

/**
 * @brief Verifica se é possivel a próxima jogada ser numa dada posição.
 *
 * Apenas verifica se está dentro do tabuleiro e ainda não está ocupada, depois apenas chama a função
 * 'posPossivelDir' para todas as direções.
 *
 * @param e Estado atual.
 * @param l Linha (de 0 a 7) da posição a verificar.
 * @param c Coluna (de 0 a 7) da posição a verificar.
 * @return 1 caso seja possível, 0 caso contrário.
 * @see posPossivelDir
 */
int posPossivel (ESTADO e, int l, int c) {
    if (!inTab(l,c)) return 0;                                  //Nao pertence ao tabuleiro
    if (e.grelha[l][c] != VAZIA) return 0;                      //Ja esta ocupada
    return (posPossivelDir(e,l,c,0,1) || posPossivelDir(e,l,c,1,1) || posPossivelDir(e,l,c,1,0) ||
            posPossivelDir(e,l,c,1,-1) || posPossivelDir(e,l,c,0,-1) || posPossivelDir(e,l,c,-1,-1) ||
            posPossivelDir(e,l,c,-1,0) || posPossivelDir(e,l,c,-1,1));
}

/**
 * @brief Verifica se é possivel jogar tendo apenas em conta uma direção.
 *
 * Uma posição é possível caso, dada um direção, a posição seguinte tem uma peça contrária e, ao percorrer a direção
 * enquanto as peças são contrárias, a peça na posição final (dentro do tabuleiro) for igual à qual se pretende jogar.
 *
 * @param e Estado atual.
 * @param l Linha (de 0 a 7) da posição a verificar.
 * @param c Coluna (de 0 a 7) da posição a verificar.
 * @param lv Linha do vetor direção.
 * @param cv Coluna do vetor direção.
 * @return 1 caso seja possivel, 0 caso contrário.
 */
int posPossivelDir(ESTADO e,int l,int c, int lv, int cv) {
    l += lv;                                                    //Soma as coordenadas do vetor a posicao
    c += cv;
    if(!inTab(l,c) || e.grelha[l][c] != pecaSim(e.peca))        //Verifica se a posicao resultante pertence ao
        return 0;                                               // tabuleiro e se e "simetrica"
    while (inTab(l,c) && e.grelha[l][c] == pecaSim(e.peca)) {   //Se for continua a somar o vetor ate nao ser mais
        l += lv;
        c += cv;
    }
    return(inTab(l,c) && e.grelha[l][c]==e.peca);               //Se a posicao final pertencer ao tabuleiro e for
                                                                // igual, e valida(1), caso contrario, nao e valida (0)
}

/**
 * @brief Virar todas as peças necessárias numa direção ao fim de uma jogada.
 *
 * Chama a funcao 'virarPecasDir' para todas as direcões necessárias (possiveis).
 *
 * @param e Estado atual.
 * @param l Linha (de 0 a 7) onde foi efetuada a jogada.
 * @param c Coluna (de 0 a 7) onde foi efetuada a jogada.
 * @return Estado com as respetivas peças alteradas.
 * @see posPossivelDir, virarPecasDir
 */
ESTADO virarPecas (ESTADO e, int l, int c) {
    if(posPossivelDir(e,l,c,1,1)) e = virarPecasDir(e,l,c,1,1);
    if(posPossivelDir(e,l,c,1,0)) e = virarPecasDir(e,l,c,1,0);
    if(posPossivelDir(e,l,c,1,-1)) e = virarPecasDir(e,l,c,1,-1);
    if(posPossivelDir(e,l,c,0,1)) e = virarPecasDir(e,l,c,0,1);
    if(posPossivelDir(e,l,c,0,-1)) e = virarPecasDir(e,l,c,0,-1);
    if(posPossivelDir(e,l,c,-1,1)) e = virarPecasDir(e,l,c,-1,1);
    if(posPossivelDir(e,l,c,-1,0)) e = virarPecasDir(e,l,c,-1,0);
    if(posPossivelDir(e,l,c,-1,-1)) e = virarPecasDir(e,l,c,-1,-1);
    return e;
}

/**
 * @brief Virar as peças necessárias numa direção ao fim de uma jogada.
 *
 * A função só é chamada quando se sabe que a a direcão em questão é possível, por isso começa desde inicio a
 * substituir as peças na direção.
 *
 * @param e Estado atual.
 * @param l Linha (de 0 a 7) onde foi efetuada a jogada.
 * @param c Coluna (de 0 a 7) onde foi efetuada a jogada.
 * @param lv Linha do vetor direção.
 * @param cv Coluna do vetor direção.
 * @return Estado com as respetivas peças alteradas.
 */
ESTADO virarPecasDir (ESTADO e, int l, int c, int lv, int cv) {
    l += lv;                                                    //Soma as coordenadas do vetor a posicao
    c += cv;
    while(e.grelha[l][c] == pecaSim(e.peca)) {                  //Enquanto for simetrica, troca
        e.grelha[l][c] = e.peca;
        l += lv;
        c += cv;
    }
    return e;
}

/**
 * @brief Efetua uma jogada válida.
 *
 * Coloca a peça no sítio indicado, inverte as peças necessárias e inverte a próxima peça (este último apenas no caso
 * de estar no modo manual).
 *
 * A função só é chamada quando se sabe que é uma jogada possível.
 *
 * @param e Estado atual.
 * @param l Linha (de 0 a 7) onde foi efetuada a jogada.
 * @param c Coluna (de 0 a 7) onde foi efetuada a jogada.
 * @return Estado com as devidas alterações.
 */
ESTADO jogar (ESTADO e,int l, int c){
    e.grelha [l][c] = e.peca;                                   //Coloca a peça no tabuleiro
    e = virarPecas(e,l,c);                                      //Virar as pecas necessárias
    if(e.modo == '0') e.peca = pecaSim(e.peca);                 //Modifica a peca para o proximo jogador(caso Manual)
    return e;
}

/**
 * @brief Num jogo entre dois jogadores (não ro'bot's), verifica se deve saltar a próxima jogada ou até mesmo acabar
 * o jogo.
 *
 * Se saltar a jogada muda a próxima peça.
 *
 * Se acabar o jogo mostra a tela final.
 *
 * Se nenhum dos anteriores, devolve o estado inalterado.
 *
 * @param e Estado atual.
 * @return Estado alterado se saltar a próxima jogada e inalterado caso contrário.
 */
ESTADO skipJogvsJog (ESTADO e) {
    if(numJogPos(e))                                            //Verifica se o próximo jogador
        return e;                                               // ainda pode continuar a jogar
    e.peca = pecaSim(e.peca);
    if(numJogPos(e)) {                                          //Depois de saltar a jogada,
        printf("Saltar Jogada!\n");                             // verifica se o jogador atual pode jogar
        return e;
    }
    e.peca = pecaSim(e.peca);
    fim(e);
    return e;
}

/**
 * @brief Num jogo entre um jogador e um ro'bot', durante o turno do jogador, verifica se deve saltar a próxima
 * jogada ou até mesmo acabar o jogo.
 *
 * Se saltar a próxima jogada, o estado não sofre alterações, se não, significa que o ro'bot' vai fazer a sua jogada.
 *
 * Se acabar o jogo, mostra a tela final.
 *
 * @param e Estado atual.
 * @return Estado alterado se não saltar a próxima jogada e inalterado caso contrário.
 */
ESTADO skipJogvsBot (ESTADO e) {
    ESTADO ghost = e;
    ghost.peca = pecaSim(e.peca);
    if(numJogPos(ghost))                                        //Verifica se o bot ainda pode continuar a jogar
        return botJogar(e);                                     //Bot joga
    if(numJogPos(e)) {                                          //Depois de saltar a jogada,
        printf("Saltar Jogada do bot!\n");                      // verifica se o Jogador pode jogar
        return e;
    }
    fim(e);
    return e;
}

/**
 * @brief Num jogo entre um jogador e um ro'bot', durante o turno do ro'bot', verifica se deve saltar a próxima
 * jogada ou até mesmo acabar o jogo.
 *
 * Se saltar a próxima jogada, significa que o ro'bot' vai fazer a sua jogada outravez, se não, o estado não sofre
 * alterações.
 *
 * Se acabar o jogo, mostra a tela final.
 *
 * @param e Estado atual.
 * @return Estado alterado se saltar a próxima jogada e inalterado caso contrário.
 */
ESTADO skipBotvsJog (ESTADO e) {
    if(numJogPos(e))                                            //Verifica se o jogador ainda pode
        return e;                                               // continuar a jogar
    ESTADO ghost = e;
    ghost.peca = pecaSim(e.peca);
    if(numJogPos(ghost)) {                                      //Depois de saltar a jogada,
        printf("Saltar Jogada do Jogador!\n");                  // verifica se o Bot pode jogar
        return botJogar(e);
    }
    fim(e);
    return e;
}

/**
 * @brief Mensagem de fim de jogo.
 * @param e Estado atual.
 */
void fim (ESTADO e) {
    SCORE s = contagemScore(e);
    printf("Fim do jogo\n");
    printf("X: %d peça(s)\n",s.X);
    printf("O: %d peça(s)\n",s.O);
    if(s.X == s.O) printf("Empate!\n");
    else if(s.X > s.O) printf("Vitória: X\n");
    else if(s.X < s.O) printf("Vitória: O\n");
}

/**
 * @brief Indica o número de jogadas que o próximo jogador pode efetuar.
 * @param e Estado atual.
 * @return Número de jogadas que o próximo jogador pode efetuar.
 */
int numJogPos (ESTADO e) {
    int i,j;
    int c=0;
    for(i=0;i<TABSIZE;i++)
        for(j=0;j<TABSIZE;j++)
            if(posPossivel(e,i,j)) c++;
    return c;
}

/**
 * @brief Indica o número de peças atual de cada jogador.
 * @param e Estado atual.
 * @return Estrutura que contem o número de peças atual de cada jogador.
 */
SCORE contagemScore (ESTADO e) {
    SCORE s;
    s.X = 0;
    s.O = 0;
    int i,j;
    for(i=0;i<TABSIZE;i++) {
        for(j=0;j<TABSIZE;j++){
            switch (e.grelha[i][j]){
                case VALOR_X:
                    s.X++;
                    break;
                case VALOR_O:
                    s.O++;
                    break;
                default:
                    break;
            }
        }
    }
    return s;
}

/**
 * @brief Mostra ao utilizador os resultados finais do jogo e cria um ficheiro com o novo nome indicando o resultado.
 * @param e Estado atual do jogo
 * @param file Nome do ficheiro do jogo.
 */
void acabarCamp(ESTADO e, char file[]){
    SCORE s = contagemScore(e);
    printf("Fim do jogo\n");
    printf("X: %d peça(s)\n",s.X);
    printf("O: %d peça(s)\n",s.O);
    if(s.X == s.O) {
        printf("Empate!\n");
        strcat(file,".g-");
    }
    else if(s.X > s.O) {
        printf("Vitória: X\n");
        strcat(file,".gX");
    }
    else if(s.X < s.O) {
        printf("Vitória: O\n");
        strcat(file,".gO");
    }
    cmd_E(e,file);
}
/**
 * @brief Cria um elemento POSICAO com os dados fornecidos.
 * @param l Linha (de 0 a 7) a guardar.
 * @param c Coluna (de 0 a 7) a guardar.
 * @param next Resto da lista.
 * @return Nova lista.
 */
POSICAO newLP (int l,int c,POSICAO next){
    POSICAO new = malloc(sizeof(struct posicao));
    new->l = l;
    new->c = c;
    new->next = next;
    return new;
}

/**
 * @brief Cria uma lista das posicões onde é possivel jogar.
 * @param e Estado atual do jogo.
 * @return A lista de posições onde é possivel jogar.
 */
POSICAO listaPos (ESTADO e){
    int l,c;
    POSICAO start = NULL,*temp = &start;
    for (l=0;l<TABSIZE;l++)
        for (c=0;c<TABSIZE;c++)
        {
            if (!posPossivel(e,l,c)) continue;                  //Se nao e possivel, ignora
            *temp = newLP(l,c,NULL);                            //Cria um novo
            temp = &((*temp)->next);                            //Avancar para o próximo
        }
    return start;
}

/**
 * @brief Mostra a lista de posições onde é possível jogar.
 *
 * Esta função é apenas utilizada em testes.
 *
 * @param p Lista de posicoes a ser imprimida.
 */
void displayLP (POSICAO p){
    while(p){
        printf("(%d,%d)\n",p->l+1,p->c+1);                      //Mostrar coordenada [+1 porque a grelha
        p = p->next;                                            // comeca em (0,0) e o tabuleiro em (1,1)]
    }
}

/**
 * @brief Retira o primeiro elemento de uma lista de posições(se existente), libertando a sua memória.
 * @param p Lista de posições de onde vai ser retirado o elemento.
 */
void free1LP (POSICAO *p){
    if(!(*p)) return;
    POSICAO temp = (*p)->next;
    free(*p);
    *p=temp;
}

/**
 * @brief Liberta a memória alocada por uma lista de posições.
 * @param p Lista de posições a ser libertada.
 */
void freeLP (POSICAO p){
    while (p)
    {
        POSICAO temp = p->next;
        free(p);
        p = temp;
    }
}