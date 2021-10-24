package major.adam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Echoer extends Thread {
    private Socket socket;
    private String EXIT_STRING = "exit";

    public Echoer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            while (true) {
                String echoString = input.readLine();
                if (echoString != null && echoString.equalsIgnoreCase(EXIT_STRING)) break;
                output.println(echoString);
            }

        } catch (IOException e) {
            System.out.println("Oops: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Consider logging this...");
            }
        }
    }
}
