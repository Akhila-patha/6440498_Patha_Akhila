import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubHttpClient_36 {

    public static void main(String[] args) {
        try {
           
            HttpClient client = HttpClient.newHttpClient();

           
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/users/octocat")) 
                    .GET() 
                    .build();

         
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

         
            System.out.println("HTTP Status Code: " + response.statusCode());

          
            System.out.println("Response Body:");
            System.out.println(response.body());

        } catch (Exception e) {
            // If anything goes wrong, print the error
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
