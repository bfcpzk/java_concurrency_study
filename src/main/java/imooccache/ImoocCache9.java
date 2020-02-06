package imooccache;


import imooccache.computable.Computable;
import imooccache.computable.ExpensiveFunction;
import imooccache.computable.MayFail;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 处理计算错误，避免缓存污染问题。
 * @param <A>
 * @param <V>
 */
public class ImoocCache9<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computer;

    public ImoocCache9(Computable<A, V> computer) {
        this.computer = computer;
    }

    public V compute(A key) throws ExecutionException, InterruptedException {
        while (true) {
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
            try {
                return f.get();
            } catch (CancellationException e) {
                System.out.println("被取消了");
                cache.remove(key);
                throw e;
            } catch (InterruptedException e) {
                cache.remove(key);
                throw e;
            } catch (ExecutionException e) {
                cache.remove(key);
                System.out.println("计算错误，开始重试");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ImoocCache9<String, Integer> imoocCache9 = new ImoocCache9<>(new MayFail());
        new Thread(() -> {
            try {
                Integer result = imoocCache9.compute("666");
                System.out.println("线程1的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache9.compute("666");
                System.out.println("线程3的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = imoocCache9.compute("667");
                System.out.println("线程2的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        /*Future<Integer> f = imoocCache9.cache.get("666");
        f.cancel(true);*/
    }
}
