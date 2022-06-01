public class Utilizador extends UtilizadorGeral{
    private static final long serialVersionUID = -3304510842456497477L;




    // CONSTRUTORES ---------------------------------------------------------------------------


    public Utilizador() {
        super();
    }

    public Utilizador(Utilizador u) {
        super(u);

    }

    public Utilizador(String codigo, String nome, Login login, Localizacao localizacao) {
        super(codigo, nome, login, localizacao);
    }

    public Utilizador(String codigo, String nome, Localizacao localizacao) {
        super(codigo, nome, localizacao);
    }





    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(super.toString());
        s.append("\n\n");

        return s.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Utilizador ug = (Utilizador) obj;

        return super.equals(ug);

    }

    @Override
    public int numeroEncomendas() {
        return 0;
    }


    public Utilizador clone() {
        return new Utilizador(this);
    }



}
