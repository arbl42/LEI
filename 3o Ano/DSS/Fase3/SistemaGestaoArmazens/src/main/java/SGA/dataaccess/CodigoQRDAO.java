package SGA.dataaccess;

import SGA.Business.CodigoQR;

import java.util.Arrays;

public class CodigoQRDAO extends GenericDAO<Integer> {
    public CodigoQRDAO() {
        super(
                "CodigoQR",
                new CodigoQR(),
                Arrays.asList("id", "numero")
        );
    }
}
