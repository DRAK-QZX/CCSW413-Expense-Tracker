package model;

public class TransactionTableObserver implements TransactionObserver {

    private TransactionManager transactionManager;

    public TransactionTableObserver(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void update() {
        System.out.println("Transaction table updated.");
        System.out.println("Total transactions: " + transactionManager.getTransactions().size());
    }
}