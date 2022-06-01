package SGA.Business;

import SGA.dataaccess.ClassInterface;

import java.util.LinkedList;
import java.util.List;

public class Palete implements ClassInterface<Integer> {
    private int id;
    private int Localizacao_id;
    private int CodigoQR_id;

    public Palete() {
        this.id = 0;
        this.Localizacao_id = 0;
        this.CodigoQR_id = 0;
    }

    public Palete(int id, int Localizacao_id, int CodigoQR_id) {
        this.id = id;
        this.Localizacao_id = Localizacao_id;
        this.CodigoQR_id = CodigoQR_id;
    }

    public Palete(List<String> rows) {
        this.id = Integer.parseInt(rows.get(0));
        this.Localizacao_id = Integer.parseInt(rows.get(1));
        this.CodigoQR_id = Integer.parseInt(rows.get(2));
    }

    @Override
    public Integer key() {
        return this.id;
    }

    @Override
    public ClassInterface<Integer> fromRow(List<String> row) {
        return new Palete(row);
    }

    @Override
    public List<String> toRow() {
        List<String> l = new LinkedList<>();
        l.add(String.valueOf(this.id));
        l.add(String.valueOf(this.Localizacao_id));
        l.add(String.valueOf(this.CodigoQR_id));
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

    public int getLocalizacao_id() {
        return Localizacao_id;
    }

    public void setLocalizacao_id(int localizacao_id) {
        Localizacao_id = localizacao_id;
    }

    public int getCodigoQR_id() {
        return CodigoQR_id;
    }

    public void setCodigoQR_id(int codigoQR_id) {
        CodigoQR_id = codigoQR_id;
    }
}
