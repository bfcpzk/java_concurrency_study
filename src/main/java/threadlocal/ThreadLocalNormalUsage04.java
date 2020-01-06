package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  用线程池打印1000个日期，只创建一个SimpleDateFormat对象，对日期格式化的位置加锁，消除线程安全问题
 */
public class ThreadLocalNormalUsage04 {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0 ; i < 1000 ; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalNormalUsage04().date(finalI);
                    System.out.println(date);
                }
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        //单位是毫秒
        Date date = new Date(seconds * 1000);
        String s = null;
        synchronized (ThreadLocalNormalUsage04.class) {
            s = sdf.format(date);
        }
        return s;
    }
}
