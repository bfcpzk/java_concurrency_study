package synchronized_learn;

/**
 * synchronized 对象锁
 * 两种用法：
 * (1)synchronized (this) {...}
 * (2)synchronized (lock1) {...}
 */
public class SynchronizedObjectCodeBlock2 implements Runnable {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public static void main(String[] args) {
        SynchronizedObjectCodeBlock2 instance = new SynchronizedObjectCodeBlock2();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }

    public void run() {
        synchronizedObjectThis();
    }

    private void synchronizedObjectThis() {
        synchronized (this) {
            System.out.println("我是对象锁代码块形式，我是" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "运行结束");
        }
    }

    private void synchronizedObjectLockObject() {
        synchronized (lock1) {
            System.out.println("我是lock1，我是" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "lock1部分运行结束");
        }

        synchronized (lock2) {
            System.out.println("我是lock2，我是" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "lock2部分运行结束");
        }
    }
}
