package synchronized_learn;

/**
 * 访问同一个对象的不同的普通同步方法
 * ***重要***
 * 普通方法上的synchronized关键字本质上是对当前对象加锁，所以即便是两个不同方法，但是锁只有一把，因此串行执行.
 */
public class SynchronizedDifferentMethod7 implements Runnable {

    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            method1();
        } else {
            method2();
        }
    }

    public synchronized void method1() {
        System.out.println("我是加锁的方法1，我是" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public synchronized void method2() {
        System.out.println("我是加锁的方法2，我是" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public static void main(String[] args) {
        SynchronizedDifferentMethod7 instance = new SynchronizedDifferentMethod7();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }
}