package threadcoreknowledge.security;

/**
 * 演示线程安全问题问题：死锁的例子
 */
public class MultiThreadDeadLock implements Runnable {

    private int flag;
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public MultiThreadDeadLock(int flag) {
        this.flag = flag;
    }

    public static void main(String[] args) {
        MultiThreadDeadLock multiThreadDeadLock1 = new MultiThreadDeadLock(1);
        MultiThreadDeadLock multiThreadDeadLock2 = new MultiThreadDeadLock(0);
        new Thread(multiThreadDeadLock1).start();
        new Thread(multiThreadDeadLock2).start();
    }

    @Override
    public void run() {
        if (flag == 1) {
            System.out.println("我是线程" + Thread.currentThread().getName());
            synchronized (lock1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("flag = " + flag);
                }
            }
        } else if (flag == 0) {
            System.out.println("我是线程" + Thread.currentThread().getName());
            synchronized (lock2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("flag = " + flag);
                }
            }
        }
    }
}
