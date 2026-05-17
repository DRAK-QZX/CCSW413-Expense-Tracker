package model;

public class ClearTransactionsCommand implements Command {

    private TransactionManager transactionManager;

    public ClearTransactionsCommand(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void execute() {
        transactionManager.clearTransactions();
        System.out.println("All transactions cleared successfully.");
    }
}