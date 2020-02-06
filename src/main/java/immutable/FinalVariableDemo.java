package immutable;

/**
 * 演示final属性（3种）和类属性（2种）和local 变量（1种）赋值
 */
public class FinalVariableDemo {
    private final int a = 9;
    /*{
        a = 9;
    }*/

    /*public FinalVariableDemo(int a) {
        this.a = a;
    }*/

    private static final int b = 10;
    /*static {
        b = 10;
    }*/

    public void test() {
        final int c = 10;

        //c = 10;

        //使用前必须赋值
        System.out.println(c);
    }
}