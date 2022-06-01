package controllers;

import models.AlarmeCovid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final AlarmeCovid alarmeCovid;

    public ClientHandler(Socket socket, AlarmeCovid alarmeCovid) {
        this.socket = socket;
        this.alarmeCovid = alarmeCovid;
    }

    public void run() {
        try {
            acceptClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptClient() throws IOException {
        String inputLine, outputLine;

        PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        Router router = new Router(alarmeCovid);

        while ((inputLine = in.readLine()) != null) {
            outputLine = router.processInput(inputLine, out);
            out.println(outputLine);
            if (outputLine.equals("bye"))
                break;
        }
    }
}
