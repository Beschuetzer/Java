package major.adam;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(5000);
            while (true) {
                byte[] buffer = new byte[50];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);
                String receivedString = new String(buffer, 0, datagramPacket.getLength());
                System.out.println("Text received  is: " + receivedString);

                String returnString = "Server received: " + receivedString;
                byte[] returnBuffer = returnString.getBytes(StandardCharsets.UTF_8);

                DatagramPacket returnPacket = new DatagramPacket(returnBuffer, returnBuffer.length, datagramPacket.getAddress(), datagramPacket.getPort());

                try{
                    //simulating a delayed response from serve (client blocks at .receive())
                    Thread.sleep(2000);
                    socket.send(returnPacket);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
