/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8;

/**
 *
 * @author mcste
 */
import java.sql.*;

public class LoginModel {

    private static final String URL = "jdbc:derby:PearStoreDB_Ebd";

    //verifies if user credentials are in our database
    public boolean authenticateUser(String username, String password) { 
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, "pdc", "pdc");
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("User already exists");
        }

        return false;
    }

    //handles the user creation by inserting users input for creating a new user into our database
    public boolean insertIntoUserTable(String username, String password, String email) {
        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, "pdc", "pdc");
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {  // 23505 is the SQL state for unique constraint violation
                System.out.println("User already exists");
            } else {
                e.printStackTrace();
            }
        }

        return false;
    }
}
