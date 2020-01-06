package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示newSingleThreadExecutor
 *
 * new ThreadPoolExecutor(
 * corePoolSize: 1,
 * maxPoolSize: 1,
 * keepAliveTime: 0L,  //没有线程需要被回收，所以此处该参数没有意义
 * keepAliveTime对应的时间单位: TimeUnit.MILLISECONDS,
 * workQueue: new LinkedBlockingQueue<Runnable>()  //无界队列 -> OOM
 * );
 */
public class SingleThreadExecutor {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 1000 ; i++) {
            executorService.execute(new Task());
        }
        executorService.shutdown();
    }
}
