package lock.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁和非公平锁打印结果的不同
 */
public class FairLock {
    public static void main(String[] args) throws InterruptedException {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10 ; i++) {
            threads[i] = new Thread(new Job(printQueue));
        }
        for (int i = 0; i < 10 ; i++) {
            threads[i].start();
            Thread.sleep(100);
        }
    }
}

class Job implements Runnable {
    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始打印");
        printQueue.printJob(null);
        System.out.println(Thread.currentThread().getName() + "完成打印");

    }
}

class PrintQueue {
    private static Lock queueLock = new ReentrantLock(false);

    public void printJob(Object document) {
        queueLock.lock();
        try {
            int duration = (int) (Math.random() * 10 + 1);
            System.out.println(Thread.currentThread().getName() + "正在打印文件1，需要" + duration + "秒");
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }

        queueLock.lock();
        try {
            int duration = (int) (Math.random() * 10 + 1);
            System.out.println(Thread.currentThread().getName() + "正在打印文件2，需要" + duration + "秒");
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }
}
