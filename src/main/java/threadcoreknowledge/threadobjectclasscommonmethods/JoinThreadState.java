package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 查看线程装填当join的时候
 * 1. 先join, 再mainThread.getState()
 * 2. 通过debugger看线程join前后状态的对比
 */
public class JoinThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println(mainThread.getState());
                    System.out.println("Thread-0运行结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        System.out.println("等待子线程执行完毕");
        t1.join();
        System.out.println("子线程执行完毕");
    }
}
