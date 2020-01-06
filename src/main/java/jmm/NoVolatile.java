package jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示volatile不适用于a++的场合
 */
public class NoVolatile implements Runnable {

    volatile int a = 0;
    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        NoVolatile r = new NoVolatile();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(r.a);
        System.out.println(r.realA.getAndIncrement());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000 ; i++) {
            a++;
            realA.incrementAndGet();
        }
    }
}
