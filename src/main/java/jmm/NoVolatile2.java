package jmm;

import java.util.concurrent.atomic.AtomicInteger;

public class NoVolatile2 implements Runnable {

    volatile boolean flag = false;
    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        NoVolatile2 r = new NoVolatile2();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(r.flag);
        System.out.println(r.realA.getAndIncrement());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000 ; i++) {
            flipDone();
            realA.incrementAndGet();
        }
    }

    private void flipDone() {
        flag = !flag;
    }
}
