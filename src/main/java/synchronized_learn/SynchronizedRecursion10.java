package synchronized_learn;

/**
 * 可重入粒度测试：递归调用本方法
 */
public class SynchronizedRecursion10 {
    private int a = 0;

    private synchronized void method1() {
        System.out.println("这是method1，a=" + a);
        if (a == 0) {
            a += 1;
            method1();
        }
    }

    public static void main(String[] args) {
        SynchronizedRecursion10 instance = new SynchronizedRecursion10();
        instance.method1();
    }
}
