package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 展示notify和wait的基本用法
 * 1. 研究代码执行顺序
 * 2. 证明wait释放锁
 */
public class Wait {
    private static Object object = new Object();

    static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程1开始执行了");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁。");
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                object.notify();
                System.out.println("线程" + Thread.currentThread().getName() + "调用了notify().");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread1();
        Thread t2 = new Thread2();
        t1.start();
        Thread.sleep(200);
        t2.start();
    }
}
