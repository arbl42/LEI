package SGA.Business;

import SGA.dataaccess.ClassInterface;

import java.util.LinkedList;
import java.util.List;

public class Utilizador implements ClassInterface<Integer> {
    private int id;
    private String nome;
    private String password;
    private String tipo;

    public Utilizador() {
        this.id = 0;
        this.nome = "";
        this.password = "";
        this.tipo = "";
    }

    public Utilizador(int id, String nome, String password, String tipo) {
        this.id = id;
        this.nome = nome;
        this.password = password;
        this.tipo = tipo;
    }

    public Utilizador(List<String> rows) {
        this.id = Integer.parseInt(rows.get(0));
        this.nome = rows.get(1);
        this.password = rows.get(2);
        this.tipo = rows.get(3);
    }

    @Override
    public Integer key() {
        return this.id;
    }

    @Override
    public ClassInterface<Integer> fromRow(List<String> row) {
        return new Utilizador(row);
    }

    @Override
    public List<String> toRow() {
        List<String> l = new LinkedList<>();
        l.add(String.valueOf(this.id));
        l.add(String.valueOf(this.nome));
        l.add(String.valueOf(this.password));
        l.add(String.valueOf(this.tipo));
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean validarCredenciais(int id, String password) {
        return this.id == id && this.password.equals(password);
    }
}
