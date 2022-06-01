import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Transportadora extends UtilizadorGeral{
    private static final long serialVersionUID = 7292067081077616115L;

    private int nif;
    private double raio;
    private boolean recolheEncomendas; //sinaliza se a empresa esta a recolher encomendas
    private double kmsPercorridos; 
    private Map<EncomendaAceite,Gastos> registoCusto; //associa a encomenda aos gastos (tempo de viagem e preco total)
    private double totalFaturado;
    private double precoPorKm;


    public Transportadora(){
        super();
        this.nif = 0;
        this.raio = 0;
        this.recolheEncomendas = false;
        this.kmsPercorridos = 0;
        this.registoCusto = new HashMap<>();
        this.totalFaturado = 0;
        this.precoPorKm = 0;
    }

    public Transportadora(Transportadora t){
        super(t);
        this.nif = t.getNif();
        this.raio = t.getRaio();
        this.recolheEncomendas = t.getRecolheEncomendas();
        this.kmsPercorridos = t.getKmsPercorridos();
        this.registoCusto = t.getRegistoCusto();
        this.totalFaturado = t.getTotalFaturado();
        this.precoPorKm = t.getprecoPorKm();

    }

    public Transportadora(String codigo, String nome, Login login, Localizacao place, int nif, double raio, boolean recolheEncomendas,
                          double kmsPercorridos, Map<EncomendaAceite,Gastos> registoCusto, double totalFaturado,
                          double precoPorKm){

        super(codigo, nome, login, place);
        this.nif = nif;
        this.raio = raio;
        this.recolheEncomendas = recolheEncomendas;
        this.kmsPercorridos = kmsPercorridos;
        this.registoCusto = new HashMap<>(registoCusto);
        this.totalFaturado = totalFaturado;
        this.precoPorKm = precoPorKm;
    }


    public Transportadora clone(){
        return new Transportadora(this);
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(super.toString());
        s.append("NIF: ");
        s.append(this.nif);
        s.append("\nRaio: ");
        s.append(this.raio);
        s.append("\nA empresa esta disponivel para transportar encomendas: ");
        s.append(this.recolheEncomendas);
        s.append("\nTotal de quilometros percorridos pela empresa: ");
        s.append(this.kmsPercorridos);
        s.append("\nTotal faturado: ");
        s.append(this.totalFaturado);
        s.append("\nPreco por km: ");
        s.append(this.precoPorKm);
        s.append("\n\n");
        return s.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || this.getClass() != o.getClass())
            return false;
        Transportadora l = (Transportadora) o;

        return  super.equals(l) &&
                this.recolheEncomendas == l.getRecolheEncomendas() &&
                this.kmsPercorridos == l.getKmsPercorridos() &&
                this.registoCusto.equals(l.getRegistoCusto()) &&
                this.totalFaturado == l.getTotalFaturado() &&
                this.precoPorKm == l.getprecoPorKm();
    }

    // GETS ---------------------------------------------------------------------

    public int getNif() {
        return this.nif;
    }

    public double getRaio() {
        return raio;
    }

    public boolean getRecolheEncomendas(){
        return this.recolheEncomendas;
    }

    public double getKmsPercorridos(){
        return this.kmsPercorridos;
    }

    public Map<EncomendaAceite,Gastos> getRegistoCusto(){
        Map<EncomendaAceite, Gastos> res = new HashMap<>();
        for(Map.Entry<EncomendaAceite, Gastos> e : this.registoCusto.entrySet())
            res.put(e.getKey().clone(), e.getValue().clone());
        return res;
    }

    public double getTotalFaturado(){
        return this.totalFaturado;
    }

    public double getprecoPorKm(){
        return this.precoPorKm;
    }


    // SETS ---------------------------------------------------------------------

    public void setNif(int nif){
        this.nif = nif;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public void setRecolheEncomendas(boolean state){
        this.recolheEncomendas = state;
    }

    public void setKmsPercorridos(double quilometros){
        this.kmsPercorridos = quilometros;
    }

    public void setRegistoCusto(Map<EncomendaAceite,Gastos> registoCusto){
        this.registoCusto = new HashMap<>();
        for(Map.Entry<EncomendaAceite, Gastos> e : registoCusto.entrySet())
            this.registoCusto.put(e.getKey().clone(), e.getValue().clone());

    }

    public void setTotalFaturado(double totalFaturado){
        this.totalFaturado = totalFaturado;
    }

    public void setprecoPorKm(double precoPorKm){
        this.precoPorKm = precoPorKm;
    }

    //------------------------------------- Funcoes auxiliares e/ou de teste -------------------------------------------

    public int numeroEncomendas(){
        return this.registoCusto.keySet().size();
    }

    public ArrayList<String> getEncomendas(){
        ArrayList<String> resultados = new ArrayList<>();
        for(EncomendaAceite a : this.registoCusto.keySet()){
            resultados.add(a.getCodigoEncomenda());
        }
        return resultados;
    }

}
