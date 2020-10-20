import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter1 counter1 = new Counter1();
        CounterIncreaseThread counterIncreaseThread = new CounterIncreaseThread(counter1);
        CounterDecreaseThread counterDecreaseThread = new CounterDecreaseThread(counter1);
        counterIncreaseThread.start();
        counterDecreaseThread.start();
        counterIncreaseThread.join();
        counterDecreaseThread.join();
        System.out.println("Counter : " + counter1.getCount());
    }
}

class Counter1 {
    private int count = 0;
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    /*private final Object objectLock1 = new Object();
    private final Object objectLock2 = new Object();*/

    public void increase() throws InterruptedException {
        // Following code will result in dead lock, but we can resolve it by using tryLock
        /*lock1.lock();
        lock2.lock();
        System.out.println("Increase: " + this.getCount());
        this.setCount(this.getCount() + 1);
        lock2.unlock();
        lock1.unlock();*/

        if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
            if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                System.out.println("Increase: " + this.getCount());
                this.setCount(this.getCount() + 1);
                lock2.unlock();
            }
            lock1.unlock();
        }
    }

    public void decrease() throws InterruptedException {
        /*lock2.lock();
        lock1.lock();
        System.out.println("Decrease: " + this.getCount());
        this.setCount(this.getCount() - 1);
        lock1.unlock();
        lock2.unlock();*/

        if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
            if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
                System.out.println("Decrease: " + this.getCount());
                this.setCount(this.getCount() - 1);
                lock1.unlock();
            }
            lock2.unlock();
        }
    }

    /*public void increase() {
        synchronized (objectLock1) {
            synchronized (objectLock2) {
                System.out.println("Increase: " + this.getCount());
                this.setCount(this.getCount() + 1);
            }
        }
    }

    public void decrease() {
        synchronized (objectLock2) {
            synchronized (objectLock1) {
                System.out.println("Decrease: " + this.getCount());
                this.setCount(this.getCount() - 1);
            }
        }
    }*/

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class CounterIncreaseThread extends Thread {
    private final Counter1 counter1;

    public CounterIncreaseThread(Counter1 counter1) {
        this.counter1 = counter1;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                counter1.increase();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CounterDecreaseThread extends Thread {
    private final Counter1 counter1;

    public CounterDecreaseThread(Counter1 counter1) {
        this.counter1 = counter1;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                counter1.decrease();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
