package synchronized_learn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized 和 lock 等价 加锁解锁代码
 */
public class SynchronizedToLock13 {

    private Lock lock = new ReentrantLock();

    public synchronized void method1() {
        System.out.println("我是synchronized形式的锁");
    }

    public void method2() {
        lock.lock();
        try {
            System.out.println("我是lock形式的锁");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        SynchronizedToLock13 instance = new SynchronizedToLock13();
        instance.method1();
        instance.method2();
    }
}
