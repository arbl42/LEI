import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Encomenda implements Serializable{
    private static final long serialVersionUID = -7029014923111543093L;
    private String codUtilizador, codTransportadora, codLoja, nomeVendedor, codEncomenda; //codTransportadora -> codigo de transportadora ou voluntario
    private double peso;
    private boolean encomendaMedica;
    private List<LinhaEncomenda> linhas;
    private int classificacao;
    private boolean entregue;
    private boolean aceite;



    public Encomenda() {
        this.codEncomenda = "not defined";
        this.codUtilizador = "not defined";
        this.codLoja = "not defined";
        this.nomeVendedor = "not defined";
        this.codTransportadora = "not defined";
        this.peso = 0;
        this.encomendaMedica = false;
        this.linhas = new ArrayList<>();
        this.aceite = false;
        this.entregue = false;
        this.classificacao = -1;
    }

    public Encomenda(Encomenda e) {
        this.codEncomenda = e.getCodEncomenda();
        this.codLoja = e.getCodLoja();
        this.codUtilizador = e.getCodUtilizador();
        this.nomeVendedor = e.getNomeVendedor();
        this.codTransportadora = e.getcodTransportadora();
        this.peso = e.getPeso();
        this.encomendaMedica = e.getEncomendaMedica();
        this.linhas = e.getLinhas();
        this.aceite = e.isAceite();
        this.classificacao = e.getClassificacao();
    }

    public Encomenda(String codUtilizador, String codLoja, String codEncomenda, String codTransportadora, List<LinhaEncomenda> l,
                     String nomeVendedor, double peso, boolean encomendaMedica, int classificacao, boolean aceite,boolean entregue) {
        this.codEncomenda = codEncomenda;
        this.codUtilizador = codUtilizador;
        this.codLoja = codLoja;
        this.nomeVendedor = nomeVendedor;
        this.peso = peso;
        this.encomendaMedica = encomendaMedica;
        setLinhas(l);
        this.classificacao = classificacao;
        this.aceite = aceite;
        this.entregue = entregue;
    }


    public Encomenda(String codUtilizador, String codLoja , String novoCodigoEncomenda, double peso) {
        this.codUtilizador = codUtilizador;
        this.codLoja = codLoja;
        this.codEncomenda = novoCodigoEncomenda;
        this.peso = peso;

	}

	public Encomenda(String codUtilizador2, String codLoja2, String codEncomenda2, List<LinhaEncomenda> l,
			String nomeVendedor2, double peso2, boolean encomendaMedica2, boolean entregue, int classificacao2,
			boolean aceite2) {

        this.codUtilizador = codUtilizador2;
        this.codLoja = codLoja2;
        this.codEncomenda = codEncomenda2;
        setLinhas(l);
        this.nomeVendedor = nomeVendedor2;
        this.peso = peso2;
        this.encomendaMedica = encomendaMedica2;
        this.entregue = entregue;
        this.classificacao = classificacao2;
        this.aceite = aceite2;
    
    
    }   

	public Encomenda clone() {
        return new Encomenda(this);
    }


    public String toString() {
        int i;
        StringBuilder s = new StringBuilder();
        s.append("\nCodigo da Encomenda : ");
        s.append(codEncomenda);
        s.append("\nNome do Utilizador : ");
        s.append(codUtilizador);
        s.append("\nCodigo da Loja : ");
        s.append(codLoja);
        s.append("\nCodigo da Transportadora : ");
        s.append(codTransportadora);
        s.append("\nLinhas de encomenda : ");
        for (LinhaEncomenda linha : linhas) {
            s.append("\n");
            s.append(linha.toString());
        }
        s.append("\nNome do vendedor :");
        s.append(nomeVendedor);
        s.append("\nPeso da encomenda : ");
        s.append(peso);
        s.append("\nEncomenda medica : ");
        s.append(encomendaMedica);
        s.append("\nA encomenda foi aceite? : ");
        s.append(this.aceite);
        return s.toString();

    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || this.getClass() != o.getClass())
            return false;
        Encomenda e = (Encomenda) o;

        return codUtilizador.equals(e.getCodUtilizador()) &&
                codLoja.equals(e.getCodLoja()) &&
                codEncomenda.equals(e.getCodEncomenda()) &&
                codTransportadora.equals(e.getcodTransportadora()) &&
                aceite == e.isAceite() &&
                nomeVendedor.equals(e.getNomeVendedor()) &&
                peso == e.getPeso() &&
                encomendaMedica == e.getEncomendaMedica() &&
                linhas.equals(e.getLinhas());
    }


    // GETS ---------------------------------------------------------------------------

    public String getCodEncomenda() {
        return this.codEncomenda;
    }

    public String getCodUtilizador() {
        return this.codUtilizador;
    }

    public String getCodLoja() {
        return this.codLoja;
    }

    public String getNomeVendedor() {
        return this.nomeVendedor;
    }

    public String getcodTransportadora(){
        return this.codTransportadora;
    }

    public double getPeso() {
        return this.peso;
    }

    public boolean getEncomendaMedica() {
        return this.encomendaMedica;
    }

    public List<LinhaEncomenda> getLinhas() {
        List<LinhaEncomenda> array = new ArrayList<>();
        for (LinhaEncomenda linha : this.linhas) {
            array.add(linha.clone());
        }
        return array;
    }

    public boolean isAceite() {
        return this.aceite;
    }

        public boolean isEntregue() {
        return this.entregue;
    }

    public int getClassificacao() {
        return this.classificacao;
    }



    // SETS ---------------------------------------------------------------------------

    public void setCodEncomenda(String codEncomenda) {
        this.codEncomenda = codEncomenda;
    }

    public void setCodUtilizador(String codUtilizador) {
        this.codUtilizador = codUtilizador;
    }

    public void setCodLoja(String codLoja) {
        this.codLoja = codLoja;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    public void setcodTransportadora(String codTransportadora){
        this.codTransportadora = codTransportadora;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setEncomendaMedica(boolean encomendaMedica) {
        this.encomendaMedica = encomendaMedica;
    }


    public void setLinhas(List<LinhaEncomenda> l) {
        ArrayList<LinhaEncomenda> array = new ArrayList<>();
        for (LinhaEncomenda linha : l) {
            array.add(linha.clone());
        }
        this.linhas = array;
    }

    public void setAceite(boolean aceite) {
        this.aceite = aceite;
    }

    public void setEntregue(boolean entregue){
        this.entregue = entregue;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
        
    }

    //--------------------------------- Calculo do valor total da encomenda --------------------------------------------

    public double calculaValorToTal() {
        double total = 0;

        for (LinhaEncomenda linha : this.linhas) {
            total += linha.calculaValorLinhaEnc();
        }

        return total;
    }

    //------------------------------- Calculo do numero total de produtos da encomenda ---------------------------------

    public int numeroTotalProdutos() {
        int total = 0;

        for (LinhaEncomenda linha : this.linhas) {
            total += linha.getQuantidade();
        }
        return total;
    }

    //----------------------------- Testa se existe um dterminado produto na encomenda ---------------------------------
    public boolean existeProdutoEncomenda(String refProduto) {
        boolean found = false;

        for (LinhaEncomenda linha : this.linhas) {
            if (linha.getCodProduto().equals(refProduto)) {
                found = true;
                break;
            }
        }

        return found;
    }

    //------------------------------- Adiciona uma linha de encomenda --------------------------------------------------

    public void adicionaLinha(LinhaEncomenda linha) {
        this.linhas.add(linha.clone());
    }


    //-------------------------- Remove um determinado produto da encomenda --------------------------------------------

    public void removeProduto(String codProd) {
        this.linhas.removeIf(linha -> linha.getCodProduto().equals(codProd));
    }


    public List<String> getListaProdutos () { //esta feita de acordo com os codigos dos produtos
        List<String> listaP = new ArrayList<>();
        for(LinhaEncomenda linha : linhas){
            listaP.add(linha.getCodProduto());
        }
        return listaP; 
    }


}