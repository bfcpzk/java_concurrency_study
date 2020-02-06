package imooccache;


import imooccache.computable.Computable;
import imooccache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 利用Future解决重复计算问题，解决的是当计算时间比较长的任务，起的作用比较大，但是不能保证只执行一次
 * @param <A>
 * @param <V>
 */
public class ImoocCache7<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computer;

    public ImoocCache7(Computable<A, V> computer) {
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
            f = ft;
            cache.put(key, ft);
            System.out.println("从FutureTask调用了计算函数");
            ft.run();
        }
        return f.get();
    }

    public static void main(String[] args) throws Exception {
        ImoocCache7<String, Integer> imoocCache7 = new ImoocCache7<>(new ExpensiveFunction());
        new Thread(() -> {
            try {
                Integer result = imoocCache7.compute("666");
                System.out.println("线程1的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache7.compute("666");
                System.out.println("线程3的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache7.compute("667");
                System.out.println("线程2的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
