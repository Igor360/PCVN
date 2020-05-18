package servers;

import threads.EchoThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    protected int PORT;

    static List<EchoThread> clients;

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public void run() {
        ServerSocket serverSocket = null;
//        Socket socket = null;
        clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
                EchoThread thread = new EchoThread(socket, this);
                clients.add(thread);
                thread.run();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
        }
    }

    public void broadcast(String message, EchoThread excludeUser) {
        for (EchoThread client : clients) {
            if (client != excludeUser) {
                client.write(message);
            }
        }
    }

    public void disconnected(EchoThread user) {
        clients.remove(user);
        System.out.println("The user " + user.getUuid().toString() + " quitted");
    }

    public static void main(String[] args) {
        int port = 1917;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        Server server = new Server(port);
        server.run();
    }

}
