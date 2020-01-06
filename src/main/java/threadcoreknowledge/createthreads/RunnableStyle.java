package threadcoreknowledge.createthreads;

/**
 * 用Runnable 方法创建线程
 */
public class RunnableStyle implements Runnable {

    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableStyle());
        thread.start();
    }

    public void run() {
        System.out.println("用Runnable方法实现线程");
    }
}
