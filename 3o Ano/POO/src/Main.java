import Exceptions.NoUserAvailableException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

public class Main {
    

    public static double getRandomNumber(int min, int max){
        return (Math.random()*((max-min)+1))+min;
    }
    public static boolean getRandomBoolean(){
        Random rnd = new Random();
        return rnd.nextBoolean();
    }


    public static void main(String[] args) throws IOException {
        TrazAqui model = new TrazAqui();
        
       try{
           model = model.load("input_files/pseudologs.dat");
           System.out.println("Loaded by binary file");
        }
       catch(FileNotFoundException | ClassNotFoundException e){
            Parse p = new Parse();
            p.parse(model);
            
            System.out.println("Loaded by log file");
        }

       start(model);
    }


    public static void save(TrazAqui modelo){
            try{
                modelo.save("input_files/pseudologs.dat");
            } catch(IOException e){
                System.out.println("Erro a salvar ficheiro");
                System.exit(1);
            }
        System. exit(0);
    }

    public static void mainMenu(String codUser, TrazAqui model) throws NoUserAvailableException {
        System.out.println("Log in feito com sucesso.");
        

        switch (codUser.charAt(0)){
            case 'u':
                menuUtilizador(codUser, model);
                break;
            case 'v':
                menuVoluntario(codUser,model);
                break;
            case 'l':
                menuLoja(codUser,model);
                break;
            case 't':
                menuTransportadora(codUser,model);
                break;
            default:
                System.out.println("Codigo de utilizador invalido.");
        }
    }

    public static void menuUtilizador(String codUser, TrazAqui model){
        int choice;
        
        do{
            System.out.println("Bem-vindo ao TrazAqui!\nSelecione uma opcao: ");
            System.out.println("1: Fazer uma encomenda.\n\n2:Ver as 10 transportadoras mais utilizadas.\n\n3:Ver os 10 utilizadores que" +
                    " utilizam mais a aplicacao.\n\n4: Classificar encomendas.\n\n5: Consultar encomendas.\n\n6: Sair da aplicacao.");
            choice = Input.lerInt();

        }
        while(choice < 1 || choice > 6);

        if (choice == 1) {
           List<Loja> lojas = model.lojasDisponiveisUser(model, codUser);
           if(lojas.size() == 0){
               System.out.println("Nao existem lojas na proximidade.");
               menuUtilizador(codUser,model);
           }
            menuUserEscolheLojas(codUser, model, lojas);
            menuUtilizador(codUser,model);
        }

        if (choice == 2) {
            int i = 1;
            List<String> lista = model.ordenarTransportadoras(model.filterT());
            for(String s : lista){
                System.out.println(i + " - " + s);
                i++;
            }
            menuUtilizador(codUser,model);
        }

        if(choice == 3) {
            int i = 1;
            List<UtilizadorGeral> list = model.getAllUsers().getUgerais();
            List<String> lista = model.ordenarUtilizador(list);
            for(String s : lista){
                System.out.println(i + " - " + s);
                i++;
            }
            menuUtilizador(codUser,model);
        }

        if(choice == 4) {
            model.classificarEncomenda(codUser,model);
            menuUtilizador(codUser,model);
        }

        if(choice == 5) {
            model.consultarEncomenda(codUser,model);
            menuUtilizador(codUser,model);
        }

        if (choice == 6) {
            save(model);
        } 

    }

    //imprime todas a lojas disponiveis para o utilizador
    public static void menuUserEscolheLojas(String codUser, TrazAqui model, List<Loja> lojas){
        int i = 0;
        System.out.println("Selecione uma das Lojas\n");
        for(Loja a: lojas){
            System.out.println("Loja "+i+": "+a.getNome());
            i++;
        }
        i = Input.lerInt();
        try {menuFazerEncomenda(codUser, model, lojas.get(i));}
        catch (IndexOutOfBoundsException | NoUserAvailableException e){
            System.out.println("Numero selecionado fora de limite");
            menuUserEscolheLojas( codUser,  model,  lojas);
        }
    }

    //Mostra todos os pacotes avaliaveis da loja para o user selecionar um ou mais para a encomenda.
    public static void menuFazerEncomenda(String codUser, TrazAqui model, Loja loja) throws NoUserAvailableException {
        String codLoja = loja.getCodigo();
        List<Encomenda> produtos = verProdutosLoja(model, codLoja);
        List<Encomenda> pacotesEscolhidos = new ArrayList<>();

        int i = 0, j = 0;
        StringBuilder s = new StringBuilder();
        for (Encomenda a : produtos){
            s.append("\n\nPacote "+i+": " );
            i++;
            for (LinhaEncomenda linha : a.getLinhas()) {
                s.append("\n"+linha.toString());
            }
        }
        System.out.println(s);
        System.out.println("Escolha um ou mais pacotes para encomendar. Se nao quiser mais insira -1");
        j = Input.lerInt();
        while (j>=0){
            try{ pacotesEscolhidos.add(produtos.get(j).clone());}
            catch (IndexOutOfBoundsException e){
                System.out.println("Numero selecionado fora de limite");
                menuFazerEncomenda( codUser,  model,  loja);
            }
            j = Input.lerInt();
        }
        if (pacotesEscolhidos.size() != 0) model.inserirEncomenda(loja,codUser, pacotesEscolhidos);

        menuUtilizador(codUser, model);
    }

    public static void menuVoluntario(String codUser,TrazAqui model) throws NoUserAvailableException {
        int choice;
        Voluntario voluntario = (Voluntario) model.getAllUsers().getUser(codUser);
        
        do{
            System.out.println("Bem-vindo ao TrazAqui!\nSelecione uma opcao: ");
            System.out.println("1: Sinalizar que esta disponivel.\n\n2: Ver encomendas prontas a ser entregues.\n\n3: Fazer transporte de encomenda\n\n4: Sair da aplicacao");
            choice = Input.lerInt();
        }
        while(choice < 1 || choice > 4);
        if (choice == 1) {
            model.sinalizarVoluntarios(voluntario);
            System.out.println("Estou disponivel.\n\n");
            menuVoluntario(codUser,model);
        }
        if (choice == 2){
            model.verEncsLojas(voluntario,model);
            menuVoluntario(codUser,model);
            

        }
        if (choice == 3){
            
            String encomenda = model.fazerEncsLojas(voluntario,model);
            if(encomenda.equals("")) {
                menuVoluntario(codUser,model);
            }
            else {
                model.fazerTransporte(voluntario,encomenda);
                System.out.println("Encomenda aceite com sucesso.\n");
                menuVoluntario(codUser,model);
            }

        }
        if (choice == 4){
            save(model);
        }
    }

    public static void menuLoja(String codUser, TrazAqui model) throws NoUserAvailableException {
        int choice;
        Loja l = (Loja) model.getAllUsers().getUser(codUser);
        List<Encomenda> lista = model.getAllEncomendas().getEncomendasLj(codUser,false);
        List<String> lj = new ArrayList<>();

        for(Encomenda e : lista) lj.add(e.getCodEncomenda());

        l.setEncsProntas(lj);

        do {
            System.out.println("Bem-vindo ao TrazAqui!\nSelecione uma opcao: ");
            System.out.println("1: Sinalizar que uma encomenda esta disponivel.\n\n2: Ver encomendas prontas a ser entregues.\n\n3: Sair da aplicacao.");
            choice = Input.lerInt();
        }
        while (choice < 1 || choice > 3);

        if (choice == 1) {
            if(l.getEncProntas().size() == 0){
                System.out.println("A loja nao tem encomendas\n");
                menuLoja(codUser, model);
            }
            else{
                System.out.println("Encomendas da loja: ");
                for(String e : l.getEncProntas()){
                    System.out.println(e);
                }
                System.out.println("Insira o codigo da encomenda pretendida:");
                String cod = Input.lerString();
                if(!l.getEncProntas().contains(cod)){
                    System.out.println("A encomenda que inseriu nao existe. Tente novamente.");
                    menuLoja(codUser,model);
                } else {
                    model.encDisponivel(l,cod);
                    menuLoja(codUser,model);
                }
               
            }
            
        }
        if (choice == 2) {
            if(l.getEncProntas().size() == 0){
                System.out.println("A loja nao tem encomendas\n");
                menuLoja(codUser, model);
            }
            else{
                System.out.println("Encomendas prontas a ser entregues:");
                for(String e : l.getEncProntas()){
                    System.out.println(e);
                }
                menuLoja(codUser,model);
            }
        }

        if (choice == 3) {
            save(model);
        }
    }

    public static void menuTransportadora(String codUser, TrazAqui model) throws NoUserAvailableException {
        int choice;
        Transportadora t = (Transportadora) model.getAllUsers().getUser(codUser);

        do {
            System.out.println("Bem-vindo ao TrazAqui!\nSelecione uma opcao: ");
            System.out.println("1: Sinalizar que esta disponivel a fazer encomendas.\n\n2: Determinar preco de encomenda.\n\n3: Fazer transporte de encomenda\n\n" +
                    "4: Verificar total faturado. \n\n5: Sair da aplicacao.");
            choice = Input.lerInt();
        }
        while (choice < 1 || choice > 5);

        if (choice == 1) {
            t.setRecolheEncomendas(true);
            System.out.println("Estou disponivel.");
            menuTransportadora(codUser,model);
        }

        if (choice == 2){
            List<Encomenda> lista = model.getAllEncomendas().getEncomendasTr(codUser, false);


            if(lista.size() == 0) {
                System.out.println("Nao existem encomendas por entregar.");
                menuTransportadora(codUser, model);
            } else {
                System.out.println("Encomendas disponiveis:\n");
                for(Encomenda s : lista) System.out.println(s.getCodEncomenda());
                System.out.println("Indique o codigo da encomenda: ");
                String codEnc = Input.lerString();
                if(model.precoEncomenda(codEnc,t) == -1 || !lista.contains(codEnc)){
                    System.out.println("A encomenda que inseriu nao existe.");
                    menuTransportadora(codUser,model);
                }
                else{
                    System.out.println("Preco: "+ model.precoEncomenda(codEnc,t));
                    menuTransportadora(codUser,model);
                }
            }
            
        }

        if (choice == 3){
            List<Encomenda> lista = model.getAllEncomendas().getEncomendasTr(codUser, false);

            if(lista.size() == 0) {
                System.out.println("Nao existem encomendas para entregar.");
                menuTransportadora(codUser, model);
            } else {
                model.fazerEntregaT(t);
                menuTransportadora(codUser,model);
            }
        }

        if (choice == 4) {
            System.out.println("Total faturado pela impresa: ");
            double total = ((Transportadora) model.getAllUsers().getUser(codUser)).getTotalFaturado();
            System.out.println((int) total);
            menuTransportadora(codUser, model);
        }

        if (choice == 5){
            save(model);
        }
    }

    public static List<Encomenda> verProdutosLoja(TrazAqui model, String codLoja){
        List<Encomenda> encomendas = new ArrayList<>();
        for (Map.Entry<String, Encomenda> a: model.getAllEncomendas().getEncomendas().entrySet()){
            String codigoUser = a.getValue().getCodUtilizador();
            if (codigoUser.equals(a.getValue().getCodLoja()) && codLoja.equals(codigoUser)) encomendas.add(a.getValue().clone());
        }
        return encomendas;
    }


    public static void login(TrazAqui model) {
        String codUser = "";
        Login input = new Login();
        System.out.println("Insira email:\n");
        String mail = Input.lerString();
        UtilizadorGeral user = model.getAllUsers().getUsers().get(mail.split("@")[0]);
        System.out.println("Insira password:\n");
        String password = Input.lerString();
        try {
             codUser = user.getCodigo();
             input = new Login(user.getLogin().getEmail(), password);
        } catch(NullPointerException e){
            System.out.println("A informacao inserida esta errada");
            login(model);
        }
        if (input.equals(user.getLogin())) {
           try{ mainMenu(codUser,model);}
           catch(NoUserAvailableException e){
               System.out.println("Utilizador nao existe");
           }
        }
        else{
            System.out.println("A informacao inserida esta errada");
            login(model);
        }
    }

    public static void start(TrazAqui model) {
        int s = 0;
        System.out.println("Bem vindo ao TrazAqui!\n");
        System.out.println("1: Fazer log in.\n");
        System.out.println("2: Criar uma conta.\n");
        System.out.println("3: Sair da aplicacao.\n");
        s = Input.lerInt();
        if (s == 3) save(model);
        if (s == 1) login(model);
        else if (s == 2) signUp(model); 
        else{
            System.out.println("A informacao inserida esta errada");
            start(model);
        }
    }

    public static void signUp(TrazAqui model)  {
        System.out.println("Criar uma conta como: ");
        System.out.println("1: Transportadora(Empresa).");
        System.out.println("2: Loja.");
        System.out.println("3: Voluntario.");
        System.out.println("4: Utilizadores.");
        System.out.println("5: Voltar para o inicio.");
        int choice = Input.lerInt();
        if(choice >= 1 && choice <=5) createAccount(choice, model);
        else{
            System.out.println("A informacao inserida esta errada");
            signUp(model);
        }
    }

    public static void createAccount(int choice, TrazAqui model){
        int count, num;
        if (choice > 5 || choice < 1){
            System.out.println("Selecione uma das opcoes anteriormente referidas.");
            signUp(model);
        }
        switch (choice) {
            case 1:
                System.out.println("Insira o nome da Empresa");
                String nome = Input.lerString();
                System.out.println("Insira o NIF da Empresa");
                int NIF = Input.lerInt();
                for( count = 0, num = NIF; num != 0; num/=10, ++count);
                if (count != 9)  {
                    System.out.println("Numero incorreto de caracteres");
                    createAccount(1, model);
                    return;
                }
                System.out.println("Insira a Localizacao da Empresa (latitude e logitude)");
                System.out.print("Latitude: ");
                double lat = Input.lerDouble();
                System.out.print("Longitude: ");
                double lo = Input.lerDouble();
                if (lat < -180 || lat > 180  || lo < -180 || lo > 180 ){
                    System.out.println("Cordenadas impossiveis");
                    createAccount(1, model);
                    return;
                }
                System.out.println("Insira o preco por quilometro pretendido: ");
                double pKM = Input.lerDouble();
                String cod = model.getAllUsers().novoCodigoUser('t'); 
                System.out.println("Insira uma password");
                String password = Input.lerString();

                Transportadora newT = new Transportadora(cod, nome, new Login(cod+"@trazaqui.com",password),
                        new Localizacao(lat,lo), NIF, getRandomNumber(50,1000), false,0,
                        new HashMap<>(),0, pKM);

                        
                model.addUser(newT);

                System.out.println("Conta criada com sucesso. Email: "+ cod +"@trazaqui.com");
                start(model);
                break;


            case 2:
                System.out.println("Insira o nome da Loja");
                nome = Input.lerString();
                cod = model.getAllUsers().novoCodigoUser('l');
                System.out.println("Insira a Localizacao da Loja (latitude e logitude)");
                System.out.print("Latitude: ");
                lat = Input.lerDouble();
                System.out.print("Longitude: ");
                lo = Input.lerDouble();
                if (lat < -180 || lat > 180  || lo < -180 || lo > 180 ){
                    System.out.println("Cordenadas impossiveis");
                    createAccount(2, model);
                    return;
                }
                System.out.println("Insira uma password");
                password = Input.lerString();
                List<String> encs = new ArrayList<>();

                Loja newL = new Loja(cod, nome, new Login(cod+"@trazaqui.com",password) ,new Localizacao(lat,lo),
                 (int) getRandomNumber(10,90), 0, encs);
                model.addUser(newL);

                System.out.println("Conta criada com sucesso. Email: "+ cod +"@trazaqui.com");
                start(model);
                break;

            case 3:
                System.out.println("Insira o seu nome: ");
                nome = Input.lerString();
                cod = model.getAllUsers().novoCodigoUser('v');
                System.out.println("Insira a sua Localizacao (latitude e logitude)");
                System.out.print("Latitude: ");
                lat = Input.lerDouble();
                System.out.print("Longitude: ");
                lo = Input.lerDouble();
                if (lat < -180 || lat > 180  || lo < -180 || lo > 180 ){
                    System.out.println("Cordenadas impossiveis");
                    createAccount(3, model);
                    return;
                }
                System.out.println("Insira uma password");
                password = Input.lerString();

                Voluntario newV = new Voluntario(cod, nome, new Localizacao(lat,lo),
                        new Login(cod+"@trazaqui.com",password), (int) getRandomNumber(3,9), false, getRandomBoolean(),
                         new HashMap<>());
                model.addUser(newV);

                System.out.println("Conta criada com sucesso. Email: "+ cod +"@trazaqui.com");
                start(model);
                break;

            case 4:
                System.out.println("Insira o seu nome");
                nome = Input.lerString();
                cod = model.getAllUsers().novoCodigoUser('u');
                System.out.println("Insira a sua Localizacao (latitude e logitude)");
                System.out.print("Latitude: ");
                lat = Input.lerDouble();
                System.out.print("Longitude: ");
                lo = Input.lerDouble();
                if (lat < -180 || lat > 180  || lo < -180 || lo > 180 ){
                    System.out.println("Cordenadas impossiveis");
                    createAccount(4, model);
                    return;
                }
                System.out.println("Insira uma password");
                password = Input.lerString();

                Utilizador newU = new Utilizador(cod,nome,new Login(cod+"@trazaqui.com",password),
                        new Localizacao(lat,lo));
                model.addUser(newU);

                System.out.println("Conta criada com sucesso. Email: "+ cod +"@trazaqui.com");
                start(model);
                break;

            case 5:
                start(model);

            default:
                start(model);

        }
    }

}

