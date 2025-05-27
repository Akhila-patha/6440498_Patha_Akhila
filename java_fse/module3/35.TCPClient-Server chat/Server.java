import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server started. Waiting for client on port 1234...");
            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Client connected.");
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Client: " + message);
                    System.out.print("Server: ");
                    String response = console.readLine();
                    if (response == null || response.equalsIgnoreCase("exit")) {
                        break;
                    }
                    out.println(response);
                }
                System.out.println("Server shutting down.");
            } catch (IOException e) {
                System.err.println("Client error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}