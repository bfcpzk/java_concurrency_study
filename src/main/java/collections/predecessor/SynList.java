package collections.predecessor;

import java.util.*;

/**
 * 演示Collections.synchronizedList(new ArrayList<>())的用法
 */
public class SynList {
    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        //Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        list.add("asd");
        System.out.println(list.get(0));
    }
}
