package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示newFixedThreadPool
 *
 * new ThreadPoolExecutor(
 * corePoolSize: 0,
 * maxPoolSize: Integer.MAX_VALUE,
 * keepAliveTime: 60L,
 * keepAliveTime对应的时间单位: TimeUnit.SECONDS,
 * workQueue: new SynchronousQueue<Runnable>()  //有界队列
 * );
 * 但是线程数量没有限制，也有可能OOM
 */
public class CachedThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000 ; i++) {
            executorService.execute(new Task());
        }
        executorService.shutdown();
    }
}
