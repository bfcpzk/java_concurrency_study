package future;

/**
 * 在run方法中不能抛出checked exception
 */
public class RunnableCantThrowException {

    public void normalMethod() throws Exception {

    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //throw new Exception(); 不行
            }
        };
    }
}
