import java.util.ArrayList;
import java.util.List;

public class Loja extends UtilizadorGeral {
    private static final long serialVersionUID = -7241504802280655923L;

    private int raio;
    private int nEncEntregues;
    private List<String> encProntas; //lista de encomendas prontas a ser entregues


    public Loja() {
        super();
        this.raio = 0;
        this.nEncEntregues = 0;
        this.encProntas = new ArrayList<>();
    }

    public Loja(Loja l) {
        super(l);
        this.raio = l.getRaio();
        this.nEncEntregues = l.getNumeroEncEntregues();
        this.encProntas = l.getEncProntas();
    }


    public Loja(String codigo, String nome, Login login, Localizacao localizacao,
                 int raio, int nEncEntregues, List<String> encProntas) { 

        super(codigo, nome, login, localizacao);
        this.raio = raio;
        this.nEncEntregues = nEncEntregues;
        this.encProntas = encProntas;
    }


    public Loja clone() {
        return new Loja(this);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        super.toString();
        s.append("\nRaio Geografico : ");
        s.append(this.raio);
        s.append("\nNumero total de encomendas entregues: ");
        s.append(this.nEncEntregues);
        s.append("\nEncomendas prontas a ser entregues:");
        for (String str : this.encProntas) {
            s.append(str).append("\n");
        }
        s.append("\n\n");

        return s.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || this.getClass() != o.getClass())
            return false;
        Loja l = (Loja) o;

        return super.equals(l) &&
                raio == l.getRaio() &&
                nEncEntregues == l.getNumeroEncEntregues();
    }

    @Override
    public int numeroEncomendas() {
        return this.nEncEntregues;
    }

    // GETS ---------------------------------------------------------------------------

    public int getRaio() {
        return this.raio;
    }

    public int getNumeroEncEntregues() {
        return this.nEncEntregues;
    }

    public List<String> getEncProntas() {
        List<String> res = new ArrayList<>();
        for (String enc : this.encProntas) {
            res.add(enc);
        }
        return res;
    }


    // SETS ---------------------------------------------------------------------------

    public void setRaio(int raioGeografico) {
        this.raio = raioGeografico;
    }

    public void setNumeroEncEntregues(int nEncEntregues) {
        this.nEncEntregues = nEncEntregues;
    }


    public void setEncsProntas(List<String> encs) {
        this.encProntas = new ArrayList<>();
        for (String e : encs) {
            this.encProntas.add(e);
        }
    }


//------------------------------------- Funcoes auxiliares e/ou de teste -----------------------------------------------


    public void addEncomenda(String enc) {
        this.encProntas.add(enc);
    }

    public void removeEncomenda(String enc){
        this.encProntas.remove(enc);
    }

}