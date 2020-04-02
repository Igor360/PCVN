package servers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class ServerTCP {

    ServerSocket serverSocket = null;

    public ServerTCP() {
        try {
            serverSocket = new ServerSocket(1500);
            System.out.println("Starting the server ");
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Socket clientSocket;
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Connection accepted from " + clientSocket.getInetAddress().getHostAddress());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("Client connected to sockets");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if ("get_names".equals(inputLine)) {
                        writer.println("I....");
                    }else if ("get_group".equals(inputLine)) {
                        writer.println("IT-91mn");
                    }else {
                        clientSocket.close();
                        break;
                    }
                    System.out.println(inputLine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ServerTCP();
    }

}