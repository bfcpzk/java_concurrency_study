package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FixedThreadPool 的 OOM 异常演示
 */
public class FixedThreadPoolOOM {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < Integer.MAX_VALUE ; i++) {
            executorService.execute(new LongTimeTask());
        }
        executorService.shutdown();
    }
}

class LongTimeTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}