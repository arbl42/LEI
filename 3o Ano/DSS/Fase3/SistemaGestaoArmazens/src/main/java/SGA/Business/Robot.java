package SGA.Business;

import SGA.dataaccess.ClassInterface;

import java.util.LinkedList;
import java.util.List;

public class Robot implements ClassInterface<Integer> {
    private int id;
    private String password;
    private String estado;

    public Robot() {
        this.id = 0;
        this.password = "";
        this.estado = "";
    }

    public Robot(int id, String password, String estado) {
        this.id = id;
        this.password = password;
        this.estado = estado;
    }

    public Robot(List<String> rows) {
        this.id = Integer.parseInt(rows.get(0));
        this.password = rows.get(1);
        this.estado = rows.get(2);
    }

    @Override
    public Integer key() {
        return this.id;
    }

    @Override
    public ClassInterface<Integer> fromRow(List<String> row) {
        return new Robot(row);
    }

    @Override
    public List<String> toRow() {
        List<String> l = new LinkedList<>();
        l.add(String.valueOf(this.id));
        l.add(String.valueOf(this.password));
        l.add(String.valueOf(this.estado));
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean validarCredenciais(int id, String password) {
        return this.id == id && this.password.equals(password);
    }
}
