import java.sql.*;
import java.util.Scanner;
public class EmployeeApp {
    static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String USER = "root";         // Change to your MySQL username
    static final String PASS = "password";     // Change to your MySQL password
    static Connection conn;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to DB!");
            int choice;
            do {
                System.out.println("\n=== Employee DB Menu ===");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1: addEmployee(); break;
                    case 2: viewEmployees(); break;
                    case 3: updateEmployee(); break;
                    case 4: deleteEmployee(); break;
                    case 5: System.out.println("Exiting..."); break;
                    default: System.out.println("Invalid choice");
                }
            } while (choice != 5);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    static void addEmployee() throws SQLException {
        System.out.print("Enter name: ");
        sc.nextLine(); // Clear newline
        String name = sc.nextLine();
        System.out.print("Enter role: ");
        String role = sc.nextLine();
        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();
        String query = "INSERT INTO employees (name, role, salary) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, role);
        ps.setDouble(3, salary);
        int rows = ps.executeUpdate();
        System.out.println(rows + " employee added.");
    }
    static void viewEmployees() throws SQLException {
        String query = "SELECT * FROM employees";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("\n--- Employee List ---");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("name") +
                    ", Role: " + rs.getString("role") +
                    ", Salary: ₹" + rs.getDouble("salary"));
        }
    }
    static void updateEmployee() throws SQLException {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // Clear newline
        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new role: ");
        String role = sc.nextLine();
        System.out.print("Enter new salary: ");
        double salary = sc.nextDouble();
        String query = "UPDATE employees SET name = ?, role = ?, salary = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, role);
        ps.setDouble(3, salary);
        ps.setInt(4, id);
        int rows = ps.executeUpdate();
        System.out.println(rows + " employee updated.");
    }
    static void deleteEmployee() throws SQLException {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        String query = "DELETE FROM employees WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        System.out.println(rows + " employee deleted.");
    }
}