/**
 * @file comando.c
 * @brief Definições dos comandos disponíveis ao utilizador.
 * @authors Grupo 4 - PL1
 * @author Romeu Silva - A89617
 * @author Ariana Lousada - A87998
 * @author Ricardo Carvalho - A89504
*/

#include <string.h>
#include "comando.h"
#include "bot.h"

///Número de linhas e colunas do tabuleiro
#define TABSIZE 8

/**
 * @brief Comando a partir do qual o jogador começa um novo jogo manual, indicando a primeira peça a jogar.
 *
 * Utilização: N <peça>
 *
 * A função começa por testar se a peça escolhida pelo jogador é válida (se corresponde a 'X' ou a 'O').
 *
 * Caso não o seja, a função retorna o estado recebido sem quaisquer alterações.
 *
 * No caso de ser válida, prossegue-se para um jogo no modo manual (jogador contra jogador), apagando os salvamentos
 * prévios de estados(stack).
 *
 * @param s Apontador para a pilha de estados que apareceram durante o correr do jogo atual, ordenados do mais recente
 * para o mais antigo. Se a peça for válida, a stack depois da utilização do comando apenas terá o estado inicial.
 * @param e Estado atual do jogo.
 * @param arg Peça escolhida pelo jogador como sendo a primeira a jogar.
 * @return e Estado do jogo após as alterações(se existentes) feitas a partir deste comando.
 */
ESTADO cmd_N (STACK *s,ESTADO e,char arg) {
    if (!pecaVal(arg)) return e;                                //Verificar se a peca e valida
    e.modo = '0';                                               //Modo de jogo. Manual (0)
    e.peca = (arg == 'X') ? VALOR_X : VALOR_O;                  //Proxima peca
    e = tabInicial(e);                                          //Coloca as pecas no tabuleiro
    resetStack(s);                                              //Eliminar tudo o que foi feito ate agora
    push(s,e);                                                  //Guardar na stack
    printf("Inicio do jogo:\n");
    printa(e);                                                  //Mostrar tabuleiro
    return e;
}

/**
 * @brief Comando para ler um ficheiro de jogo.
 *
 * Utilização: L <ficheiro>
 *
 * A função começa por verificar se o ficheiro escolhido existe.
 *
 * Caso não exista, é retornado o estado recebido pela função sem quaisquer alterações.
 *
 * Se existir, a função lê o ficheiro, guardando os dados do estado nos sitios correspondentes.
 *
 * @param s Apontador para a pilha de estados que apareceram durante o correr do jogo atual, ordenados do mais recente
 * para o mais antigo. Se o ficheiro existir, a stack depois da utilização do comando apenas terá o estado lido.
 * @param e Estado atual do jogo.
 * @param arg Nome do ficheiro indicado pelo utilizador.
 * @return e Estado do jogo alterado (caso o ficheiro exista).
 * @see ler1linha, lerTabuleiro.
 */
ESTADO cmd_L (STACK *s,ESTADO e,char arg[]) {
    FILE *f = fopen(arg,"r");
    if (f==NULL) {                                              //Verificar se o ficheiro existe
        printf("Ficheiro não encontrado!\n");
        return e;
    }
    e = ler1linha(e,f);                                         //Le a primeira linha do ficheiro
    e = lerTabuleiro(e,f);                                      //Le o tabuleiro
    fclose(f);                                                  //Fecha o ficheiro
    printa(e);                                                  //Mostrar tabuleiro
    e = e.modo == '0' ? skipJogvsJog(e) : skipBotvsJog(e);      //Verificar se salta ou ate acaba
    resetStack(s);                                              //Eliminar tudo o que foi feito ate agora
    push(s, e);                                                 //Guardar na stack
    printf("Jogo carregado com sucesso!\n");
    return e;
}

/**
 * @brief Comando para escrever o estado do jogo num determinado ficheiro.
 *
 * Utilização: E <ficheiro>
 *
 * @param e Estado atual do jogo.
 * @param arg Nome do ficheiro onde vai ser guardado o estado.
 * @see escrever1linha, escreverTabuleiro
 */
void cmd_E (ESTADO e,char arg[]) {
    if(e.peca==0){                                              //Apenas no inicio, quando nao foi iniciado jogo
        printf("Não existe jogo criado para guardar!\n");
        printf("Comando não executado\n");
        return;
    }
    FILE *f = fopen(arg,"w");
    if (f==NULL) {
        printf("ERRO!\n");
        return;
    }
    escrever1linha(e,f);                                        //Escreve a primeira linha do ficheiro
    escreverTabuleiro(e,f);                                     //Escreve o tabuleiro
    fclose(f);                                                  //Fecha o ficheiro
    printf("Jogo guardado com sucesso!\n");
}

/**
 * @brief Comando para jogar numa determinada posição (linha,coluna).
 *
 * Utilização: J <Linha> <Coluna>
 *
 * A função começa por avaliar se a jogada é válida. Caso não o seja, retorna o estado recebido sem quaisquer
 * alterações. Se a jogada for válida, a peça anteriormente escolhida pelo jogador é colocada na posição (l,c), e são
 * alteradas todas as peças necessárias de acordo com as regras do jogo.
 *
 * @param s Apontador para a pilha de estados que apareceram durante o correr do jogo atual, ordenados do mais recente
 * para o mais antigo. Se a jogada for válida, adiciona o novo estado ao topo da stack.
 * @param e Estado do jogo atual.
 * @param l Linha da posição a jogar (entre 1 e 8).
 * @param c Coluna da posição a jogar (entre 1 e 8).
 * @return e Estado do jogo alterado(caso a jogada seja válida).
 */
ESTADO cmd_J (STACK *s,ESTADO e,int l,int c){
    if (!(posPossivel(e,l,c))){                                 //Joga apenas se a jogada for possivel
        printf("Jogada Invalida\n");
        return e;
    }
    if(e.modo != '0') printf("A sua jogada:\n");
    e = jogar(e,l,c);                                           //Faz a jogada propriamente dita
    printa(e);                                                  //Mostrar tabuleiro
    e = e.modo == '0' ? skipJogvsJog(e) : skipJogvsBot(e);      //Verificar se salta ou ate acaba (e jogada do bot)
    if(!push(s,e)) {                                            //Guardar na stack
        printf("Erro: Espaco cheio");                           //Caso nao haja mais espaco para guardar
        return e;
    }
    return e;
}

/**
 * @brief Comando que indica as posições de jogada válida (com '.').
 *
 * Utilização: S
 *
 * Esta função de maneira similar à função 'printa', mas antes de imprimir uma posição, verifica se esta é válida
 * para a próxima peça(dado armazenado no estado) e caso o seja imprime um '.' ao invés do '-'.
 *
 * @param e Estado atual do jogo.
 */
void cmd_S (ESTADO e) {
    int i,j;
    char c;
    printf("  ");
    for (i=1;i < TABSIZE+1; i++) printf("%d ",i);               //Mostrar indices das colunas
    putchar('\n');
    for (i = 0; i < TABSIZE; i++) {
        printf("%d ",i+1);                                      //Mostrar indices das linhas
        for (j = 0; j < TABSIZE; j++) {
            if (posPossivel(e,i,j)) c='.';                      //Colocar os respetivos caracteres
            else switch (e.grelha[i][j]) {
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
        putchar('\n');
    }
}

/**
 * @brief Comando que oferece uma sugestão de jogada (a melhor jogada possível de acordo com o bot3).
 *
 * Utilização: H
 *
 * A função começa por verificar se existem jogadas possíveis no tabuleiro. Caso não existam, a função imprime o
 * tabuleiro normalmente (função 'printa'). Caso existam, a função seleciona a jogada de acordo com o bot3 para e
 * coloca um '?' nessa posição.
 *
 * @param e Estado atual do jogo.
 */
void cmd_H (ESTADO e){
    POSICAO s = bot3(e);
    if(!s) {                                                    //Caso nao haja jogadas possiveis(nunca deve
        printa(e);                                              // acontecer mas para prevenir), mostra tabuleiro
        return;
    }
    int i,j;
    char c;
    printf("  ");
    for (j=1;j < TABSIZE+1; j++) printf("%d ",j);               //Mostrar indices das colunas
    putchar('\n');
    for (i = 0; i < TABSIZE; i++) {
        printf("%d ",i+1);                                      //Mostrar indices das linhas
        for (j = 0; j < TABSIZE; j++) {
            if(s->l == i && s->c == j) c = '?';                 //Mostrar sugestao
            else switch (e.grelha[i][j]) {                      //Mostrar resto dos caracteres
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
    freeLP(s);
}

/**
 * @brief Comando que anula a última jogada executada.
 *
 * Utilização: U
 *
 * Esta função utiliza a stack e começa por testar se é possivel voltar atrás, isto é, se a stack tem mais que um
 * elemento. Caso seja, elimina o que está no topo da stack, isto é, a última jogada efetuada. Isto é possível fazer
 * até o estado ser igual ao estado inicial do jogo, ou estado obtido pela leitura do ficheiro, caso tenha sido
 * iniciado dessa maneira.
 *
 * @param s Apontador para a pilha de estados que apareceram durante o correr do jogo atual, ordenados do mais recente
 * para o mais antigo. Se a stack tiver mais que um elemento, depois da execução do comando a stack terá menos um
 * elemento(o que estava previamente no topo).
 * @param e Estado atual do jogo (para devolver caso não seja possivel voltar atrás).
 * @return e Estado do jogo alterado(caso tenha sido possivel voltar atrás).
 */
ESTADO cmd_U (STACK *s,ESTADO e) {
    if (pop(s)) peek(*s,&e);                                    //Se a stack nao esta vazia, faz pop e
    else printf("Não é possível voltar mais atrás!\n");         // mostra o que ficou no topo
    printa(e);                                                  //Mostrar tabuleiro resultante
    return e;
}

/**
 * @brief Comando que comeca um novo jogo contra um ro'bot' (de dificuldade 1,2 ou 3).
 *
 * Utilização: A <peca> <nivel>
 *
 * Esta função permite ao jogador começar um modo de jogo automático, no qual tem a opção de escolher um ro'bot' de
 * dificuldade 1(fácil), 2(intermédio) ou 3(difícil).
 *
 * A função começa for testar se a peça escolhida e nível são válidos e caso não sejam, retorna o estado do jogo sem
 * quaisquer alterações. No caso de ser válida, prossegue-se para um jogo no modo automático (jogador contra ro'bot'),
 * apagando os salvamentos prévios de estados(stack).
 *
 * @param s Apontador para a pilha de estados que apareceram durante o correr do jogo atual, ordenados do mais recente
 * para o mais antigo. Se a peça for válida, a stack depois da utilização do comando apenas terá o estado inicial.
 * @param e Estado atual do jogo.
 * @param arg Peça com que o ro'bot' irá jogar.
 * @param level Nível de dificuldade do ro'bot'.
 * @return e Estado do jogo alterado, caso a peça e nivel sejam válidos.
 */
ESTADO cmd_A (STACK *s,ESTADO e,char arg,int level){
    if (!pecaVal(arg)) return e;                                //Verificar se a peca e valida
    switch(level){                                              //Mudar nivel do bot no estado
        case 1:
            e.modo = '1';
            break;
        case 2:
            e.modo = '2';
            break;
        case 3:
            e.modo = '3';
            break;
        default:                                                //Parar caso o nivel nao seja valido
            printf("Erro: Nivel Invalido\n");
            return e;
    }
    e.peca = arg == 'X' ? VALOR_O : VALOR_X;                    //Peca do jogador e a peca contraria a dada
    e = tabInicial(e);                                          //Coloca as pecas no tabuleiro
    resetStack(s);                                              //Eliminar tudo o que foi feito ate agora
    printf("Inicio do Jogo:\n");
    printa(e);                                                  //Mostrar tabuleiro
    if(arg == 'X') e = botJogar(e);                             //Caso o bot seja o X, ele e o primeiro a jogar
    push(s,e);                                                  //Guardar na stack
    return e;
}

/**
 * @brief Comando que inicia/continua um campeonato entre ro'bot's.
 *
 * Utilização: C <ficheiro>
 *
 * Utilização alternativa (caso já tenha sido utilizada a outra): C
 *
 * Este comando tenta ler um ficheiro de jogo, se não for possivel, cria um ficheiro com os dados necessários ao
 * inicio do jogo, caso seja, lê, joga(se possível) de acordo com o bot3 e guarda o jogo. Caso o jogo tenha acabado,
 * guarda o jogo com um novo nome, onde indica quem ganhou.
 *
 * @param n Número de argumentos que o utilizador deu (contando com o C), de modo a saber se foi usada a utilização
 * principal ou a alternativa.
 * @param arg Nome do ficheiro de jogo.
 * @param nomeant Nome do ficheiro de jogo atribuído anteriormente (na sua última utilização).
 */
void cmd_C (int n,char arg[], char nomeant[]){
    if(n==1 && !*nomeant){
        printf("Ainda não foi acessado nenhum ficheiro campeonato!\n");
        return;
    }
    if(n>1) strcpy(nomeant,arg);                                //Caso deia um novo nome
    FILE *f = fopen(nomeant,"r");
    ESTADO e = {0};
    if(!f){                                                     //Caso não exista, criar um novo
        printf("Novo Campeonato\n");
        e.modo = '3';
        e.peca = 'X';
        e = tabInicial(e);
        printa(e);                                              //Mostrar tabuleiro
        cmd_E(e,nomeant);                                       //Escrever no ficheiro
    }
    else{                                                       //Jogar
        e = ler1linha(e,f);                                     //Ler primeira linha do ficheiro
        e = lerTabuleiro(e,f);                                  //Ler tabuleiro do ficheiro
        fclose(f);                                              //Fechar ficheiro
        printf("Tabuleiro recebido\n");
        printa(e);
        POSICAO jog = bot3(e);                                  //Jogada do bot3
        if(jog) e = jogar(e,jog->l,jog->c);                     //Se consegue jogar, joga
        e.peca = pecaSim(e.peca);                               //Mudar peca para o proximo
        printf("Tabuleiro enviado\n");
        printa(e);                                              //Mostrar tabuleiro
        if(!jog && !numJogPos(e))                               //Se nao e possivel ao proprio jogar nem ao oponente
            acabarCamp(e,nomeant);                              //Acaba o jogo
        else cmd_E(e,nomeant);                                  //Se for possivel a algum jogar, guarda normamente
    }
}