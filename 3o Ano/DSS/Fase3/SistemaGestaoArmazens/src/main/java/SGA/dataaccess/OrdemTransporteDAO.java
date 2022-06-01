package SGA.dataaccess;

import SGA.Business.OrdemTransporte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class OrdemTransporteDAO extends GenericDAO<Integer> {
    public OrdemTransporteDAO() {
        super(
                "OrdemTransporte",
                new OrdemTransporte(),
                Arrays.asList("id", "estado", "tipo", "Localizacao_id", "Robot_id", "Gestor_id")
        );
    }

    public Map<Integer, OrdemTransporte> getAllOrdensTransporte() {
        Map<Integer, ClassInterface<Integer>> a = super.getAll();

        Map<Integer, OrdemTransporte> r = new HashMap<>();
        a.forEach((k, v) -> r.put(k, (OrdemTransporte) v));
        return r;
    }

    public Map<Integer, OrdemTransporte> getOrdensTransportePendentes() {
        try {
            Map<Integer, OrdemTransporte> map = new HashMap<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM OrdemTransporte WHERE estado = 'Pendente' ;");

            List<String> row;
            int col = rs.getMetaData().getColumnCount();


            while (rs.next()) {

                row = new ArrayList<>();
                for (int i = 1; i <= col; i++)
                    row.add(rs.getString(i));
                OrdemTransporte ordemTransporte = new OrdemTransporte(row);
                map.put(ordemTransporte.key(), ordemTransporte);
            }

            return map;
        } catch (SQLException e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
