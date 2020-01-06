package threadlocal;

import java.lang.reflect.Field;

/**
 * long/Long 拆箱装箱中的问题。
 */
public class ThreadLocalNPE {

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    private void set() {
        threadLocal.set(Thread.currentThread().getId());
    }

    private Long get() {
        return threadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalNPE threadLocalNPE = new ThreadLocalNPE();
        System.out.println(threadLocalNPE.get());
        System.out.println(threadLocalNPE.threadLocal);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocalNPE.set();
                System.out.println(threadLocalNPE.get());
                System.out.println(threadLocalNPE.threadLocal);
            }
        });
        thread1.start();
    }
}
