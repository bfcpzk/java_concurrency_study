package deadlock;

import java.util.Random;

import static deadlock.TransferMoney.*;
/**
 * 多人同时转账，依然很危险
 */
public class MultiTransferMoney {

    private static final int NUM_ACCOUNTS = 500;
    private static final int NUM_ITERATIONS = 1000000;
    private static final int NUM_MONEY = 1000;
    private static final int NUM_THREADS = 20;

    public static void main(String[] args) {
        Account[] accounts = new Account[NUM_ACCOUNTS];
        for (int i = 0 ; i < accounts.length ; i++) {
            accounts[i] = new Account(NUM_MONEY);
        }
        Random rnd = new Random();

        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS ; i++) {
                    int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
                    int toAcct = rnd.nextInt(NUM_ACCOUNTS);
                    int rndAmount = rnd.nextInt(NUM_MONEY);
                    transferMoney(accounts[fromAcct], accounts[toAcct], rndAmount);
                }
            }
        }

        for (int i = 0; i < NUM_THREADS ; i++) {
            new TransferThread().start();
        }
    }
}
