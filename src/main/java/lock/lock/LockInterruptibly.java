package lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示lockInterruptibly方法的使用
 */
public class LockInterruptibly implements Runnable {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Runnable r1 = new LockInterruptibly();
        Runnable r2 = new LockInterruptibly();
        Thread t0 = new Thread(r1);
        Thread t1 = new Thread(r2);
        t0.start();
        t1.start();

        Thread.sleep(2000);

        t0.interrupt();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "获取到了锁");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "在睡眠期间被中断");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "在等锁期间被中断");
        }
    }
}
