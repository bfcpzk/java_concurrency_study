package imooccache;


import imooccache.computable.Computable;
import imooccache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 解决Future少量重复计算的问题，利用ConcurrentHashMap的putIfAbsent方法。
 * @param <A>
 * @param <V>
 */
public class ImoocCache8<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computer;

    public ImoocCache8(Computable<A, V> computer) {
        this.computer = computer;
    }

    public V compute(A key) throws Exception {
        Future<V> f = cache.get(key);
        if (f == null) {
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return computer.compute(key);
                }
            };
            FutureTask<V> ft = new FutureTask<>(callable);
            f = cache.putIfAbsent(key, ft);//保证了原子性
            if (f == null) {
                f = ft;
                System.out.println("从FutureTask调用了计算函数");
                ft.run();
            }
        }
        return f.get();
    }

    public static void main(String[] args) throws Exception {
        ImoocCache8<String, Integer> imoocCache8 = new ImoocCache8<>(new ExpensiveFunction());
        new Thread(() -> {
            try {
                Integer result = imoocCache8.compute("666");
                System.out.println("线程1的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache8.compute("666");
                System.out.println("线程3的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
