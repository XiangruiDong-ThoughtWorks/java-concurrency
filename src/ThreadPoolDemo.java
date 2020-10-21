import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        /*ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 8; i++) {
            Task task = new Task(String.valueOf(i));
            fixedThreadPool.submit(task);
            System.out.println("Task " + i + " is added into thread pool");
        }*/

        /*ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 8; i++) {
            Task task = new Task(String.valueOf(i));
            cachedThreadPool.submit(task);
            System.out.println("Task " + i + " is added into thread pool");
        }*/

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 8; i++) {
            Task task = new Task(String.valueOf(i));
            executorService.submit(task);
            System.out.println("Task " + i + " is added into thread pool");
        }

        // The main thread will not stop without the invocation of shutdown()
        // If shutdown() is invoked, main thread will end after all tasks finished
        // executorService.shutdown();

        // Main thread will be shutdown immediately if shutdownNow() is invoked
        // In this case, InterruptedException will be thrown if any task has not finished
        Thread.sleep(60000);
        executorService.shutdownNow();
    }

    static class Task extends Thread {
        private final String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Task " + name + " started");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task " + name + " finished");
        }
    }
}

