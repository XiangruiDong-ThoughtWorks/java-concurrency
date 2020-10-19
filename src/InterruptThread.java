
public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MainThread();
        thread.start();
        Thread.sleep(5);
        thread.interrupt();  // This method cannot worked without isInterrupted()
    }
}

class MainThread extends Thread {
    @Override
    public void run() {
        /*int count = 0;
        while (! isInterrupted()) {
            System.out.println("count: " + count++);
        }*/
        System.out.println("MainThread start");
        Thread thread = new SubThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException in MainThread was thrown");
        }
        System.out.println("MainThread end");
    }
}

class SubThread extends Thread {
    @Override
    public void run() {
        System.out.println("SubThread start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("SubThread end");
    }
}

