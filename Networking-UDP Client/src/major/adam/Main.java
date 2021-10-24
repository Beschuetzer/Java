package major.adam;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            //InetAddress contains many classes for working with IP addresses  like .getByName() at .getLocalHost();
            InetAddress address = InetAddress.getLocalHost();

            //DatagramSocket is a UDP socket whereas Socket is TCP
            //DatagramSockets aren't associated with a port number (port and address specified when creating DatagramPacket
            //If the server wants to respond, it has to get the address and port info from each message
            //In contrast, the TCP Socket stores the session info
            DatagramSocket datagramSocket = new DatagramSocket();

            Scanner scanner = new Scanner(System.in);
            String echoString = "";

            do {
                System.out.println("Enter string to be echoed");
                echoString = scanner.nextLine();

                byte[] buffer = echoString.getBytes(StandardCharsets.UTF_8);

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, address, 5000);
                datagramSocket.send(datagramPacket);


                //how you'd receive a response (generally don't want response with UDP though)
                byte[] buffer2 = new byte[50];
                datagramPacket = new DatagramPacket(buffer2, buffer2.length);

                //.receive() blocks thread
                datagramSocket.receive(datagramPacket);
                String receivedText = new String(buffer2, 0, datagramPacket.getLength());
                System.out.println("Text received back is: '" + receivedText + "'");

            } while (!echoString.equalsIgnoreCase("exit"));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
