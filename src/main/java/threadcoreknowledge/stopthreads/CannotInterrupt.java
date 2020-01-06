package threadcoreknowledge.stopthreads;

/**
 * 不能正确终止线程的方式。（1）因为sleep在while内部，所以在interrupt的时候，while循环没有结束，循环会继续执行。
 * （2）即使在循环判断条件的地方添加!Thread.currentThread().isInterrupted()也不能中断，因为当前线程的中断标志位会被清除，判断失效
 */
public class CannotInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            while (num < 10000 && !Thread.currentThread().isInterrupted()) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    //Thread.currentThread().interrupt(); //中断恢复
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
