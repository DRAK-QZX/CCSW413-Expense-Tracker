package model;

public interface Transaction {
    double getAmount();
    String getCategory();
    String getDescription();
    String getDate();
    String getType();
    double applyToBalance(double currentBalance);
}
