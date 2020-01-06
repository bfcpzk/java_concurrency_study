package threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示停止线程池的方法
 */
public class ShutDown {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100 ; i++) {
            executorService.execute(new ShutDownTask());
        }
        /*System.out.println("isShutdown: " +executorService.isShutdown());
        executorService.shutdown();
        System.out.println("isShutdown: " +executorService.isShutdown());
        System.out.println("isTerminated: " +executorService.isTerminated());

        boolean b = executorService.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("awaitTerminated: " + b);

        boolean b1 = executorService.awaitTermination(7, TimeUnit.SECONDS);
        System.out.println("awaitTerminated: " + b1);

        Thread.sleep(1);
        System.out.println("isTerminated: " + executorService.isTerminated());*/

        Thread.sleep(3000);
        List<Runnable> undoneTask = executorService.shutdownNow();
        System.out.println(undoneTask.size());
    }
}

class ShutDownTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了");
        }
    }
}
