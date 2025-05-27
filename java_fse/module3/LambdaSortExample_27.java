import java.util.*;

public class LambdaSortExample_27 {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>(Arrays.asList("Banana", "Apple", "Mango", "Cherry", "Date"));

        // Sort using lambda expression (alphabetical order)
        Collections.sort(fruits, (s1, s2) -> s1.compareTo(s2));

        // Display sorted list
        System.out.println("Sorted fruits: " + fruits);
    }
}
