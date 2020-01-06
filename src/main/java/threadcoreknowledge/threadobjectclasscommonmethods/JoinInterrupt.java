package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 演示join期间被中断的效果
 */
public class JoinInterrupt {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainThread.interrupt();
                    Thread.sleep(5000);
                    System.out.println("THREAD1 FINISHED.");
                } catch (InterruptedException e) {
                    System.out.println("THREAD1 子线程被中断...");
                }
            }
        });
        t1.start();
        System.out.println("等待子线程运行完毕.");
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "主线程被中断了...");
            t1.interrupt();
        }
        System.out.println("子线程已经运行完毕！");
    }
}
