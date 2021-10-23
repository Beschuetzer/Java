package major.adam;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                //starting a new thread everytime a new connection is accepted

                //the verbose way
//                Socket connectedSocket = serverSocket.accept();
//                Echoer echoForConnectedSocket = new Echoer(connectedSocket);
//                echoForConnectedSocket.start();

                //using one line
                new Echoer(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
