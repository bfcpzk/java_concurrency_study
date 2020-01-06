package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 利用synchronized，让两个线程进行奇偶数的交替打印，一个线程打印偶数，另一个线程打印奇数。
 * 问题在于会有很多无谓的竞争，偶数线程打印完释放锁，之后还有可能是偶数线程获取锁，但是什么也不做。
 * 直到奇数线程获取锁，进行有意义的打印
 */
public class WaitNotifyPrintOddEvenSyn {

    private static int count;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        if ((count & 1) == 0) {
                            System.out.println(Thread.currentThread().getName() + ":" + count++);
                        }
                    }
                }
            }
        }, "偶数").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        if ((count & 1) == 1) {
                            System.out.println(Thread.currentThread().getName() + ":" + count++);
                        }
                    }
                }
            }
        }, "奇数").start();
    }
}
