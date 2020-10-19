public class ThreadSynchronization {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread incrementThread = new IncrementThread(counter);
        Thread decrementThread = new DecrementThread(counter);
        incrementThread.start();
        decrementThread.start();
        incrementThread.join();
        decrementThread.join();
        System.out.println("Counter: " + counter.getCount());
    }
}

class Counter {
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}

class IncrementThread extends Thread {
    public final Counter counter;

    public IncrementThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (counter) {
                counter.setCount(counter.getCount() + 1);
            }
        }
    }
}

class DecrementThread extends Thread {
    public final Counter counter;

    public DecrementThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (counter) {
                counter.setCount(counter.getCount() - 1);
            }
        }
    }
}
