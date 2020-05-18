package clients;

import threads.ReadThread;
import threads.WriteThread;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);

            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
//            new WriteThread(socket, this).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }

    public static void main(String[] args) {
        String hostname;
        int port;
        if (args.length < 2) {
            hostname = "localhost";
            port = 1917;
        } else {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }
        Client client = new Client(hostname, port);
        client.execute();
    }
}