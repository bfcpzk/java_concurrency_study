package singleton;

/**
 * 静态内部类方法，可用
 * 保证了懒加载（加载Singleton7的时候instance不会被实例化，只有在调用时才会），同时线程安全（JVM保证）
 */
public class Singleton7 {

    private Singleton7() { }

    private static class SingletonInstance {
        private static final Singleton7 instance = new Singleton7();
    }

    public static Singleton7 getInstance() {
        return SingletonInstance.instance;
    }
}
