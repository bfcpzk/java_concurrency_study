package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * ID从1开始，JVM运行起来后，自己创建的线程早已经不是从1开始，从10开始。
 */
public class Id {

    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println("主线程的ID : " + Thread.currentThread().getId());
        System.out.println("子线程的ID : " + thread.getId());
    }
}
