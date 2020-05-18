package servers;

import threads.EchoThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Server {

    protected int PORT;

    protected Set<EchoThread> clients = new HashSet<>();

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("New user connected");
                EchoThread thread = new EchoThread(socket, this);
                this.clients.add(thread);
                thread.start();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
        }
    }

    public void broadcast(String message, EchoThread excludeUser) {
        for (EchoThread client : this.clients) {
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
