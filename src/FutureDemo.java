import java.util.concurrent.*;

public class FutureDemo {

    private static long timeStampOfMainThreadStart;

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        System.out.println("Main thread started");

        timeStampOfMainThreadStart = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Future<String> future = executorService.submit(new Task("Task 1"));

        while (!future.isDone()) {
            System.out.println("The task has not done yet, I will waiting for one second more");
            Thread.sleep(1000);
        }
        System.out.println(future.get());

        // We can set the maximum waiting time in get()
        // But TimeoutException will be thrown if the task not finished
        // System.out.println(future.get(2, TimeUnit.SECONDS));

        System.out.println("Main thread finished : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));

        executorService.shutdown();
    }

    static class Task implements Callable<String> {
        private final String name;

        public Task(String taskName) {
            this.name = taskName;
        }

        @Override
        public String call() {
            System.out.println("Task " + name + " start : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task " + name + " finish : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));
            return "Return value of " + name;
        }
    }
}
