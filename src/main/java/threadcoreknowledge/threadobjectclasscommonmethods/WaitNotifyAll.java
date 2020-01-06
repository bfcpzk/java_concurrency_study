package threadcoreknowledge.threadobjectclasscommonmethods;

public class WaitNotifyAll implements Runnable {
    private static final Object resourceA = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable r = new WaitNotifyAll();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    resourceA.notifyAll();
                    System.out.println("Thread3 notifyAll");
                }
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(200);
        t3.start();
    }

    @Override
    public void run() {
        synchronized (resourceA) {
            System.out.println("线程" + Thread.currentThread().getName() + "获得A锁");
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "waits to start");
                resourceA.wait();
                System.out.println("线程" + Thread.currentThread().getName() + "waits to end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
