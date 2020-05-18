package clients;

import servers.Server;

import java.io.Console;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public Client() throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendEcho(String msg) {
        try {
            buf = msg.getBytes();
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            buf = new byte[512];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(
                    packet.getData(), 0, packet.getLength());
            return received;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            System.out.println(client.sendEcho("time"));
            System.out.println(client.sendEcho("date"));
            System.out.println(client.sendEcho("info"));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
