package model;

public class SummaryObserver implements TransactionObserver {

    @Override
    public void update() {
        System.out.println("Summary panel refreshed.");
    }
}
