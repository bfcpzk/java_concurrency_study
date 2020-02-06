package lock.reentrantlock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class RecursionDemo {
    private static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {

        new RecursionDemo().accessResource();
    }

    private void accessResource() {
        lock.lock();
        try {
            System.out.println("已对资源进行了处理");
            if (lock.getHoldCount() < 5) {
                System.out.println(lock.getHoldCount());
                accessResource();
                System.out.println(lock.getHoldCount());
            }
        } finally {
            lock.unlock();
        }
    }
}
