import java.sql.*;

public class AccountDAO {

    private final String URL = "jdbc:mysql://localhost:3306/bank";
    private final String USER = "root";
    private final String PASSWORD = "root";

    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        String debitSQL = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        String creditSQL = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            try (PreparedStatement debitStmt = conn.prepareStatement(debitSQL);
                 PreparedStatement creditStmt = conn.prepareStatement(creditSQL)) {

                debitStmt.setDouble(1, amount);
                debitStmt.setInt(2, fromAccountId);
                int rowsDebited = debitStmt.executeUpdate();

                creditStmt.setDouble(1, amount);
                creditStmt.setInt(2, toAccountId);
                int rowsCredited = creditStmt.executeUpdate();

                if (rowsDebited == 1 && rowsCredited == 1) {
                    conn.commit();
                    System.out.println("Transfer completed successfully!");
                    return true;
                } else {
                    conn.rollback();
                    System.out.println("Transfer failed, rolling back.");
                    return false;
                }

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();

        int fromAccount = 1;
        int toAccount = 2;
        double amountToTransfer = 500.0;

        dao.transfer(fromAccount, toAccount, amountToTransfer);
    }
}
