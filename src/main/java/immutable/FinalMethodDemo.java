package immutable;

/**
 * 演示final修饰方法（1）不能修饰构造方法（2）不允许子类重写 （3）static 方法不存在重写，不需要final关键字
 */
public class FinalMethodDemo {
    /*int a;
    public FinalMethodDemo(int a) {
        this.a = a;
    }*/

    public void drink() {

    }

    public final void eat() {

    }

    public static void sleep() {

    }
}

class SubClass extends FinalMethodDemo {
    @Override
    public void drink() {
        super.drink();
    }

    /**
     * 不是重写，因为static方法不是动态加载的，与类绑定，两个sleep分别属于两个类，与重写无关
     * 因此，static本身就保证了方法不存在重写的情况，因而不需要对static方法加final修饰
     */
    public static void sleep() {

    }
}
