package threads;

import servers.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

public class EchoThread extends Thread {


    /**
     * UUID socket
     */
    protected UUID uuid;

    /**
     * List subscribed topics
     */
    protected Server server;

    /**
     * Client socket
     */
    protected Socket socket;


    /**
     * List clients
     */
    protected List<EchoThread> clients;

    /**
     * XZ
     */
    protected PrintWriter writer;


    public UUID getUuid() {
        return uuid;
    }

    public EchoThread(Socket clientSocket, Server server) {
        this.socket = clientSocket;
        this.uuid = UUID.randomUUID();
        this.server = server;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String serverMessage = "New user connected: " + this.uuid;
        server.broadcast(serverMessage, this);

        String line;
        try {
            do {
                line = brinp.readLine();
                serverMessage = "[" + this.uuid + "]: " + line;
                server.broadcast(serverMessage, this);
            } while (!line.equalsIgnoreCase("bye"));
            socket.close();
            server.disconnected(this);
            serverMessage = this.uuid + " has quitted.";
            server.broadcast(serverMessage, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        writer.println(message);
    }
}