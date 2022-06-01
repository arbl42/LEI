package controllers;

import models.AlarmeCovid;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientEntrypoint {
    private final ServerSocket serverSocket;
    private final AlarmeCovid alarmeCovid;

    public ClientEntrypoint(AlarmeCovid alarmeCovid) throws IOException {
        this.serverSocket = new ServerSocket(8000);
        this.alarmeCovid = alarmeCovid;
        acceptClients();
    }

    private void acceptClients() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Thread thread = new Thread(new ClientHandler(socket, alarmeCovid));
            thread.start();
        }
    }
}
