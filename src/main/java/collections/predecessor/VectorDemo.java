package collections.predecessor;

import java.util.Vector;

/**
 * 演示Vector的使用，方法大多都是synchronized，保证了线程安全，但是效率不高
 */
public class VectorDemo {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("test");
        System.out.println(vector.get(0));
    }
}
