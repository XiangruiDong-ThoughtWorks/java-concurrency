import java.util.concurrent.atomic.AtomicInteger;

public class AtomicClassDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicCounter atomicCounter = new AtomicCounter();
        Thread incThread = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                atomicCounter.incCounter();
            }
        });
        Thread decThread = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                atomicCounter.decCounter();
            }
        });
        incThread.start();
        decThread.start();
        incThread.join();
        decThread.join();
        System.out.println("Counter: " + atomicCounter.getAtomicInteger());
    }
}

class AtomicCounter {
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void incCounter() {
        atomicInteger.addAndGet(1);
    }

    public void decCounter() {
        atomicInteger.addAndGet(-1);
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }
}
