package imooccache.computable;

/**
 * 有一个计算函数compute，用来代表耗时计算，每个计算器都要实现这个接口，这样就可以无侵入实现缓存功能。
 * @param <A>
 * @param <V>
 */
public interface Computable<A, V> {

    public V compute(A key) throws Exception;
}
