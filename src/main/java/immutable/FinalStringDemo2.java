package immutable;

/**
 * final 修饰的变量接收函数返回值，编译器不能做优化
 */
public class FinalStringDemo2 {
    public static void main(String[] args) {
        String a = "wukong2";
        final String b = getDashixiong();
        String d = "wukong";
        String c = b + 2;
        String e = d + 2;
        System.out.println(a == c);
        System.out.println(a == e);
    }

    private static String getDashixiong() {
        return "wukong";
    }
}
