package model;

public class IncomeTransaction implements Transaction {

    private double amount;
    private String category;
    private String description;
    private String date;

    public IncomeTransaction(double amount, String category, String description, String date) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getType() {
        return "Income";
    }

    @Override
    public double applyToBalance(double currentBalance) {
        return currentBalance + amount;
    }
}