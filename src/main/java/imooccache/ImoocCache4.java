package imooccache;


import imooccache.computable.Computable;
import imooccache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * 缩小了synchronized的粒度，提高了性能，但是依然并发不安全，利用ConcurrentHashMap解决性能和线程安全问题。
 * @param <A>
 * @param <V>
 */
public class ImoocCache4<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new HashMap<>();

    private final Computable<A, V> computer;

    public ImoocCache4(Computable<A, V> computer) {
        this.computer = computer;
    }

    public V compute(A key) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(key);
        if (result == null) {
            result = computer.compute(key);
            synchronized (this) { //保护写的时候，读还是可以进行的，线程还是不安全的，直接用ConcurrentHashMap比较好
                cache.put(key, result);
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        ImoocCache4<String, Integer> imoocCache4 = new ImoocCache4<>(new ExpensiveFunction());
        Integer result = imoocCache4.compute("666");
        System.out.println("第一次计算结果：" + result);
        result = imoocCache4.compute("666");
        System.out.println("第二次计算结果：" + result);
    }
}
