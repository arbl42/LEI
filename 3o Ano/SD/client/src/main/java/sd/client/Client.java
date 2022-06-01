package sd.client;

import org.springframework.shell.Bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private static PrintWriter socketWriter;
    private static BufferedReader socketReader;

    public static ReentrantLock reentrantLock = new ReentrantLock(true);

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);

        socketWriter = new PrintWriter(socket.getOutputStream(), true);
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread thread = new Thread(new InputHandler());
        thread.start();

        Bootstrap.main(args);
    }

    public static PrintWriter getSocketWriter() {
        return socketWriter;
    }

    public static BufferedReader getSocketReader() {
        return socketReader;
    }
}

