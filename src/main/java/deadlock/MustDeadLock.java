package deadlock;

/**
 * 普通情况：必然发生死锁
 */
public class MustDeadLock implements Runnable {

    private boolean flag;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        MustDeadLock r1 = new MustDeadLock();
        MustDeadLock r2 = new MustDeadLock();
        r1.flag = true;
        r2.flag = false;
        new Thread(r1).start();
        new Thread(r2).start();
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (lock1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("线程1获得了两把锁");
                }
            }
        } else {
            synchronized (lock2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("线程2获得了两把锁");
                }
            }
        }
    }
}
