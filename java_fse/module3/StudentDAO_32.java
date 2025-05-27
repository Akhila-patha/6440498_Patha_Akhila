import java.sql.*;
import java.util.Scanner;

public class StudentDAO_32 {

    private final String URL = "jdbc:mysql://localhost:3306/school";
    private final String USER = "root";
    private final String PASSWORD = "root";

    // Insert new student record
    public boolean insertStudent(int id, String name) {
        String sql = "INSERT INTO students (id, name) VALUES (?, ?)"; // '?' are placeholders

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);     
            pstmt.setString(2, name); 
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateStudentName(int id, String newName) {
        String sql = "UPDATE students SET name = ? WHERE id = ?"; // placeholders for update

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setInt(2, id);        
            return pstmt.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        // Gather input for insert
        System.out.print("Enter student ID to insert: ");
        int insertId = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        System.out.print("Enter student name to insert: ");
        String insertName = scanner.nextLine();

        // Call insert method
        if (dao.insertStudent(insertId, insertName)) {
            System.out.println("Insert successful.");
        } else {
            System.out.println("Insert failed.");
        }

        System.out.print("Enter student ID to update: ");
        int updateId = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        System.out.print("Enter new name: ");
        String updateName = scanner.nextLine();

       
        if (dao.updateStudentName(updateId, updateName)) {
            System.out.println("Update successful.");
        } else {
            System.out.println("Update failed.");
        }

        scanner.close();
    }
}
