package imooccache;


import imooccache.computable.Computable;
import imooccache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 利用ConcurrentHashMap来解决问题。
 * @param <A>
 * @param <V>
 */
public class ImoocCache5<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computer;

    public ImoocCache5(Computable<A, V> computer) {
        this.computer = computer;
    }

    public V compute(A key) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(key);
        if (result == null) {
            result = computer.compute(key);
            cache.put(key, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        ImoocCache5<String, Integer> imoocCache2 = new ImoocCache5<>(new ExpensiveFunction());
        Integer result = imoocCache2.compute("666");
        System.out.println("第一次计算结果：" + result);
        result = imoocCache2.compute("666");
        System.out.println("第二次计算结果：" + result);
    }
}
