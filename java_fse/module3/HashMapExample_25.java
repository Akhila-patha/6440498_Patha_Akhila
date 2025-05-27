import java.util.HashMap;
import java.util.Scanner;

public class HashMapExample_25 {
    public static void main(String[] args) {
        HashMap<Integer, String> studentMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Adding entries
        System.out.println("Enter number of students to add:");
        int n = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < n; i++) {
            System.out.print("Enter student ID (integer): ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            studentMap.put(id, name);
        }

        System.out.print("Enter a student ID to search: ");
        int searchId = scanner.nextInt();

        String studentName = studentMap.get(searchId);

        if (studentName != null) {
            System.out.println("Student Name: " + studentName);
        } else {
            System.out.println("Student ID not found.");
        }

        scanner.close();
    }
}
