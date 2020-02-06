package imooccache;


import imooccache.computable.Computable;
import imooccache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * 用装饰者模式，给计算器自动添加缓存功能
 * @param <A>
 * @param <V>
 */
public class ImoocCache2<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new HashMap<>();

    private final Computable<A, V> computer;

    public ImoocCache2(Computable<A, V> computer) {
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
        ImoocCache2<String, Integer> imoocCache2 = new ImoocCache2<>(new ExpensiveFunction());
        Integer result = imoocCache2.compute("666");
        System.out.println("第一次计算结果：" + result);
        result = imoocCache2.compute("666");
        System.out.println("第二次计算结果：" + result);
    }
}
