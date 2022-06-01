import Exceptions.NoUserAvailableException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;



public class TrazAqui implements Serializable{
    private static final long serialVersionUID = -5461304744388682480L;
    private final Users allUsers;
    private final Encomendas allEncomendas;


    public TrazAqui() {
        this.allUsers = new Users();
        this.allEncomendas = new Encomendas();
    }

    // --------- Metodos de acesso -------------------------------------------

    public Users getAllUsers() {
        return this.allUsers.clone();
    }

    public Encomendas getAllEncomendas() {
        return this.allEncomendas.clone();
    }


    public void addUser(UtilizadorGeral u) {
        this.allUsers.addUser(u.clone());
    }

    
    public void addEncomenda(Encomenda e) {
        this.allEncomendas.addEncomenda(e.clone());
    }

    public static double getRandomNumber(int min, int max){
        return (Math.random()*((max-min)+1))+min;
    }

   


    //--------------------------------------Menu Utilizador-------------------------------------------------------------







   //--Possibilita a opcao de rejeicao da encomenda ao utilizador---------
    public String menuCalcularGastos(String user,Encomenda enc){
        int choice;
        Transportadora l = (Transportadora) this.allUsers.getUser(enc.getcodTransportadora());

        this.allEncomendas.addEncomenda(enc);
        String res;

        long valorTotal = precoEncomenda(enc.getCodEncomenda(),l);
        System.out.println("Preco total da sua encomenda: ");
        System.out.println(valorTotal);
        do {
            System.out.println("Continuar?\n1 - Sim\n2 - Nao");
            choice = Input.lerInt();
        }
        while(choice < 1 || choice > 2);
            if(choice == 1) {
                enc.setCodUtilizador(user);
                this.allEncomendas.addEncomenda(enc);
                System.out.println("Encomenda registada com sucesso. Codigo da sua encomenda: "+enc.getCodEncomenda());
                res = enc.getCodEncomenda();
            }
            else {
                res = "";
            }
        return res;
    }



    /*inserir pedidos de encomendas a uma loja, por parte de um utilizador;
      Assumir que Encomendas em que o codigo de util é igual ao codigo de loja sao parte do catalogo da loja.
       Objectivo é que o utilizador entra na aplicaçao, escolhe uma loja e ai vai ter uma lista de possiveis encomendas que pode fazer
       */
    public void inserirEncomenda(Loja loja, String user, List<Encomenda> encomendas) {

        double peso = 0;
        List<LinhaEncomenda> linhas = new ArrayList<>();
        for(Encomenda a: encomendas){
            peso += a.getPeso();
            for(LinhaEncomenda l : a.getLinhas())
                linhas.add(l);
        }
        Encomenda encomenda = new Encomenda(user, loja.getCodigo(),this.allEncomendas.novoCodigoEncomenda(), peso);
        encomenda.setLinhas(linhas);
        encomenda.setCodUtilizador(user);
        encomenda.setCodLoja(loja.getCodigo());
        String[] ts = {"t26","t24","t51","t31","t9"};
        int j = (int) getRandomNumber(0,4);
        encomenda.setcodTransportadora(ts[j]);
        encomenda.setAceite(false);

        String res = menuCalcularGastos(user,encomenda);
        if(res.equals("")) {
            System.out.println("Procedimento cancelado\n\n");
        }
        else {
            Loja l = (Loja) this.allUsers.getUser(loja.getCodigo());
            l.addEncomenda(encomenda.getCodEncomenda());
        }


    }

    public void classificarEncomenda(String codUser, TrazAqui model){
        List<Encomenda> encomendas = model.getAllEncomendas().getEncomendasRecebidas(codUser);
        int i = 0;
        System.out.println("Selecione uma das encomendas para classificar:");

        if(encomendas.size() == 0){
            System.out.println("Nao fez encomendas para poder classificar.");
        }
        else {
            for (Encomenda a : encomendas) {
                System.out.println("Encomenda " + i + ": \n\n" + a.toString() + "\n\n");
                i++;
            }
            int selecionado = Input.lerInt();
            System.out.println("Indique a classificacao desejada:");
            int classificacao = Input.lerInt();
            classificarEntrega(classificacao, encomendas.get(selecionado));
            System.out.println("Encomenda classificada com sucesso!");
        }
    }



    //classificar, por parte do utilizador, o serviço de entrega;
    public void classificarEntrega(int classificacao, Encomenda entrega){
        entrega.setClassificacao(classificacao);
    }




    //----------------------------------------Menu Voluntario-----------------------------------------------------------






     public void sinalizarVoluntarios(Voluntario v) { //sinaliza que o voluntario esta disponivel
        v.setLivre(true);
    }

    //----------Sub-menu->Opcao 2 do menuVoluntario------------
    public void verEncsLojas(Voluntario v,TrazAqui model) throws NoUserAvailableException{
        List<Loja> lojaList = lojasDisponiveisUser(model, v.getCodigo());
        String inputLj = "";

        do { //escolher a loja
            System.out.println("Selecione uma das Lojas seguintes (escreva o codigo correspondente):");
            for (Loja l : lojaList) System.out.println(l.getCodigo() + " -> " + l.getNome());
            inputLj = Input.lerString();
            if (!lojaExiste(inputLj, lojaList))
                System.out.println("O codigo que inseriu nao corresponde a nenhuma loja. Tente novamente.");
        }
        while (!lojaExiste(inputLj, lojaList));

        Loja l = (Loja) this.getAllUsers().getUser(inputLj);

        if(l.getEncProntas().size() == 0){
            System.out.println("Esta loja nao tem encomendas por entregar");
        
        } else {
            System.out.println("Encomendas disponiveis:");
            for (String enc : l.getEncProntas()) System.out.println(enc.toString());
        }
        

    }



    //------Sub-menu->Opcao 3 do menuVoluntario------------
    public boolean lojaExiste(String codLoja, List<Loja> lojaList) throws NoUserAvailableException{
        Loja lj = (Loja) this.allUsers.getUser(codLoja);
        return (lojaList.contains(lj));
    }
    public boolean encExiste(String codEnc, List<String> encProntas){
        return (encProntas.contains(codEnc));
    }

    public String fazerEncsLojas(Voluntario v,TrazAqui model) throws NoUserAvailableException{
        List<Loja> lojaList = lojasDisponiveisUser(model, v.getCodigo());
        String inputLj = "";
        String inputEnc = "";

        if(lojaList.size()==0){
            System.out.println("Nao existe nenhuma loja nas proximidades");
            return "";
        } else {
            do { //escolher a loja
                System.out.println("Selecione uma das Lojas seguintes (escreva o codigo correspondente):");
                for (Loja l : lojaList) System.out.println(l.getCodigo() + " -> " + l.getNome());
                inputLj = Input.lerString();
                if (!lojaExiste(inputLj, lojaList))
                    System.out.println("O codigo que inseriu nao corresponde a nenhuma loja. Tente novamente.");
            }
            while (!lojaExiste(inputLj, lojaList));

            Loja l = (Loja) this.getAllUsers().getUser(inputLj);

            if(l.getEncProntas().size() == 0){
                System.out.println("A loja que inseriu nao tem encomendas prontas para entrega");
                return "";
            } else {
                do { //escolher a encomenda
                    System.out.println("Selecione uma das encomendas disponiveis (escreva o codigo correspondente):");
                    for (String enc : l.getEncProntas()) System.out.println(enc);
                    inputEnc = Input.lerString();
                    if (!encExiste(inputEnc, l.getEncProntas()))
                        System.out.println("O codigo que inseriu nao corresponde a nenhuma encomenda. Tente novamente.");
                }
                while (!encExiste(inputEnc, l.getEncProntas()));
    
                if(this.allEncomendas.getEncomenda(inputEnc).getEncomendaMedica()
                        && !v.aceitoTransporteMedicamentos()){
                    System.out.println("Nao pode transportar este tipo de encomenda. Escolha outra encomenda que nao seja deste tipo.");
                    inputEnc = "";
                }
            }
            
        }
           return inputEnc;
     }

        public void fazerTransporte (Voluntario v,String enc){
            Encomenda e = this.allEncomendas.getEncomenda(enc);
            double time = 0;

            Localizacao l1 = v.getLocalizacao();
            Localizacao l2 = (this.allUsers.getUser(e.getCodUtilizador())).getLocalizacao();

            time = l1.tempoViagem(l2);

            EncomendaAceite encA = new EncomendaAceite(enc);
            v.getRegistoEncomendas().putIfAbsent(encA,(int) time);
            aceitaEncomenda(enc);
            
        }









    //-----------------------------------------Menu Transportadoras-----------------------------------------------------





    //---------------Preco total de uma encomenda---------------------
    public long precoEncomenda(String codEnc,UtilizadorGeral e) {

        if (!allEncomendas.getEncomendas().containsKey(codEnc)) return -1;

        long res = -1;
        Encomenda enc = this.allEncomendas.getEncomendas().get(codEnc);
        Utilizador u = (Utilizador) this.allUsers.getUser(enc.getCodUtilizador());
        Localizacao l2 = u.getLocalizacao();
        Localizacao l1 = e.getLocalizacao();

        double dist = l1.distancia(l2);

        if(e instanceof Loja){
            Loja t = (Loja) e;
            res = (long) enc.calculaValorToTal();
        }

        if(e instanceof Transportadora){
            Transportadora t = (Transportadora) e;
            res = (long) (enc.calculaValorToTal()+t.getprecoPorKm()*dist);
        }
        return res;
    }

    //------------------Total faturado por uma empresa-------------------------------------

    /* indicar o total facturado por uma empresa transportadora num determinado período; */
    /* Por enquanto devolve o valor total faturado desde o inicio da Empresa*/
    public double valorTotalFaturado (Transportadora t){return t.getTotalFaturado(); }



    //-----------Fazer transporte de uma encomenda-------------------

    /*inserir pedidos de encomendas a uma loja, por parte de um utilizador;
    Assumir que Encomendas em que o codigo de util é igual ao codigo de loja sao parte do catalogo da loja.
     Objectivo é que o utilizador entra na aplicaçao, escolhe uma loja e ai vai ter uma lista de possiveis encomendas que pode fazer
     */
    public void fazerEntregaT(Transportadora t) throws NoUserAvailableException {

        List<Encomenda> lista = this.allEncomendas.getEncomendasTr(t.getCodigo(), false);
        List<String> lE = new ArrayList<>();
        for(Encomenda e : lista) lE.add(e.getCodEncomenda());

        System.out.println("Indique o codigo da encomenda: ");
        String codEnc = Input.lerString();
        if (!lE.contains(codEnc)){
            System.out.println("A encomenda que inseriu nao existe nesta transportadora.");
            return;
        }
        Encomenda encomenda = this.allEncomendas.getEncomenda(codEnc);
        String codU = encomenda.getCodUtilizador();
        Utilizador u = (Utilizador) this.allUsers.getUser(codU);

        double custo = precoEncomenda(codEnc,t);

        Localizacao l1 = t.getLocalizacao();
        Localizacao l2 = u.getLocalizacao();

        double time = l1.tempoViagem(l2);

        EncomendaAceite enc = new EncomendaAceite(codEnc);

        Gastos gt = new Gastos(time,custo);

        t.getRegistoCusto().putIfAbsent(enc,gt);
        aceitaEncomenda(encomenda.getCodEncomenda());
        System.out.println("Encomenda transportada com sucesso.");
    }






    //-----------------------------------------------Menu Loja----------------------------------------------------------





    //Mostra as encomendas disponiveis para entrega
    public void encDisponivel(Loja loja,String codEnc){
        loja.getEncProntas().add(codEnc);
        System.out.println("A encomenda "+codEnc+" esta pronta para entrega.");
    }

    //inserir informação de encomenda pronta a ser entregue, por parte das lojas;
    public void lojaAdicionaEncomenda(Encomenda enc, Loja loja, double peso, boolean encomendaMedica,
                                      ArrayList<LinhaEncomenda> linhas){
        Encomenda encomenda = enc.clone();
        encomenda.setLinhas(linhas);
        encomenda.setEncomendaMedica(encomendaMedica);
        this.allEncomendas.addEncomenda(encomenda);
    }







    //------------------------------- Funcoes para teste/auxiliares e Querys -------------------------------------------
    public void aceitaEncomenda(String codigo){ 
        //
        Encomenda encomenda = allEncomendas.getEncomenda(codigo);
        UtilizadorGeral user = getTransporteEncomenda(encomenda);
        Loja l = (Loja) this.allUsers.getUser(encomenda.getCodLoja());
        l.removeEncomenda(codigo);
        if (user != null) this.allEncomendas.aceitaEncomendas(codigo, user);
        else System.out.println("Encomenda: "+codigo+" nao foi aceite por falta de opçoes de transporte.");
        }

    public UtilizadorGeral getTransporteEncomenda(Encomenda encomenda){
        List<UtilizadorGeral> voluntarios = allUsers.getTypeUsers('v');
        List<UtilizadorGeral> transportadores = allUsers.getTypeUsers('t');
        String codE = encomenda.getCodEncomenda();
        for (UtilizadorGeral v: voluntarios){
            if ((isInRange(v.getCodigo(),encomenda.getCodUtilizador(),this)) && (isInRange(v.getCodigo(),encomenda.getCodLoja(),this))) 
                return v;
        }
        for (UtilizadorGeral t: transportadores){
            if ((isInRange(t.getCodigo(),encomenda.getCodUtilizador(),this)) && (isInRange(t.getCodigo(),encomenda.getCodLoja(),this))) 
                return t;
        }
        return null;
    }

    public void consultarEncomenda(String user, TrazAqui model){
        List<Encomenda> encomendas = new ArrayList<>();
        for( Encomenda a : model.getAllEncomendas().getEncomendas().values()){
            if(a.getCodUtilizador().equals(user)) encomendas.add(a.clone());
        }
        int i = 0;
        for (Encomenda enc : encomendas){
            System.out.println("Encomenda  "+i+": "+enc.getCodEncomenda()+" Peso:"+enc.getPeso());
            i++;
        }
        System.out.println("Selecione uma das encomendas. Selecione -1 para voltar ao menu anterior");
        int numEnc = Input.lerInt();
        if (numEnc == -1) return;
        if (numEnc >= i) {
            System.out.println("Valor inserido fora de limite");
            consultarEncomenda( user,  model);
            return;
        }

        Encomenda encomenda = encomendas.get(numEnc);
        if (encomenda.isEntregue()) System.out.println("A sua encomenda ja foi entregue");
        if (encomenda.isAceite()) System.out.println("A sua encomenda esta a caminho");
            else System.out.println("A sua encomenda ainda se encontra na loja");

    }

    
    //-------Lista de lojas disponiveis para um dado utilizador---------------------
    public List<Loja> lojasDisponiveisUser(TrazAqui model,String user){ //este model tem de sair daqui -> resolver em chamada
        List<Loja> ret = new ArrayList<>();
        for (UtilizadorGeral a: model.getAllUsers().getUsers('l').values()){
            if (isInRange(a.getCodigo(),user, model)) ret.add( (Loja)a.clone());
        }
        return ret;
    }


    //-------------------Testa se as distancias sao compativeis-----------------------------------

    //testar se um utilizador esta em range para fazer um pedido a um serviço. works for everything i think
    public boolean isInRange(String serviceS, String userS, TrazAqui model) {
        double x,y,x1,y1;
        UtilizadorGeral service = model.getAllUsers().getUsers().get(serviceS);
        UtilizadorGeral user = model.getAllUsers().getUsers().get(userS);
        x1 = user.getLocalizacao().getLatitude();
        y1 = user.getLocalizacao().getLongitude();
        if(service instanceof Loja){
            Loja loja = (Loja) service;
            double raio =  loja.getRaio();
            x = loja.getLocalizacao().getLatitude();
            y = loja.getLocalizacao().getLongitude();
            Double distance = Math.sqrt(((x1*x1)-(x*x))+((y1*y1)-(y*y)));
            if (distance <= raio) return true;
        }
        if(service instanceof Transportadora){
            Transportadora transport = (Transportadora) service;
            double raio =  transport.getRaio();
            x = transport.getLocalizacao().getLatitude();
            y = transport.getLocalizacao().getLongitude();
            Double distance = Math.sqrt(((x1*x1)-(x*x))+((y1*y1)-(y*y)));
            if (distance <= raio) return true;
        }
        if(service instanceof Voluntario){
            Voluntario voluntario = (Voluntario) service;
            double raio =  voluntario.getRaioGeografico();
            x = voluntario.getLocalizacao().getLatitude();
            y = voluntario.getLocalizacao().getLongitude();
            Double distance = Math.sqrt(((x1*x1)-(x*x))+((y1*y1)-(y*y)));
            if (distance <= raio) return true;
        }
        return false;
    }


    //-----------------Gravar e salvar o estado da aplicação em ficheiro----------------------------

    /*Grava o estado da aplicação em ficheiro, para que seja possível retomar mais tarde a execução */
    public void save(String fileName) throws IOException{
        ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream(fileName));
        os.writeObject(this);
        os.close();
    }

    public void save(){
        try{
            System.out.println("A gravar...");
            save("input_files/pseudologs.dat");

            System.out.println("Ficheiro pseudolog.dat gravado");
        } catch(IOException e){
            System.out.println("\nO ficheiro inserido não é válido");
        }
    }


    public TrazAqui load(String fileName) throws IOException, ClassNotFoundException{
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
        TrazAqui model = (TrazAqui) is.readObject();
        is.close();
        return model;
    }

    public void load(){
        Scanner input = new Scanner(System.in);
        try{
            System.out.println("A carregar...");
            load("input_files/pseudologs.dat");  

            System.out.println("Ficheiro pseudologs.dat carregado");
        } catch(ClassNotFoundException | IOException e){
            System.out.println("\nO ficheiro inserido não é válido");
            Input.lerString();
        }
        input.close();
    }


    //------------Listagens das 10 transportadoras--------------------------------------------------

    public List<Transportadora> filterT (){
        List<Transportadora> res = new ArrayList<>();
        for(UtilizadorGeral u : this.allUsers.getUsers().values()){
            if(u instanceof Transportadora) res.add((Transportadora) u);
        }
        return res;
    }

    public List<String> ordenarTransportadoras(List<Transportadora> list){
        List<Transportadora> lista = new ArrayList<>(list);

        Comparator<Transportadora> comparator = new Comparator<Transportadora>() {
            @Override
            public int compare(Transportadora o1, Transportadora o2) {
                if(o1.getKmsPercorridos() < o2.getKmsPercorridos()) return 1;
                return -1;
            }
        };
        Collections.sort(lista,comparator);
        List<String> nomes = new ArrayList<>();
        int i = 1;
        for (Transportadora t : lista) {
            nomes.add(t.getNome());
            if (nomes.size() == 10) break;
        }
        return nomes;
    }


    //------------Listagens dos 10 utilizadores que mais utilizam a aplicaçao--------------------------


    public List<String> ordenarUtilizador(List<UtilizadorGeral> list){
        List<UtilizadorGeral> lista = new ArrayList<>(list);

        Comparator<UtilizadorGeral> comparator = new Comparator<UtilizadorGeral>() {
            @Override
            public int compare(UtilizadorGeral o1, UtilizadorGeral o2) {
                if(o1.numeroEncomendas() < o2.numeroEncomendas()) return 1;
                return -1;
            }
        };
        Collections.sort(lista,comparator);
        List<String> nomes = new ArrayList<>();
        for (UtilizadorGeral u : lista) {
                nomes.add(u.getNome());
                if (nomes.size() == 10) break;
        }
        return nomes;
    }
}

