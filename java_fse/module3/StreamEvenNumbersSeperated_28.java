import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamEvenNumbersSeparated_28 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 15, 22, 33, 40, 55, 60);

               Stream<Integer> evenStream = numbers.stream()
                                            .filter(n -> n % 2 == 0);

      
        List<Integer> evenNumbers = evenStream.collect(Collectors.toList());

        System.out.println("Even numbers: " + evenNumbers);
    }
}
