package singleton;

/**
 * 懒汉式 (线程不安全，同步代码块) 【不可用】
 */
public class Singleton5 {
    private static Singleton5 instance;

    private Singleton5() { }

    public static Singleton5 getInstance() {
        if (instance == null) {//还是会有多个线程进入到if内部，还是会创建出多个对象，只不过都在等锁，线程不安全。
            synchronized (Singleton5.class) {
                instance = new Singleton5();
            }
        }
        return instance;
    }
}
