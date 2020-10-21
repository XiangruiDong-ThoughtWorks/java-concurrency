import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolDemo {

    private static long timeStampOfMainThreadStart;

    public static void main(String[] args) {
        timeStampOfMainThreadStart = System.currentTimeMillis();
        System.out.println("Main thread start : 0");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

        System.out.println("Task 1 scheduled : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));
        scheduledExecutorService.schedule(new Task("Task 1"), 1, TimeUnit.SECONDS);

        System.out.println("Task 2 scheduled : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));
        // The third param specify the period between the task started at the last time
        // If the period shorter than the time that task actually need in running, the task will start immediately once it finished
        scheduledExecutorService.scheduleAtFixedRate(new Task("Task 2"), 2, 3, TimeUnit.SECONDS);

        System.out.println("Task 3 scheduled : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));
        scheduledExecutorService.scheduleAtFixedRate(new Task("Task 3"), 2, 8, TimeUnit.SECONDS);

        System.out.println("Task 4 scheduled : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));
        scheduledExecutorService.scheduleWithFixedDelay(new Task("Task 4"), 5, 3, TimeUnit.SECONDS);
    }

    static class Task extends Thread {
        private final String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Task " + name + " start : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task " + name + " finish : " + (System.currentTimeMillis() - timeStampOfMainThreadStart));
        }
    }
}
