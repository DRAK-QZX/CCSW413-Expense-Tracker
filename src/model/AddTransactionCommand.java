package model;

public class AddTransactionCommand implements Command {

    private ExpenseTrackerFacade facade;
    private String username;
    private Transaction transaction;

    public AddTransactionCommand(ExpenseTrackerFacade facade, String username, Transaction transaction) {
        this.facade = facade;
        this.username = username;
        this.transaction = transaction;
    }

    @Override
    public void execute() {
        try {
            facade.addTransaction(username, transaction);
            System.out.println("Transaction added successfully using Command Pattern.");
        } catch (Exception e) {
            System.out.println("Error while adding transaction using Command Pattern.");
            e.printStackTrace();
        }
    }
}