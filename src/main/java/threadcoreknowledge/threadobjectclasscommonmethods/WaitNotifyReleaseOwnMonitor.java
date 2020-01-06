package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 证明wait只释放当前的那把锁
 */
public class WaitNotifyReleaseOwnMonitor {
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    System.out.println("thread1 got resourceA lock");
                    synchronized (resourceB) {
                        System.out.println("thread1 got resourceB lock");
                        try {
                            System.out.println("thread1 releases resourceA lock");
                            resourceA.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resourceA) {
                    System.out.println("thread2 got resourceA lock");
                    System.out.println("thread2 tries to get resourceB lock");
                    synchronized (resourceB) {
                        System.out.println("thread2 got resourceB lock");
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }

}
