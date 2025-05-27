import java.util.InputMismatchException;
import java.util.Scanner;

// Custom exception class for invalid age
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Main class to demonstrate custom exception handling
public class CustomException_21 {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        
        try {
            // Prompt user for input
            System.out.print("Enter your age: ");
            int age = scn.nextInt();
            
            if (age < 18) {
                throw new InvalidAgeException("Age must be 18 or above.");
            }
            System.out.println("Access granted.");
        } catch (InvalidAgeException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Exception: Please enter a valid integer for age.");
        } finally {
            // Close the Scanner to prevent resource leaks
            scn.close();
        }
    }
}