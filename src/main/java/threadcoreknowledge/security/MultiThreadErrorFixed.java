package threadcoreknowledge.security;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadErrorFixed implements Runnable {
    private int index = 0;
    private static final MultiThreadErrorFixed instance = new MultiThreadErrorFixed();
    private static AtomicInteger wrongCount = new AtomicInteger();
    private static AtomicInteger realIndex = new AtomicInteger();
    private static CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    private static CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);
    private static boolean[] marked = new boolean[20001];

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面上结果次数：" + instance.index);
        System.out.println("真正运行次数：" + realIndex.get());
        System.out.println("错误次数：" + wrongCount.get());
    }

    @Override
    public void run() {
        marked[0] = true;
        for (int i = 0 ; i < 10000 ; i++) {
            //错误三：确保index按轮次增加，等待两个线程都完毕，才开始递增，不会出现轮次错位。
            try {
                cyclicBarrier2.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            index++;
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.getAndIncrement();
            synchronized (instance) {
                //错误2：虽然实现了原子化标记，但是当线程1标记完后，线程2在进入同步代码块进行检测之前，线程1又对
                //index 更新，此时线程二拿到的结果就不是自己当时更新的结果了，或者线程1执行完还没又进行下一轮index的更新
                //线程2已经冲突，进行检测。
                if (marked[index] && marked[index - 1]) {//错误4：mutable的引用，必须连续两位都是true才是冲突，或者可以用本地变量进行判断进行。
                    //错误1：两个线程同时到达这一行，且index相同，实际上是冲突了，但是都不走if.
                    // 实际本意是要实现 if(marked[index]) 与 marked[index] = true原子化。
                    wrongCount.getAndIncrement();
                    System.out.println("发生错误：" + index);
                }
                marked[index] = true;
            }
        }
    }
}
