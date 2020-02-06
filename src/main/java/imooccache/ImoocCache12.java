package imooccache;


import imooccache.computable.ExpensiveFunction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @@缓存测试
 * 1. 用线程池测试缓存性能
 * 2. 利用CountDownLatch模拟压测
 * 3. 利用ThreadLocal确保同一时间放行
 */
public class ImoocCache12 {
    private static final ImoocCache10<String, Integer> imoocCache10 = new ImoocCache10<>(new ExpensiveFunction());
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(100);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100 ; i++) {
            service.submit(() -> {
                Integer result = null;
                try {
                    System.out.println(Thread.currentThread().getName() + "开始等待");
                    latch.await();
                    SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatter.get();
                    String time = dateFormat.format(new Date());
                    System.out.println(Thread.currentThread().getName() + " " + time + " 被放行");
                    result = imoocCache10.compute("666");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(result);
            });
        }
        Thread.sleep(5000);
        latch.countDown();
        service.shutdown();

        /*service.shutdown();
        while (!service.isTerminated()) {

        }
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));*/
    }
}

class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("mm:ss");
        }

        @Override
        public SimpleDateFormat get() {
            return super.get();
        }
    };
}