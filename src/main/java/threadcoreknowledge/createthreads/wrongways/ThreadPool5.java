package threadcoreknowledge.createthreads.wrongways;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池创建线程的方法, 线程池的本质也是new Thread创建新的任务
 */
public class ThreadPool5 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0 ; i < 1000 ; i++) {
            executorService.submit(new Job() {
            });
        }
    }
}

class Job implements Runnable {
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}
