package major.adam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            //setting socket timeout (throws SocketTimeoutException)
            socket.setSoTimeout(4000);
            BufferedReader echos = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            String echoString;
            String response;

            do {
                System.out.println("Enter String to be echoed: ");
                echoString = scanner.nextLine();
                stringToEcho.println(echoString);

                if (!echoString.equalsIgnoreCase("exit")) {
                    response = echos.readLine();
                    System.out.println(response);
                }
            } while (!echoString.equalsIgnoreCase("exit"));

        } catch (UnknownHostException e) {
            System.out.println("Client Error: " + e.getLocalizedMessage());
        } catch (SocketTimeoutException e) {
            System.out.println("Server is not responding...");
        } catch (IOException e) {
            System.out.println("Client Error: " + e.getLocalizedMessage());
        }
    }
}
