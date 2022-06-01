package SGA.dataaccess;

import SGA.Business.Robot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RobotDAO extends GenericDAO<Integer> {
    public RobotDAO() {
        super(
                "Robot",
                new Robot(),
                Arrays.asList("id", "password", "estado")
        );
    }

    public Map<Integer, Robot> getAllRobots() {
        Map<Integer, ClassInterface<Integer>> a = super.getAll();

        Map<Integer, Robot> r = new HashMap<>();
        a.forEach((k, v) -> r.put(k, (Robot) v));
        return r;
    }
}
