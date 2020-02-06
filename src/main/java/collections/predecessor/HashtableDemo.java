package collections.predecessor;

import java.util.Hashtable;

/**
 * 演示Hashtable的使用方法
 */
public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("key", "value");
        System.out.println(hashtable.get("key"));
    }
}
