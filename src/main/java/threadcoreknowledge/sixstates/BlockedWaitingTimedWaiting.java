package threadcoreknowledge.sixstates;

/**
 * 展示Blocked, Waiting, TimedWaiting
 */
public class BlockedWaitingTimedWaiting implements Runnable {

    @Override
    public void run() {
        syn();
    }

    private synchronized void syn() {
        try {
            Thread.sleep(1000);
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new BlockedWaitingTimedWaiting();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        Thread.sleep(10);
        thread2.start();
        //thread1是TIMED_WAITING
        System.out.println(thread1.getState());
        //thread2是BLOCKED
        System.out.println(thread2.getState());
        Thread.sleep(1300);
        //thread1是WAITING
        System.out.println(thread1.getState());
    }
}
