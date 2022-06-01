
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;


public class Parse {

    public static double getRandomNumber(int min, int max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }
    public static boolean getRandomBoolean(){
        Random rnd = new Random();
        boolean res = rnd.nextBoolean();
        return res;
    }


    public void parse(TrazAqui model) throws FileNotFoundException {
        int numInvalidas = 0;
        int numUtilizadores = 0;
        int numVoluntarios = 0;
        int numTransportadoras = 0;
        int numLojas = 0;
        int numEncomendas = 0;
        File f = new File("input_files/logs.txt");
        BufferedReader br = null;

        try { br = new BufferedReader(new FileReader(f));}
        catch (FileNotFoundException e) {
            f = new File("src/input_files/logs.txt");
        }

        try {br = new BufferedReader(new FileReader(f));}
        catch (FileNotFoundException e) {
            System.out.println("Can't find log file.");
            System. exit(1);
        }

        String st = null;
        while (true) {
            try {
                if (!((st = br.readLine()) != null)) break;
            } catch (IOException | NullPointerException e) {
                System.out.println("Log file is null.");
            }
            String[] linhaPartida = st.split(":", 2);
            switch(linhaPartida[0]){
                case "Utilizador":
                    numUtilizadores++;
                    model.addUser(parseUtilizador(linhaPartida[1]));
                    break;
                case "Loja":
                    numLojas++;
                    model.addUser(parseLoja(linhaPartida[1]));
                    break;
                case "Voluntario":
                    numVoluntarios++;
                    model.addUser(parseVoluntario(linhaPartida[1]));
                    break;
                case "Transportadora":
                    numTransportadoras++;
                    model.addUser(parseTransportadora(linhaPartida[1]));
                    break;
                case "Encomenda":
                    numEncomendas++;
                    model.addEncomenda(parseEncomenda(linhaPartida[1]));
                    break;
                case "Aceite":
                    model.aceitaEncomenda(linhaPartida[1]);
                    break;
                default:
                    System.out.println("Linha inválida.");
                    numInvalidas++;
                    break;
            }


        }
    
        atualizaProdutos(model);
        System.out.println("\nLINHAS INVALIDAS: " + numInvalidas);
        System.out.println("\nNUM UTILIZADORES: " + numUtilizadores);
        System.out.println("\nNUM VOLUNTARIOS: " + numVoluntarios);
        System.out.println("\nNUM TRANSPORTADORAS: " + numTransportadoras);
        System.out.println("\nNUM LOJAS: " + numLojas);
        System.out.println("\nNUM ENCOMENDAS: " + numEncomendas);
        System.out.println("done!\n\n");

    }



    public Utilizador parseUtilizador(String input){
        String[] campos = input.split(",");
        String codUtilizador = campos[0];
        String nome = campos[1];
        double gpsx = Double.parseDouble(campos[2]);
        double gpsy = Double.parseDouble(campos[3]);
        Localizacao loc = new Localizacao(gpsx, gpsy);
        String email = codUtilizador + "@trazaqui.com";
        String pass = "1234";

        return new Utilizador(codUtilizador, nome, new Login(email, pass), loc);  
    }

    public Loja parseLoja(String input){
        String[] campos = input.split(",");
        String codLoja = campos[0];
        String nomeLoja = campos[1];
        double gpsx = Double.parseDouble(campos[2]);
        double gpsy = Double.parseDouble(campos[3]);
        Localizacao loc = new Localizacao(gpsx, gpsy);
        int raio = (int) getRandomNumber(20,400);
        int nEncomendas = (int) getRandomNumber(0,50);
        String email = codLoja+"@trazaqui.com";
        String pass = "1234";
        List<String> encProntas = new ArrayList<>();

        return new Loja(codLoja, nomeLoja,new Login(email,pass),loc,raio,nEncomendas,encProntas);
       
    }

    public Voluntario parseVoluntario(String input){
        String[] campos = input.split(",");
        String codVoluntario = campos[0];
        String nomeVoluntario = campos[1];
        double gpsx = Double.parseDouble(campos[2]);
        double gpsy = Double.parseDouble(campos[3]);
        double raio = Double.parseDouble(campos[4]);
        Localizacao loc = new Localizacao(gpsx, gpsy);
        String email = codVoluntario+"@trazaqui.com";
        String pass = "1234";
        Map<EncomendaAceite,Integer> registo = new HashMap<>();
        boolean livre = getRandomBoolean();
        boolean podeMed = getRandomBoolean();


        return new Voluntario(codVoluntario, nomeVoluntario, loc,new Login(email,pass),(int) raio,livre,
                podeMed,registo);
    }

    public Transportadora parseTransportadora(String input){
        String[] campos = input.split(",");
        String codEmpresa = campos[0];
        String nomeEmpresa = campos[1];
        double gpsx = Double.parseDouble(campos[2]);
        double gpsy = Double.parseDouble(campos[3]);
        int nif = Integer.parseInt(campos[4]);
        double raio = Double.parseDouble(campos[5]);
        double precoPorKm = Double.parseDouble(campos[6]);
        Localizacao loc = new Localizacao(gpsx, gpsy);
        String email = codEmpresa+"@trazaqui.com";
        String pass = "1234";
        double kms = getRandomNumber(20,5000);
        Map<EncomendaAceite,Gastos> registoCusto = new HashMap<>(); 
        double lucroTotal = getRandomNumber(500,10000000);

        return new Transportadora(codEmpresa, nomeEmpresa,new Login(email,pass), loc, nif, raio,false,
                kms, registoCusto, lucroTotal, precoPorKm);
    }


    public Encomenda parseEncomenda(String input){ 
        String[] campos = input.split(",");
        int i = 0;
        String codEncomenda = campos[0];
        String codUtilizador = campos[1];
        String codLoja = campos[2];
        double peso = Double.parseDouble(campos[3]);
        List<LinhaEncomenda> l = new ArrayList<>();
        boolean encomendaMedica = getRandomBoolean();
        boolean entregue = getRandomBoolean();
        int classificacao = (int) getRandomNumber(1,10);
        String nomeVendedor = "Alberto Magalhaes";
        String[] ts = {"t26","t24","t51","t31","t9"};
        int j = (int) getRandomNumber(0,4);
        String codTransportadora = ts[j];
        Encomenda ret = new Encomenda(codUtilizador, codLoja, codEncomenda,codTransportadora,l,nomeVendedor, peso,
                encomendaMedica, classificacao, false,entregue);
        i = 4;
        ArrayList<LinhaEncomenda> lines = new ArrayList<LinhaEncomenda>();
        while(i< campos.length) {
            LinhaEncomenda a = new LinhaEncomenda(campos[i],campos[i+1],Double.parseDouble(campos[i+2]),Double.parseDouble(campos[i+3]));
            lines.add(a);
            i+=4;
        }
        ret.setLinhas(lines);
        return ret;
    }

    public List<String> encontraEncsLoja(TrazAqui model,Loja l){
        List<String> encomendas = new ArrayList<>();
        for(Encomenda e : model.getAllEncomendas().getEncomendas().values()){
            if (e.getCodLoja().equals(l.getCodigo())) encomendas.add(e.getCodEncomenda());
        }
        return encomendas;
    }


    public void atualizaProdutos(TrazAqui model){
        String codLoja = "";
        for (UtilizadorGeral a: model.getAllUsers().getUsers('l').values()){
            Loja loja = (Loja) a;
            codLoja = loja.getCodigo();
            for (Encomenda e: model.getAllEncomendas().getEncomendas().values()){
                if (e.getCodLoja().equals(codLoja) && !(e.getCodLoja().equals(e.getCodUtilizador()))) {
                    Encomenda nova = e.clone();
                    nova.setCodEncomenda(model.getAllEncomendas().novoCodigoEncomenda());
                    nova.setCodUtilizador(codLoja);
                    model.addEncomenda(nova);
                }
            }
            loja.setEncsProntas(encontraEncsLoja(model, loja));
        }
    }
}
