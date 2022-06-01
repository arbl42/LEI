package SGA.Business;

import SGA.dataaccess.ClassInterface;

import java.util.LinkedList;
import java.util.List;

public class CodigoQR implements ClassInterface<Integer> {
    private int id;
    private String numero;

    public CodigoQR() {
        this.id = 0;
        this.numero = null;
    }

    public CodigoQR(int id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public CodigoQR(List<String> rows) {
        this.id = Integer.parseInt(rows.get(0));
        this.numero = rows.get(1);
    }

    @Override
    public Integer key() {
        return this.id;
    }

    @Override
    public ClassInterface<Integer> fromRow(List<String> row) {
        return new CodigoQR(row);
    }

    @Override
    public List<String> toRow() {
        List<String> l = new LinkedList<>();
        l.add(String.valueOf(this.id));
        l.add(String.valueOf(this.numero));
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
