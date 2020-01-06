package deadlock;

/**
 * 转账时遇到死锁，一旦打开注释，便会发生死锁。
 */
public class TransferMoney implements Runnable {
    private int flag;

    private static Account a = new Account(500);
    private static Account b = new Account(500);
    private static Object lock = new Object();

    private TransferMoney(int flag){
        this.flag = flag;
    }

    public static void main(String[] args) throws InterruptedException {
        TransferMoney r1 = new TransferMoney(1);
        TransferMoney r2 = new TransferMoney(0);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a的余额" + a.balance);
        System.out.println("b的余额" + b.balance);
    }

    public static void transferMoney(Account from, Account to, int amount) {
        /*try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        class Helper {
            public void transfer() {
                if (from.balance - amount < 0) {
                    System.out.println("余额不足，转账失败");
                    return;
                }
                from.balance -= amount;
                to.balance += amount;
                System.out.println("成功转账" + amount + "元");
            }
        }
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (lock) {
                synchronized (from) {
                    synchronized (to) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        if (flag == 1) {
            transferMoney(a, b, 200);
        } else {
            transferMoney(b, a, 200);
        }
    }

    static class Account {
        int balance;

        Account(int balance) {
            this.balance = balance;
        }
    }
}
