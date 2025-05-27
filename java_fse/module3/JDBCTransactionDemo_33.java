import java.sql.*;

public class JDBCTransactionDemo_33 {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/bank?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";      // Replace with your DB username
    private static final String PASSWORD = "root";  // Replace with your DB password

    public static void main(String[] args) {
        // Create the accounts table and insert sample data
        createTableAndInsertData();

        // Simulate a money transfer
        try {
            transferMoney(1, 2, 500.00);
            System.out.println("Transfer completed successfully.");
        } catch (SQLException e) {
            System.err.println("Transfer failed: " + e.getMessage());
            e.printStackTrace();
        }

        // Display final balances
        displayBalances();
    }

    // Method to create accounts table and insert sample data
    private static void createTableAndInsertData() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts (" +
                "account_id INT PRIMARY KEY, " +
                "balance DOUBLE NOT NULL)";
        String insertDataSQL = "INSERT INTO accounts (account_id, balance) VALUES " +
                "(1, 1000.00), (2, 500.00) ON DUPLICATE KEY UPDATE balance = balance";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            // Create table
            stmt.executeUpdate(createTableSQL);
            // Insert sample data
            stmt.executeUpdate(insertDataSQL);
            System.out.println("Accounts table created and initialized with sample data.");
        } catch (SQLException e) {
            System.err.println("Error setting up table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to transfer money between two accounts
    private static void transferMoney(int fromAccountId, int toAccountId, double amount) throws SQLException {
        Connection conn = null;
        try {
            // Establish connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // Disable auto-commit to manage transaction manually
            conn.setAutoCommit(false);

            // Check if source account has sufficient balance
            String checkBalanceSQL = "SELECT balance FROM accounts WHERE account_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSQL)) {
                checkStmt.setInt(1, fromAccountId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if (balance < amount) {
                        throw new SQLException("Insufficient balance in account " + fromAccountId);
                    }
                } else {
                    throw new SQLException("Source account " + fromAccountId + " does not exist");
                }
            }

            // Debit from source account
            String debitSQL = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            try (PreparedStatement debitStmt = conn.prepareStatement(debitSQL)) {
                debitStmt.setDouble(1, amount);
                debitStmt.setInt(2, fromAccountId);
                debitStmt.executeUpdate();
            }

            // Simulate an error condition (e.g., invalid account or negative balance)
            if (amount < 0) {
                throw new SQLException("Invalid transfer amount: " + amount);
            }

            // Credit to destination account
            String creditSQL = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            try (PreparedStatement creditStmt = conn.prepareStatement(creditSQL)) {
                creditStmt.setDouble(1, amount);
                creditStmt.setInt(2, toAccountId);
                int rowsAffected = creditStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Destination account " + toAccountId + " does not exist");
                }
            }

            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            // Roll back the transaction on error
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transaction rolled back due to error.");
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            throw e; // Re-throw the exception to inform the caller
        } finally {
            // Restore auto-commit and close connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Error closing connection: " + closeEx.getMessage());
                }
            }
        }
    }

    // Method to display account balances
    private static void displayBalances() {
        String query = "SELECT account_id, balance FROM accounts";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nCurrent Account Balances:");
            System.out.println("Account ID\tBalance");
            while (rs.next()) {
                int id = rs.getInt("account_id");
                double balance = rs.getDouble("balance");
                System.out.printf("%d\t\t%.2f%n", id, balance);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving balances: " + e.getMessage());
            e.printStackTrace();
        }
    }
}