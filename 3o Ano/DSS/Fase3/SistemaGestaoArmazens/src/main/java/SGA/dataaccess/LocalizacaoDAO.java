package SGA.dataaccess;

import SGA.Business.Localizacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class LocalizacaoDAO extends GenericDAO<Integer> {
    public LocalizacaoDAO() {
        super(
                "Localizacao",
                new Localizacao(),
                Arrays.asList("id", "seccao", "prateleira")
        );
    }

    public Map<Integer, Localizacao> getAllLocalizacoes() {
        Map<Integer, ClassInterface<Integer>> a = super.getAll();

        Map<Integer, Localizacao> r = new HashMap<>();
        a.forEach((k, v) -> r.put(k, (Localizacao) v));
        return r;
    }

    public Localizacao getLocalizacao(int seccao, int prateleira) {
        Localizacao localizacao = new Localizacao();

        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Localizacao WHERE seccao = '" + seccao + "' and prateleira = '" + prateleira + "' ;";
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                List<String> row = new LinkedList<>();
                int col = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= col; i++)
                    row.add(rs.getString(i));

                return (Localizacao) localizacao.fromRow(row);
            }

            return null;
        } catch (SQLException e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
