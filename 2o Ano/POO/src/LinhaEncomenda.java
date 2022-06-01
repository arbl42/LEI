import java.io.Serializable;


public class LinhaEncomenda implements Serializable{
    private static final long serialVersionUID = 5904479582492512587L;
    private String codProduto;
    private String descricao;
    private double precoUnitario;
    private double quantidade;

    public LinhaEncomenda() {
        this.codProduto = "n/a";
        this.descricao = "n/a";
        this.precoUnitario = 0;
        this.quantidade = 0;
    }

    public LinhaEncomenda(String codProduto, String descricao, double precoUnitario,
                          double quantidade) {
        this.codProduto = codProduto;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public LinhaEncomenda(LinhaEncomenda linha) {
        this.codProduto = linha.getCodProduto();
        this.descricao = linha.getDescricao();
        this.precoUnitario = linha.getPrecoUnitario();
        this.quantidade = linha.getQuantidade();
    }


    //Calcula o preco total
    public double calculaValorLinhaEnc() {
        return this.quantidade * this.precoUnitario;
    }


    //Metodos de acesso e alteracao
    public String getCodProduto() {
        return this.codProduto;
    }

    public void setCodProduto(String referencia) {
        this.codProduto = referencia;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoUnitario() {
        return this.precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }


    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        LinhaEncomenda le = (LinhaEncomenda) obj;
        return le.getCodProduto().equals(this.codProduto) &&
                le.getQuantidade() == this.quantidade &&
                le.getDescricao().equals(this.descricao) &&
                le.getPrecoUnitario() == this.precoUnitario;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo do produto: ").append(this.codProduto);
        sb.append(" Descricao: ").append(this.descricao);
        sb.append(" Quantidade: ").append(this.quantidade);
        sb.append(" Preco Unitario: ").append(this.precoUnitario);
        return sb.toString();
    }

    public LinhaEncomenda clone() {
        return new LinhaEncomenda(this);
    }

}