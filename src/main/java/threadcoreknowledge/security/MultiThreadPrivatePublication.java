package threadcoreknowledge.security;

import java.util.HashMap;
import java.util.Map;

/**
 * private 属性逸出，通常指引用类型，基本类型不存在这个问题。
 * 解决方案一：返回的是副本，其实就是FP中强调的immutable.
 */
public class MultiThreadPrivatePublication {
    private Map<String, String> states;

    public MultiThreadPrivatePublication() {
        states = new HashMap<>();
        states.put("1", "周一");
        states.put("2", "周二");
        states.put("3", "周三");
        states.put("4", "周一");
    }

    public Map<String, String> getStates() {
        return states;
    }

    public Map<String, String> getStatesImproved() {
        return new HashMap<>(states);
    }

    public static void main(String[] args) {
        MultiThreadPrivatePublication multiThreadPrivatePublication = new MultiThreadPrivatePublication();
        /* Map<String, String> states = multiThreadPrivatePublication.getStates();
        System.out.println(states.get("1"));
        states.remove("1");
        System.out.println(states.get("1"));*/
        System.out.println(multiThreadPrivatePublication.getStatesImproved().get("1"));
        multiThreadPrivatePublication.getStatesImproved().remove("1");
        System.out.println(multiThreadPrivatePublication.getStatesImproved().get("1"));
    }
}
