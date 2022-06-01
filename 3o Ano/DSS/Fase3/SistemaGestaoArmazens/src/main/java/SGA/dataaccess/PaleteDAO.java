package SGA.dataaccess;

import SGA.Business.Palete;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class PaleteDAO extends GenericDAO<Integer> {
    public PaleteDAO() {
        super(
                "Palete",
                new Palete(),
                Arrays.asList("id", "Localizacao_id", "CodigoQR_id")
        );
    }

    public Palete deletePalete(int Localizacao_id) {
        try {
            Statement stm = conn.createStatement();
            String sql = "DELETE FROM Palete WHERE Localizacao_id = '" + Localizacao_id + "' ;";
            stm.executeUpdate(sql);

            return null;
        } catch (SQLException e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
