package synchronized_learn;

/**
 * 两个线程同时访问静态synchronized方法和非静态synchronized方法
 * ***重要***
 * 两个方法并行执行，因为前者锁的是类对象(.class) 后者锁的是实例对象(this)
 */
public class SynchronizedStaticAndNormal8 implements Runnable {

    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            method1();
        } else {
            method2();
        }
    }

    public synchronized static void method1() {
        System.out.println("我是静态加锁的方法1，我是" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public synchronized void method2() {
        System.out.println("我是非静态加锁的方法2，我是" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public static void main(String[] args) {
        SynchronizedStaticAndNormal8 instance = new SynchronizedStaticAndNormal8();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }
}
