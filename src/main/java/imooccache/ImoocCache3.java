package imooccache;


import imooccache.computable.Computable;
import imooccache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * 用装饰者模式，给计算器自动添加缓存功能，演示synchronized影响性能的情况
 * @param <A>
 * @param <V>
 */
public class ImoocCache3<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new HashMap<>();

    private final Computable<A, V> computer;

    public ImoocCache3(Computable<A, V> computer) {
        this.computer = computer;
    }

    public synchronized V compute(A key) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(key);
        if (result == null) {
            result = computer.compute(key);
            cache.put(key, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        ImoocCache3<String, Integer> imoocCache3 = new ImoocCache3<>(new ExpensiveFunction());
        new Thread(() -> {
            try {
                Integer result = imoocCache3.compute("666");
                System.out.println("线程1的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache3.compute("666");
                System.out.println("线程3的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache3.compute("667");
                System.out.println("线程2的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
