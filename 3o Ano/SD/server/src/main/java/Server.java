import controllers.ClientEntrypoint;
import models.AlarmeCovid;
import models.User;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("Server connected.");
        AlarmeCovid alarmeCovid = new AlarmeCovid();

        createAdmin(alarmeCovid);

        new ClientEntrypoint(alarmeCovid);
    }

    private static void createAdmin(AlarmeCovid alarmeCovid) {
        User user = new User("admin", "123456", null);
        alarmeCovid.createAdmin(user);
    }
}
