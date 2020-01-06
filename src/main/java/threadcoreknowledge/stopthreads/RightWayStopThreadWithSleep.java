package threadcoreknowledge.stopthreads;

/**
 * 带有sleep的中断线程的方法
 */
public class RightWayStopThreadWithSleep{

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            try {
                int num = 0;
                while (!Thread.currentThread().isInterrupted() && num <= Integer.MAX_VALUE / 2) {
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(300);
        thread.interrupt();
    }
}
