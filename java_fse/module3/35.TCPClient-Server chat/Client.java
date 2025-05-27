import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Connected to server. Type 'exit' to quit.");
            String message;
            while (true) {
                System.out.print("Client: ");
                message = console.readLine();
                if (message == null || message.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(message);
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server disconnected.");
                    break;
                }
                System.out.println("Server: " + response);
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}