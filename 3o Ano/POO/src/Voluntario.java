
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Voluntario extends UtilizadorGeral{
    private static final long serialVersionUID = 1011866795999694074L;

    private double raioGeografico;
    private boolean livre;
    private boolean podeTransportarMedicamentos;
    private Map<EncomendaAceite,Integer> registoEncomendasTempo; //vai associar cada encomenda ao respetivo tempo de entrega



    public Voluntario(){
        super();
        this.raioGeografico = 0;
        this.livre = false;
        this.podeTransportarMedicamentos = false;
        this.registoEncomendasTempo = new HashMap<>();
    }

    public Voluntario (Voluntario v) {
        super(v);
        this.raioGeografico = v.getRaioGeografico();
        this.livre = v.getLivre();
        this.podeTransportarMedicamentos = v.aceitoTransporteMedicamentos();
        this.registoEncomendasTempo = v.getRegistoEncomendas();
    }

    public Voluntario (String codigo, String nome, Localizacao localizacao,Login login, int raioGeografico, boolean livre,boolean podeTransportarMedicamentos,
                       Map<EncomendaAceite,Integer> registoEncomendasTempo){
        super(codigo,nome,login,localizacao);
        this.raioGeografico = raioGeografico;
        this.livre = livre;
        this.podeTransportarMedicamentos = podeTransportarMedicamentos;
        setRegistoEncomendas(registoEncomendasTempo);
    }

	public Voluntario clone() {
        return new Voluntario(this); 
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(super.toString());
        s.append("\nRaio geografico do Voluntario : ");
        s.append(raioGeografico);
        s.append("\nO voluntario esta livre : ");
        s.append(livre);
        s.append("\nO voluntario pode transportar medicamnetos : ");
        s.append(podeTransportarMedicamentos);
        s.append("\n\n");
        return s.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || this.getClass() != o.getClass())
            return false;
        Voluntario v = (Voluntario) o;

        return  super.equals(v) &&
                raioGeografico == v.getRaioGeografico() &&
                registoEncomendasTempo.equals(v.getRegistoEncomendas()) &&
                livre == v.getLivre() &&
                podeTransportarMedicamentos == v.aceitoTransporteMedicamentos();
    }



    // GETS ---------------------------------------------------------------------

    public double getRaioGeografico() {
        return this.raioGeografico;
    }

    public boolean getLivre () {
        return this.livre;
    }

    public Map<EncomendaAceite, Integer> getRegistoEncomendas() {
        Map<EncomendaAceite, Integer> res = new HashMap<>();
        for(Map.Entry<EncomendaAceite, Integer> e : this.registoEncomendasTempo.entrySet())
            res.put(e.getKey().clone(), e.getValue());
        return res;
    }

    public boolean aceitoTransporteMedicamentos() {
        return this.podeTransportarMedicamentos;
    }


    // SETS ---------------------------------------------------------------------

    public void setRaioGeografico(double raioGeografico) {
        this.raioGeografico = raioGeografico;
    }

    public void setLivre(boolean livre) {
        this.livre = livre;
    }

    public void setRegistoEncomendas(Map<EncomendaAceite,Integer> registoEncomendasTempo) {
        this.registoEncomendasTempo = new HashMap<>();
        for(Map.Entry<EncomendaAceite, Integer> e : registoEncomendasTempo.entrySet())
            this.registoEncomendasTempo.put(e.getKey().clone(), e.getValue());
    }

    public void aceitaMedicamentos(boolean state) {
        this.podeTransportarMedicamentos = state;
    }


   

    //------------------------------------ Funcoes auxiliares e/ou de teste --------------------------------------------


    //--------------- Funcao que conta as o numero de encomendas que o voluntario entregou no total

    public int numeroEncomendas(){
        return registoEncomendasTempo.keySet().size();
    }


    //---------Calcula a lista das encomendas ja entregues (codigos apenas) --------

    public List<String> getEncomendas(){
        List<String> resultados = new ArrayList<>();
        for(EncomendaAceite a: this.registoEncomendasTempo.keySet()){
            resultados.add(a.getCodigoEncomenda());
        }
        return resultados;
    }

}

