package threadcoreknowledge.sixstates;

/**
 * 展示线程的New Runnable Terminated
 * 即使是正在运行的线程也是Runnable而不是Running
 */
public class NewRunnableTerminated implements Runnable {

    @Override
    public void run() {
        for (int i = 0 ; i < 1000 ; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new NewRunnableTerminated());
        //打印出New状态
        System.out.println("打印出New状态: " + thread.getState());
        thread.start();
        //打印出Runnable状态
        System.out.println("打印出Runnable状态: " + thread.getState());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印出Runnable状态，即使是正在运行也是Runnable, 而不是Running。
        System.out.println("打印出Runnable状态: " + thread.getState());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印出Terminated状态
        System.out.println("打印出Terminated状态: " + thread.getState());
    }
}
