package threadcoreknowledge.security;

/**
 * 运行结果错误
 * 演示计数不准确，找出具体出错位置。
 */
public class MultiThreadError implements Runnable {
    int index = 0;
    private static final MultiThreadError instance = new MultiThreadError();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(instance.index);
    }

    @Override
    public void run() {
        for (int i = 0 ; i < 10000 ; i++) {
            index++;
        }
    }
}
