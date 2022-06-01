
import java.util.TreeMap;
import java.io.Serializable;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Encomendas implements Serializable{
    private static final long serialVersionUID = -6100027063134185720L;
    private TreeMap <String, Encomenda> allEncomendas;


    public Encomendas () {
        this.allEncomendas = new TreeMap<>();
    }

    public Encomendas(Encomendas enc){
        this.allEncomendas = enc.getEncomendas();
    }


    public TreeMap<String, Encomenda> getEncomendas() {
        TreeMap<String, Encomenda> res = new TreeMap<>();
        for (Map.Entry<String, Encomenda> u : this.allEncomendas.entrySet())
            res.put(u.getKey(), u.getValue().clone());
        return res;
    }

    public void setEncomendas(Map<String, Encomenda> allEncomendas) {
        this.allEncomendas = new TreeMap<>();
        allEncomendas.entrySet().forEach(u -> {this.allEncomendas.put(u.getKey(), u.getValue().clone()); });
    }

    public void addEncomenda(Encomenda e){
        this.allEncomendas.put(e.getCodEncomenda(),e.clone());
    }

    public void aceitaEncomendas(String cod, UtilizadorGeral user){
        allEncomendas.get(cod).setAceite(true);
        allEncomendas.get(cod).setcodTransportadora(user.getCodigo());
    }

    public Encomendas clone() {
        return new Encomendas(this);
    }

    public String novoCodigoEncomenda(){
        String cod = this.getEncomendas().lastEntry().getKey();
        int num = Integer.parseInt(cod.substring(1));
        num++;
        cod = "l"+num;
        return cod;
    }

    public Encomenda getEncomenda(String codEnc){
        return this.allEncomendas.get(codEnc);
    }

    public List<Encomenda> getEncomendasRecebidas(String codUser){
        List<Encomenda> ret = new ArrayList<>();
       for(Encomenda a: this.allEncomendas.values()) {
           if ((a.getCodUtilizador().equals(codUser)) && a.isEntregue()) ret.add(a.clone());
       }
        return ret;
    }

    public List<Encomenda> getEncomendasTr(String codUser, Boolean yN){ //para os voluntarios/transportadoras
        List<Encomenda> ret = new ArrayList<>();

       for(Encomenda a: this.allEncomendas.values()) {
           String cod = a.getcodTransportadora();
            if ((cod==codUser) && (a.isAceite() == yN)) {
                ret.add(a);
            }
       }
        return ret;
    }

    public List<Encomenda> getEncomendasLj(String codUser, Boolean yN){ //para as lojas
        List<Encomenda> ret = new ArrayList<>();

        for(Encomenda a: this.allEncomendas.values()) {
            String cod = a.getCodLoja();
            if ((cod==codUser) && (a.isAceite() == yN)) {
                ret.add(a);
            }
        }
        return ret;
    }
}
