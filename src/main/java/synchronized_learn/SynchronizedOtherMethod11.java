package synchronized_learn;

/**
 * 可重入粒度测试：调用类内另外的方法
 */
public class SynchronizedOtherMethod11 {
    public synchronized void method1() {
        System.out.println("我是method1");
        method2();
    }

    public synchronized void method2() {
        System.out.println("我是method2");
    }

    public static void main(String[] args) {
        SynchronizedOtherMethod11 instance = new SynchronizedOtherMethod11();
        instance.method1();
    }
}
