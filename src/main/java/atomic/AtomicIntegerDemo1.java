package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示AtomicInteger基本用法，对比非原子类的线程安全问题，使用原子类之后，不需要加锁，也可以保证线程安全。
 */
public class AtomicIntegerDemo1 implements Runnable {

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public void atomicIncrement() {
        //atomicInteger.getAndIncrement();
        //atomicInteger.getAndDecrement();
        atomicInteger.getAndAdd(10);
    }

    private static int basicInteger = 0;

    public synchronized void basicIncrement() {
        basicInteger++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000 ; i++) {
            atomicIncrement();
            basicIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo1 atomicIntegerDemo1 = new AtomicIntegerDemo1();
        Thread t1 = new Thread(atomicIntegerDemo1);
        Thread t2 = new Thread(atomicIntegerDemo1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("原子类的结果：" + atomicInteger.get());
        System.out.println("非原子类的结果：" + basicInteger);
    }
}
