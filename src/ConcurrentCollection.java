import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentCollection {

    public static void main(String[] args) throws InterruptedException {
        // List<Integer> integerList = new LinkedList<>();
        List<Integer> integerList = new CopyOnWriteArrayList<>();
        Thread[] threads = new Thread[100];
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    integerList.add(random.nextInt());
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Amount: " + integerList.size());

        // Only thread-safe at specified level
        /*integerList.add(0, 0);
        Thread incThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                integerList.set(0, integerList.get(0) + 1);
            }
        });
        Thread decThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                integerList.set(0, integerList.get(0) - 1);
            }
        });
        incThread.start();
        decThread.start();
        incThread.join();
        decThread.join();
        System.out.println("Count: " + integerList.get(0));*/
    }
}
