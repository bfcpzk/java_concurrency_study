package immutable;

/**
 * 不可变对象，演示其他类无法修改这个对象，即使改成public也不行
 */
public class Person {
    private final int age = 18;
    private final String name = "Alice";
}
