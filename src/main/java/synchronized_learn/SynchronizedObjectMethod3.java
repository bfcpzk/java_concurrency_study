package synchronized_learn;

/**
 * public synchronized void method() {...}
 */
public class SynchronizedObjectMethod3 implements Runnable {

    public static void main(String[] args) {
        SynchronizedObjectMethod3 instance = new SynchronizedObjectMethod3();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }

    public void run() {
        method();
    }

    private synchronized void method() {
        System.out.println("我是对象锁方法形式，我是" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }
}
