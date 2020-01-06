package threadcoreknowledge.stopthreads;

/**
 * 最佳实践2：在catch子语句中调用Thread.currentThread().interrupt()来恢复中断状态，以便于在后续执行中，
 * 依然能够检测到之前发生过中断，回到刚才RightWayStopThreadInProd补上中断，让它跳出。
 */
public class RightWayStopThreadInProd2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("中断退出。。。");
                break;
            }
            reInterrupt();
            System.out.println("保存日志");
        }
    }

    private void reInterrupt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();//检测中断后，恢复中断（sleep会更改中断标志位）
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd2());
        thread.start();
        thread.sleep(1000);
        thread.interrupt();
    }
}
