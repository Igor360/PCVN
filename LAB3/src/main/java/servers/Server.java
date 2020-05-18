package servers;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[512];

    public Server(int port) throws IOException {
        socket = new DatagramSocket(port);
    }

    public void run() {
        running = true;

        while (running) {
            try {
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);

                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received
                        = new String(packet.getData(), 0, packet.getLength());
                if (received.indexOf("time") == 0) {
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss z");
                    Date date = new Date(System.currentTimeMillis());
                    byte [] buffer = formatter.format(date).getBytes();
                    packet =  new DatagramPacket(buffer, buffer.length, address, port);
                }
                if (received.indexOf("date") == 0) {
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(System.currentTimeMillis());
                    byte [] buffer = formatter.format(date).getBytes();
                    packet =  new DatagramPacket(buffer, buffer.length, address, port);
                }
                if (received.indexOf("info") == 0) {
                    String info = "IT-91mn Davidenko";
                    byte [] buffer = info.toString().getBytes();
                    packet = new DatagramPacket(buffer, buffer.length, address, port);
                }
                if (received.equals("end")) {
                    running = false;
                    continue;
                }
                socket.send(packet);
            } catch (IOException exception) {
                exception.printStackTrace();
                break;
            }
        }
        socket.close();
    }

    public static void main(String[] args) {
        int port = 4445;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        try {
            Server server = new Server(port);
            server.run();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
