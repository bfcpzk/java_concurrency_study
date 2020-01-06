package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  利用ThreadLocal给每个线程分配自己的SimpleDateFormat对象，线程池有10个线程就是10个ThreadLocal，
 *  也就是10个SimpleDateFormat，保证了线程安全，高效利用了内存
 */
public class ThreadLocalNormalUsage05 {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0 ; i < 1000 ; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                public void run() {
                    String date = new ThreadLocalNormalUsage05().date(finalI);
                    //System.out.println(date);
                }
            });
        }
        threadPool.shutdown();
    }

    private String date(int seconds) {
        //单位是毫秒
        Date date = new Date(seconds * 1000);
        SimpleDateFormat sdf = ThreadSafeDateFormatter.dateFormatThreadLocal.get();
       /* System.out.println(ThreadSafeDateFormatter.dateFormatThreadLocal);
        System.out.println(ThreadSafeDateFormatter.dateFormatThreadLocal.get());*/
        //System.out.println(Thread.currentThread().getName() + ThreadSafeDateFormatter.dateFormatThreadLocal);
        //System.out.println(Thread.currentThread().getName() + sdf.toString());
        System.out.println(Thread.currentThread().getName() + "threadLocal : " + System.identityHashCode(ThreadSafeDateFormatter.dateFormatThreadLocal));
        System.out.println(Thread.currentThread().getName() + "sdf : " + System.identityHashCode(sdf));
        return sdf.format(date);
    }
}

class ThreadSafeDateFormatter {
    static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>(){
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2 = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
}