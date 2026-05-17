package model;

public class BalanceObserver implements TransactionObserver {

    private TransactionManager transactionManager;

    public BalanceObserver(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void update() {
        double balance = 0;

        for (Transaction transaction : transactionManager.getTransactions()) {
            balance = transaction.applyToBalance(balance);
        }

        System.out.println("Balance updated: " + balance);
    }
}
