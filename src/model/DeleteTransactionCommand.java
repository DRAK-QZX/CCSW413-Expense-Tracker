package model;

public class DeleteTransactionCommand implements Command {

    private ExpenseTrackerFacade facade;
    private int transactionId;

    public DeleteTransactionCommand(ExpenseTrackerFacade facade, int transactionId) {
        this.facade = facade;
        this.transactionId = transactionId;
    }

    @Override
    public void execute() {
        try {
            facade.deleteTransaction(transactionId);
            System.out.println("Transaction deleted successfully using Command Pattern.");
        } catch (Exception e) {
            System.out.println("Error while deleting transaction using Command Pattern.");
            e.printStackTrace();
        }
    }
}