package threadcoreknowledge.security;

import java.util.HashMap;
import java.util.Map;

/**
 * 在构造函数中开新线程实现初始化，存在安全隐患，时间不同，结果不同。
 */
public class MultiThreadNewThreadInConstructor {

    private Map<String, String> states;

    public MultiThreadNewThreadInConstructor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                states = new HashMap<>();
                states.put("1", "周一");
                states.put("2", "周二");
                states.put("3", "周三");
                states.put("4", "周一");
            }
        }).start();
    }

    private Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThreadNewThreadInConstructor multiThreadNewThreadInConstructor = new MultiThreadNewThreadInConstructor();
        //Thread.sleep(1000);
        System.out.println(multiThreadNewThreadInConstructor.getStates().get("1"));
    }

}
