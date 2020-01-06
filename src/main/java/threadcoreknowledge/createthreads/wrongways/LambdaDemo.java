package threadcoreknowledge.createthreads.wrongways;

/**
 * Lambda 表达式实现线程创建
 */
public class LambdaDemo {

    public static void main(String[] args) {
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
    }
}
