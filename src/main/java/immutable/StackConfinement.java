package immutable;

/**
 * 演示栈封闭技术
 */
public class StackConfinement implements Runnable {
    private int index = 0;

    public void inThread() {
        int neverGoOut = 0;
        for (int i = 0; i < 10000 ; i++) {
            neverGoOut++;
        }
        System.out.println(Thread.currentThread().getName() + "栈内保护的数字是线程安全的 ： " + neverGoOut);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000 ; i++) {
            index++;
        }
        inThread();
    }

    public static void main(String[] args) throws InterruptedException {
        StackConfinement r1 = new StackConfinement();
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("总和：" + r1.index);
    }
}
