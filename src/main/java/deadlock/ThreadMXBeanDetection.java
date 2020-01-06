package deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadMXBeanDetection implements Runnable {

    private boolean flag;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        ThreadMXBeanDetection r1 = new ThreadMXBeanDetection();
        ThreadMXBeanDetection r2 = new ThreadMXBeanDetection();
        r1.flag = true;
        r2.flag = false;
        new Thread(r1).start();
        new Thread(r2).start();

        Thread.sleep(1000);

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadLockThreads = threadMXBean.findDeadlockedThreads();
        if (deadLockThreads != null && deadLockThreads.length > 0) {
            for (int i = 0; i < deadLockThreads.length; i++) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadLockThreads[i]);
                System.out.println("发现死锁：" + threadInfo.getThreadName());
            }
        }
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (lock1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("线程1获得了两把锁");
                }
            }
        } else {
            synchronized (lock2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("线程2获得了两把锁");
                }
            }
        }
    }
}
