package model;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExpenseTrackerFacade {

    private Connection connection;

    public ExpenseTrackerFacade() {
        connection = DBConnection.getInstance().getConnection();
    }

    public void addTransaction(String username, Transaction transaction) throws Exception {
        String sql = "INSERT INTO transactions(username, category_name, description, amount, date, type) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, transaction.getCategory());
            pst.setString(3, transaction.getDescription());
            pst.setDouble(4, transaction.getAmount());
            pst.setString(5, transaction.getDate());
            pst.setString(6, transaction.getType().toLowerCase());

            pst.executeUpdate();

            System.out.println("Transaction inserted using Facade Pattern.");
        }
    }

    public void deleteTransaction(int transactionId) throws Exception {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, transactionId);
            pst.executeUpdate();

            System.out.println("Transaction deleted using Facade Pattern.");
        }
    }
}