public class CreateNewThread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread start");
        Thread thread = new Thread(new Thread2());
        thread.start();
        thread.join(200);  // Param: The maximum waited time

        // The second method of creating new thread can be replaced by following code
        /*Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2 start");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2 end");
        });
        thread2.start();
        thread2.join();*/
        System.out.println("Main thread end");
    }
}

// Extending the Thread class and override its run() method
class Thread1 extends Thread {
    @Override
    public void run() {
        System.out.println("Thread 1 start");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread 1 end");
    }
}

// Implementing the Runnable class and override its run() method
class Thread2 implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread 2 start");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread 2 end");
    }
}
