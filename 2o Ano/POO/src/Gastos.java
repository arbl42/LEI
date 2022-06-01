import java.io.Serializable;

public class Gastos implements Serializable{
    private static final long serialVersionUID = 8731615607857258862L;
    private double tempoEntrega;
    private double precoTotal;

    public Gastos(){
        this.tempoEntrega = 0;
        this.precoTotal = 0;
    }

    public Gastos(double tempoEntrega, double precoTotal){
        this.tempoEntrega = tempoEntrega;
        this.precoTotal = precoTotal;
    }

    public Gastos(Gastos gastos){
        this.tempoEntrega = gastos.getTempoEntrega();
        this.precoTotal = gastos.getPrecoTotal();
    }

    public Gastos clone() {
        return new Gastos(this);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Gastos:\nTempo de Entrega: ");
        s.append(this.tempoEntrega);
        s.append("\nPreco total da encomenda: ");
        s.append(this.precoTotal);
        return s.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || this.getClass() != o.getClass())
            return false;
        Gastos gt = (Gastos) o;

        return  this.precoTotal == gt.getPrecoTotal() &&
                this.tempoEntrega == gt.getTempoEntrega();
    }

    //Metodos de acesso
    public double getTempoEntrega(){return this.tempoEntrega;}

    public double getPrecoTotal() {return this.precoTotal; }

    //Metodos de alteracao
    public void setTempoEntrega(double tempoEntrega){this.tempoEntrega = tempoEntrega;}

    public void setPrecoTotal(double precoTotal){this.precoTotal = precoTotal;}

}
