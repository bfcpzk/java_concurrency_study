package jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示volatile适用场景1
 */
public class UseVolatile implements Runnable {

    volatile boolean flag = false;
    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        UseVolatile r = new UseVolatile();
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
            setDone();
            realA.incrementAndGet();
        }
    }

    private void setDone() {
        flag = true;
    }
}

