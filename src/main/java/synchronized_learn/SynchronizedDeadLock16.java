package synchronized_learn;

public class SynchronizedDeadLock16 implements Runnable {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public static void main(String[] args) {
        SynchronizedDeadLock16 instance = new SynchronizedDeadLock16();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }

    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            method1();
        } else {
            method2();
        }
    }

    private void sleep(long period) {
        try {
            Thread.sleep(period);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void method1() {
        synchronized (lock1) {
            System.out.println("我是线程0，我获得了lock1");
            sleep(1000);
            synchronized (lock2) {
                System.out.println("我是线程0，我获得了lock2");
                sleep(3000);
            }

            System.out.println(Thread.currentThread().getName() + "运行结束");
        }
    }

    private void method2() {
        synchronized (lock2) {
            System.out.println("我是线程1，我获得了lock2");
            sleep(1000);
            synchronized (lock1) {
                System.out.println("我是线程1，我获得了lock1");
                sleep(3000);
            }
            System.out.println(Thread.currentThread().getName() + "运行结束");
        }
    }
}
