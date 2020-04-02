package clients;

import java.io.*;
import java.net.Socket;

public class ClientTCP {

    public static void main(String args[]) {
        try {
            Socket clientSocket = new Socket("localhost", 1500);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter((clientSocket.getOutputStream()), true);

            System.out.println( in.readLine());

            out.println("get_group");
            System.out.println(in.readLine());

            out.println("get_names");
            System.out.println(in.readLine());

            out.println("get_name");
            System.out.println(in.readLine());


            out.close();
            in.close();
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}