package sd.client;

import java.io.IOException;

public class InputHandler implements Runnable {
    public void run() {
        try {
            listenForAlertsAux();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void listenForAlertsAux() throws IOException, InterruptedException {
        String line;

        while (true) {
            Client.reentrantLock.lock();

            line = Client.getSocketReader().readLine();

            if (line == null || line.equals("bye")) {
                System.exit(0);
            }

            if (line.startsWith("warning")) {
                System.out.println("\n\u001B[31m" + line + "\u001B[0m");
                System.out.print("\u001B[33m" + "AlarmeCovid> " + "\u001B[0m");
            } else if (line.startsWith("notification")){
                System.out.println("\n\u001B[34m" + line + "\u001B[0m");
                System.out.print("\u001B[33m" + "AlarmeCovid> " + "\u001B[0m");
            } else {
                System.out.println(line.replaceAll("!", "\n"));
            }

            Client.reentrantLock.unlock();
        }
    }
}
