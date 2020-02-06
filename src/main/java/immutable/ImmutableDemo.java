package immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * 演示不可变对象，属性全部私有，同时，公有方法只读
 */
public class ImmutableDemo {
    private final Set<String> students = new HashSet<>();

    public ImmutableDemo() {
        students.add("王小美");
        students.add("王壮");
        students.add("徐福记");
    }

    public boolean isStudent(String s) {
        return students.contains(s);
    }
}
