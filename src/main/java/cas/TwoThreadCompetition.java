package cas;

/**
 * 模拟CAS等价代码
 */
public class TwoThreadCompetition implements Runnable {
    private volatile int value;
    public synchronized int compareAndSet(int expectValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectValue) {
            value = newValue;
        }
        return oldValue;
    }

    public static void main(String[] args) throws InterruptedException {
        TwoThreadCompetition r = new TwoThreadCompetition();
        r.value = 0;
        Thread t1 = new Thread(r, "Thread 1");
        Thread t2 = new Thread(r, "Thread 2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(r.value);
    }

    @Override
    public void run() {
        compareAndSet(0, 1);
    }
}
