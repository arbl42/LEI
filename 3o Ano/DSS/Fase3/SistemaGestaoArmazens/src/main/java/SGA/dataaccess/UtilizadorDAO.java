package SGA.dataaccess;

import SGA.Business.Utilizador;

import java.util.Arrays;

public class UtilizadorDAO extends GenericDAO<Integer> {
    public UtilizadorDAO() {
        super(
                "Utilizador",
                new Utilizador(),
                Arrays.asList("id", "nome", "password", "tipo")
        );
    }
}
