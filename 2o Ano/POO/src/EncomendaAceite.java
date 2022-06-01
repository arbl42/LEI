import java.io.Serializable;

public class EncomendaAceite implements Serializable{
    private static final long serialVersionUID = -7029014923111543093L;

    private String codigoEncomenda;

    public EncomendaAceite(){
        this.codigoEncomenda = "not defined";
    }

    public EncomendaAceite(EncomendaAceite re){
        this.codigoEncomenda = re.getCodigoEncomenda();
    }

    public EncomendaAceite(Encomenda enc){
        this.codigoEncomenda = enc.getCodEncomenda();
    }

    public EncomendaAceite(String codigoEncomenda){
        this.codigoEncomenda = codigoEncomenda;
    }

    public EncomendaAceite clone() {
        return new EncomendaAceite(this);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Codigo da encomenda : ");
        s.append(codigoEncomenda);
        return s.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || this.getClass() != o.getClass())
            return false;
        EncomendaAceite reg = (EncomendaAceite) o;

        return  codigoEncomenda.equals(reg.getCodigoEncomenda());
    }


    //Metodos de acesso  
    public String getCodigoEncomenda(){return this.codigoEncomenda;}
    



    //Metodos de alteracao
    public void setCodigoEncomenda(String codigoEncomenda){this.codigoEncomenda = codigoEncomenda;}
}

//------------------------------------ Funcoes auxiliares e/ou de teste ------------------------------------------------
