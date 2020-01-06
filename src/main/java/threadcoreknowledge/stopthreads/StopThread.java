package threadcoreknowledge.stopthreads;

/**
 * 错误的停止方法：用stop()来停止线程，会导致线程运行一半突然停止，没办法完成一个单位的基本操作（一个连队），
 * 会造成脏数据（有的连队多领取少领取装备）。
 */
public class StopThread implements Runnable {

    @Override
    public void run() {
        //模拟指挥军队：一共5个连队，每个连队10人，以连队为单位，发放武器弹药，叫到号的士兵前去领取。
        for (int i = 0 ; i < 5 ; i++) {
            System.out.println("连队" + i + "开始领取武器");
            for (int j = 0 ; j < 10 ; j++) {
                System.out.println(j);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("连队" + i + "领取完毕");
        }
    }

    /**
     * 错误理论：stop不会释放所有的monitor，实际上会释放
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThread());
        thread.start();
        Thread.sleep(900);
        thread.stop();
    }
}

/**
 * 错误的停止方法：suspend会导致线程挂起，同时线程获得的锁不释放，容易造成死锁，如果唤醒该线程的线程需要对象锁的时候就会造成死锁。
 */
class SuspendResumeThread implements Runnable {
    Object lock = new Object();
    @Override
    public void run() {
        synchronized (lock) {
            int num = 0;
            while(num < Integer.MAX_VALUE / 2) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num++;
            }
        }
    }

    private void resume(Thread thread) {
        synchronized (lock) {
            thread.resume();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SuspendResumeThread runnable = new SuspendResumeThread();
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(2000);
        thread.suspend();
        System.out.println("线程挂起");
        Thread.sleep(5000);
        runnable.resume(thread);
        System.out.println("线程继续执行");
    }
}