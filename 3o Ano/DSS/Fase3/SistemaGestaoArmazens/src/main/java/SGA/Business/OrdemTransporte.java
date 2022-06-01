package SGA.Business;

import SGA.dataaccess.ClassInterface;

import java.util.LinkedList;
import java.util.List;

public class OrdemTransporte implements ClassInterface<Integer> {
    private int id;
    private String estado;
    private String tipo;
    private int Localizacao_id;
    private Integer Robot_id;
    private int Gestor_id;

    public OrdemTransporte() {
        this.id = 0;
        this.estado = "";
        this.tipo = "";
        this.Localizacao_id = 0;
        this.Robot_id = 0;
        this.Gestor_id = 0;
    }

    public OrdemTransporte(int id, String estado, String tipo, int Localizacao_id, Integer Robot_id, int Gestor_id) {
        this.id = id;
        this.estado = estado;
        this.tipo = tipo;
        this.Localizacao_id = Localizacao_id;
        this.Robot_id = Robot_id;
        this.Gestor_id = Gestor_id;
    }

    public OrdemTransporte(List<String> rows) {
        this.id = Integer.parseInt(rows.get(0));
        this.estado = rows.get(1);
        this.tipo = rows.get(2);
        this.Localizacao_id = Integer.parseInt(rows.get(3));
        this.Robot_id = rows.get(4) != null ? Integer.parseInt(rows.get(4)) : null;
        this.Gestor_id = Integer.parseInt(rows.get(5));
    }

    @Override
    public Integer key() {
        return this.id;
    }

    @Override
    public ClassInterface<Integer> fromRow(List<String> row) {
        return new OrdemTransporte(row);
    }

    @Override
    public List<String> toRow() {
        List<String> l = new LinkedList<>();
        l.add(String.valueOf(this.id));
        l.add(String.valueOf(this.estado));
        l.add(String.valueOf(this.tipo));
        l.add(String.valueOf(this.Localizacao_id));
        l.add(String.valueOf(this.Robot_id));
        l.add(String.valueOf(this.Gestor_id));
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getLocalizacao_id() {
        return Localizacao_id;
    }

    public void setLocalizacao_id(int localizacao_id) {
        Localizacao_id = localizacao_id;
    }

    public String getRobot_id() {
        return Robot_id != null ? String.valueOf(Robot_id) : "Pendente";
    }

    public void setRobot_id(int robot_id) {
        Robot_id = robot_id;
    }

    public int getGestor_id() {
        return Gestor_id;
    }

    public void setGestor_id(int gestor_id) {
        Gestor_id = gestor_id;
    }
}
