package cas;

/**
 * 模拟CAS等价代码
 */
public class SimulatedCAS {
    private volatile int value;
    public synchronized int compareAndSet(int expectValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectValue) {
            value = newValue;
        }
        return oldValue;
    }
}
