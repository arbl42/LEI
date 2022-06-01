package SGA.Business;

import SGA.dataaccess.ClassInterface;

import java.util.LinkedList;
import java.util.List;

public class Localizacao implements ClassInterface<Integer> {
    private int id;
    private int seccao;
    private int prateleira;

    public Localizacao() {
        this.id = 0;
        this.seccao = 0;
        this.prateleira = 0;
    }

    public Localizacao(int id, int seccao, int prateleira) {
        this.id = id;
        this.seccao = seccao;
        this.prateleira = prateleira;
    }

    public Localizacao(List<String> rows) {
        this.id = Integer.parseInt(rows.get(0));
        this.seccao = Integer.parseInt(rows.get(1));
        this.prateleira = Integer.parseInt(rows.get(2));
    }

    @Override
    public Integer key() {
        return this.id;
    }

    @Override
    public ClassInterface<Integer> fromRow(List<String> row) {
        return new Localizacao(row);
    }

    @Override
    public List<String> toRow() {
        List<String> l = new LinkedList<>();
        l.add(String.valueOf(this.id));
        l.add(String.valueOf(this.seccao));
        l.add(String.valueOf(this.prateleira));
        return l;
    }

    @Override
    public Integer key(String k) {
        return Integer.valueOf(k);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeccao() {
        return seccao;
    }

    public void setSeccao(int seccao) {
        this.seccao = seccao;
    }

    public int getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(int prateleira) {
        this.prateleira = prateleira;
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "id=" + id +
                ", seccao=" + seccao +
                ", prateleira=" + prateleira +
                '}';
    }
}
