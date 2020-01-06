package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 利用wait/notify，让两个线程进行奇偶数的交替打印，一个线程打印偶数，另一个线程打印奇数。
 * 解决无谓竞争锁的问题。
 */
public class WaitNotifyPrintOddEvenWait {
    private static int count = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        TurningRunner tr = new TurningRunner();
        new Thread(tr, "偶数").start();
        Thread.sleep(100);
        new Thread(tr, "奇数").start();
    }

    //1. 拿到锁我们就打印
    //2. 打印完唤醒其他线程，自己就休眠
    static class TurningRunner implements Runnable {
        @Override
        public void run() {
            while (count <= 100) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ":" + count++);
                    lock.notify();
                    //如果任务还没结束，就释放锁，自己休眠
                    if (count <= 100) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
