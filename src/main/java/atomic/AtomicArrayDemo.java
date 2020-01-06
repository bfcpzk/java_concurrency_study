package atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 演示Atomic* Array的用法
 */
public class AtomicArrayDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);
        Incrementer incrementer = new Incrementer(atomicIntegerArray);
        Decrementer decrementer = new Decrementer(atomicIntegerArray);
        Thread[] incArray = new Thread[100];
        Thread[] decArray = new Thread[100];
        for (int i = 0; i < 100 ; i++) {
            incArray[i] = new Thread(incrementer);
            decArray[i] = new Thread(decrementer);
            incArray[i].start();
            decArray[i].start();
        }

        for (int i = 0; i < 100 ; i++) {
            incArray[i].join();
            decArray[i].join();
        }

        for (int i = 0; i < atomicIntegerArray.length() ; i++) {
            if (atomicIntegerArray.get(i) != 0) {
                System.out.println("运行错误");
            }
        }
        System.out.println("运行结束");
    }
}


class Incrementer implements Runnable {
    private AtomicIntegerArray atomicIntegerArray;

    public Incrementer(AtomicIntegerArray atomicIntegerArray) {
        this.atomicIntegerArray = atomicIntegerArray;
    }

    @Override
    public void run() {
        for (int i = 0; i < atomicIntegerArray.length() ; i++) {
            atomicIntegerArray.getAndIncrement(i);
        }
    }
}

class Decrementer implements Runnable {
    private AtomicIntegerArray atomicIntegerArray;

    public Decrementer(AtomicIntegerArray atomicIntegerArray) {
        this.atomicIntegerArray = atomicIntegerArray;
    }

    @Override
    public void run() {
        for (int i = 0; i < atomicIntegerArray.length() ; i++) {
            atomicIntegerArray.getAndDecrement(i);
        }
    }
}