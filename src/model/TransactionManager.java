package model;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    private List<Transaction> transactions;
    private List<TransactionObserver> observers;

    public TransactionManager() {
        transactions = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void addObserver(TransactionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TransactionObserver observer) {
        observers.remove(observer);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        notifyObservers();
    }

    public void deleteTransaction(Transaction transaction) {
        transactions.remove(transaction);
        notifyObservers();
    }

    public void clearTransactions() {
        transactions.clear();
        notifyObservers();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    private void notifyObservers() {
        for (TransactionObserver observer : observers) {
            observer.update();
        }
    }
}