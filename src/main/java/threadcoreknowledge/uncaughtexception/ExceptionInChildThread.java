package threadcoreknowledge.uncaughtexception;

/**
 * 单线程抛出，处理，有异常堆栈
 * 多线程，子线程发生异常，异常也会抛出，但主线程不受影响，所以不太明显。
 */
public class ExceptionInChildThread implements Runnable {

    public static void main(String[] args) {
        new Thread(new ExceptionInChildThread()).start();
        for (int i = 0 ; i < 1000 ; i++) {
            System.out.println(i);
        }
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
