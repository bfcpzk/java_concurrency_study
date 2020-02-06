package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示读写锁可以降级，不能升级的特性
 */
public class Upgrading {

    //公平策略
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void readUpGrading() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取读锁");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁,正在读取");
            Thread.sleep(20);
            System.out.println(Thread.currentThread().getName() + "尝试升级写锁");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "升级成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了写锁");
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了读锁");
            readLock.unlock();
        }
    }

    private static void writeDownGrading() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取写锁");
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁,正在写入");
            Thread.sleep(40);
            System.out.println(Thread.currentThread().getName() + "尝试降级读锁");
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + "降级成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了读锁");
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        //new Thread(() -> writeDownGrading(), "Thread1").start();

        new Thread(() -> readUpGrading(), "Thread2").start();
    }
}
