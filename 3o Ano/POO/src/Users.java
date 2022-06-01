import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.io.Serializable;
import java.util.Map;


public class Users implements Serializable{
    private static final long serialVersionUID = -1348992759216451180L;
    TreeMap<String, UtilizadorGeral> users;
    
    
    public Users(){
        this.users = new TreeMap<>();
    }
    
    public Users(TreeMap<String, UtilizadorGeral> users){
        setUsers(users);
    }

    public Users(Users u){
        this.users = u.getUsers();
        
    }

    //-------- GETS --------------------------------------------------
    
    public TreeMap<String, UtilizadorGeral> getUsers(){
        TreeMap<String, UtilizadorGeral> res = new TreeMap<>();
        for (Map.Entry<String, UtilizadorGeral> u : this.users.entrySet())
            res.put(u.getKey(), u.getValue().clone());
        return res;
    }

    //retorna uma lista de users apenas do tipe requerido
    public TreeMap<String, UtilizadorGeral> getUsers(char type){
        TreeMap<String, UtilizadorGeral> res = new TreeMap<>();
        for (Map.Entry<String, UtilizadorGeral> u : this.users.entrySet())
            if (u.getKey().charAt(0) == type) res.put(u.getKey(), u.getValue().clone());
        return res;
    }

    //--------- SETS -------------------------------------------------

    public void setUsers(TreeMap<String, UtilizadorGeral> us) {
        this.users = new TreeMap<>();
        us.entrySet().forEach(u -> { this.users.put(u.getKey(), u.getValue().clone()); });
    }

    //------Funcoes auxiliares/de teste -------------------------------
    
    void addUser(UtilizadorGeral u) {
        this.users.put(u.getCodigo(), u.clone());
    }


    UtilizadorGeral getUser(String cod) {
        UtilizadorGeral u = this.users.get(cod);
        return u;
    }
     
    public Users clone(){
        return new Users(this);
    }

    public String novoCodigoUser(char userTipo){
        String cod = this.getUsers(userTipo).lastEntry().getKey();
        int num = Integer.parseInt(cod.substring(1));
        num++;
        cod = userTipo+""+num;
        return cod;
    }

    public List<UtilizadorGeral> getUgerais(){ // devolve uma lista de todos os utilizadores (gerais)
        List<UtilizadorGeral> lista = new ArrayList<>();
        for (UtilizadorGeral u : getUsers().values()){
            lista.add(u);
        }
        return lista;
    }

    public List<UtilizadorGeral> getTypeUsers (char type){
        List<UtilizadorGeral> lista = new ArrayList<>();
        for (UtilizadorGeral u : getUsers().values()){
            if (u.getCodigo().charAt(0) == type) lista.add(u);
        }
        return lista;
    }
}