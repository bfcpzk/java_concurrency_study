package threadcoreknowledge.uncaughtexception;

/**
 * 1. 不加try-catch抛出4个异常，都带有线程名字
 * 2. 加了try-catch, 期望捕获到第一个线程的异常，线程2，3，4不应该运行，希望看到打印Caught Exception
 * 3. 执行时发现，根本没有Caught Exception，线程2，3，4依然运行并且抛出异常
 *
 * 原因：try-catch只能捕获本线程的异常
 * 说明线程的异常不能用传统方法捕获
 */
public class CantCatchDirectly implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        new Thread(new CantCatchDirectly(), "MyThread-1").start();
        Thread.sleep(300);
        new Thread(new CantCatchDirectly(), "MyThread-2").start();
        Thread.sleep(300);
        new Thread(new CantCatchDirectly(), "MyThread-3").start();
        Thread.sleep(300);
        new Thread(new CantCatchDirectly(), "MyThread-4").start();
    }

    @Override
    public void run() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("Caught Exception");
        }
    }
}
