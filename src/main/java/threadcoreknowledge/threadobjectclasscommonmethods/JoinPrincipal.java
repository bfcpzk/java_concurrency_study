package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * join 的等价替代
 */
public class JoinPrincipal {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕！");
        });
        t1.start();
        System.out.println("开始等待子线程执行完毕");
        //t1.join();
        synchronized (t1) {
            t1.wait();
        }
        System.out.println("子线程执行完毕");
    }
}
