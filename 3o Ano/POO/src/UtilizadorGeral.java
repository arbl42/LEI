import java.io.Serializable;
import java.util.Objects;

public abstract class UtilizadorGeral implements Serializable{
    private static final long serialVersionUID = -3959801163615450249L;
    private String codigo;
    private String nome;
    private Login login;
    private Localizacao localizacao;


    // CONSTRUTORES ---------------------------------------------------------------------------

    public UtilizadorGeral() {
        this.codigo = "not defined";
        this.nome = "not defined";
        this.login = new Login();
        this.localizacao = new Localizacao();
    }

    public UtilizadorGeral(String codigo, String nome, Login login, Localizacao localizacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.login = login.clone();
        this.localizacao = localizacao.clone();
    }

    
    public UtilizadorGeral(String codigo, String nome, Localizacao loc) {
        this.codigo = codigo;
        this.nome = nome;
        this.localizacao = loc.clone();
        this.login = new Login(codigo+"@trazaqui.com", "1234");
        
    }


    public UtilizadorGeral(UtilizadorGeral ug) {
        this.codigo = ug.getCodigo();
        this.nome = ug.getNome();
        this.login = ug.getLogin();
        this.localizacao = ug.getLocalizacao().clone();
        
    }




    // GETS ---------------------------------------------------------------------------

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public Login getLogin() {
        return this.login.clone();
    }

    public Localizacao getLocalizacao() {
        return this.localizacao.clone();
    }

    // SETS ---------------------------------------------------------------------------


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(Login login){
        this.login = login.clone();
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao.clone();
    }




    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Codigo: ");
        sb.append(codigo);
        sb.append("\nNome: ");
        sb.append(nome);
        sb.append("\nLogin data: ");
        sb.append(this.login.toString());
        sb.append("\nLocalizacao: ");
        sb.append(this.localizacao.toString());

        sb.append("\n\n");
        return sb.toString();
    }


    public abstract UtilizadorGeral clone();


    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        UtilizadorGeral ug = (UtilizadorGeral) obj;

        return Objects.equals(codigo, ug.getCodigo()) &&
                Objects.equals(nome, ug.getNome()) &&
                Objects.equals(localizacao, ug.getLocalizacao());
    }

//------------------------------- Funcoes auxiliares e/ou de teste------------------------------------------------------

    public abstract int numeroEncomendas();

}
