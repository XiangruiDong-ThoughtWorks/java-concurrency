import java.time.LocalTime;

public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread start");
        Thread thread = new TimeReporter();
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(10000);
        System.out.println("Main thread end");
    }
}

class TimeReporter extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println(LocalTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
