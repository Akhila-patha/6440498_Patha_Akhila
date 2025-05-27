class ThreadCreation_26 extends Thread {
    private String message;

    public MyThread(String message) {
        this.message = message;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(message + " - Count: " + (i + 1));
            try {
                Thread.sleep(500); // Sleep for 0.5 seconds to see interleaving clearly
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("Thread 1 running");
        MyThread thread2 = new MyThread("Thread 2 running");

        thread1.start();
        thread2.start();
    }
}
