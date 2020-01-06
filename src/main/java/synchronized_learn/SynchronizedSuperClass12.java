package synchronized_learn;

/**
 * 子类方法可重入父类方法
 */
public class SynchronizedSuperClass12 {
    public synchronized void doSomething() {
        System.out.println("我是父类方法");
    }
}

class TestClass extends SynchronizedSuperClass12 {
    public synchronized void doSomething() {
        System.out.println("我是子类方法");
        super.doSomething();
    }

    public static void main(String[] args) {
        (new TestClass()).doSomething();
    }
}
