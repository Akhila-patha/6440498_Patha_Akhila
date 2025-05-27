import java.util.concurrent.Executors;

public class VirtualThreadsDemo_40 {
    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < 100_000; i++) {
                    executor.submit(() -> {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("Elapsed time: " + (end - start) + "ms");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}