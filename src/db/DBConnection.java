//package db;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
//    private static final String USER = "root"; // change if needed
//    private static final String PASSWORD = "sa123456";
//
//    public static Connection getConnection() throws SQLException {
//        String url = "jdbc:mysql://localhost:3306/expense_tracker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
//        System.out.println("Attempting to connect to: " + url);
//        System.out.println("Using User: " + USER);
//
//        try {
//            Connection conn = DriverManager.getConnection(url, USER, PASSWORD);
//            System.out.println("CONNECTION SUCCESSFUL!");
//            return conn;
//        } catch (SQLException e) {
//            System.err.println("CONNECTION FAILED!");
//            System.err.println("Error State: " + e.getSQLState());
//            System.err.println("Error Code: " + e.getErrorCode());
//            throw e;
//        }
//    }
//}
//

package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

//    public Connection getConnection() {
//        return connection;
//    }
//}

    public Connection getConnection() {
        try {
            // Check if connection is null or closed before returning it
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
