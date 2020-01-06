package singleton;

/**
 * 单例模式：饿汉式（静态常量）
 */
public class Singleton1 {
    private static final Singleton1 instance = new Singleton1();

    private Singleton1() { }

    public Singleton1 getInstance() {
        return instance;
    }
}
