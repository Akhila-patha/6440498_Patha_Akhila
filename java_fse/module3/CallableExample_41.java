import java.util.concurrent.*;
import java.util.*;

public class CallableExample_41 {
    public static void main(String[] args) throws Exception {
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // Create a list to hold Future objects
        List<Future<String>> futures = new ArrayList<>();
        // Submit 5 callable tasks
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            Callable<String> task = () -> {
                Thread.sleep(1000); // Simulate work
                return "Task " + taskId + " completed by " + Thread.currentThread().getName();
            };
            futures.add(executor.submit(task));
        }
        // Retrieve and print the results
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        // Shutdown executor
        executor.shutdown();
    }
}
