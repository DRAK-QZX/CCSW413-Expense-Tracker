package model;

public class TransactionFactory {

    public static Transaction createTransaction(
            String type,
            double amount,
            String category,
            String description,
            String date
    ) {

        if (type.equalsIgnoreCase("Expense")) {
            return new ExpenseTransaction(amount, category, description, date);

        } else if (type.equalsIgnoreCase("Income")) {
            return new IncomeTransaction(amount, category, description, date);

        } else {
            throw new IllegalArgumentException("Invalid transaction type: " + type);
        }
    }
}