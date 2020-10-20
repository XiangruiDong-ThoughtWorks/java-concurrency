import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter2 counter2 = new Counter2();
        Thread incThread = new IncThread(counter2);
        Thread decThread = new DecThread(counter2);
        Thread readThread = new ReadThread(counter2);
        long startTime = System.currentTimeMillis();
        incThread.start();
        decThread.start();
        readThread.start();
        incThread.join();
        decThread.join();
        readThread.join();
        long endTime = System.currentTimeMillis();
        System.out.println("Counter: " + counter2.getCount());
        System.out.println("Time: " + (endTime - startTime));
    }
}

class Counter2 {
    private int count;
    private final Lock lock = new ReentrantLock();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public void inc() {
        lock.lock();
        this.setCount(this.getCount() + 1);
        lock.unlock();

        /*writeLock.lock();
        this.setCount(this.getCount() + 1);
        writeLock.unlock();*/
    }

    public void dec() {
        lock.lock();
        this.setCount(this.getCount() - 1);
        lock.unlock();

        /*writeLock.lock();
        this.setCount(this.getCount() - 1);
        writeLock.unlock();*/
    }

    public int getCount() {
        lock.lock();
        int returnValue = count;
        lock.unlock();
        return returnValue;

        /*readLock.lock();
        int returnValue = count;
        readLock.unlock();
        return returnValue;*/
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class IncThread extends Thread {
    private final Counter2 counter2;

    public IncThread(Counter2 counter2) {
        this.counter2 = counter2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter2.inc();
        }
    }
}

class DecThread extends Thread {
    private final Counter2 counter2;

    public DecThread(Counter2 counter2) {
        this.counter2 = counter2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter2.dec();
        }
    }
}

class ReadThread extends Thread {
    private final Counter2 counter2;

    public ReadThread(Counter2 counter2) {
        this.counter2 = counter2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 400000000; i++) {
            counter2.getCount();
        }
    }
}
