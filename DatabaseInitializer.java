/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mcste
 */
public class DatabaseInitializer {

    private static final String USERNAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:PearStoreDB_Ebd; create=true";
    private Statement statement;

    private static Connection conn;

    public DatabaseInitializer() {
        establishConnection();
    }

    public static void main(String[] args) {
        DatabaseInitializer dbi = new DatabaseInitializer();
        System.out.println(dbi.getConnection());
    }

    public Connection getConnection() {
        return this.conn;
    }

    public static void establishConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println(URL + " connected.");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    //creates our products table if they don't already exist
    public void createProductsTable() { 
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            statement = conn.createStatement();
            checkExistedTable("PRODUCTS");   
            if (!checkExistedTable("PRODUCTS")) {
                statement.addBatch("CREATE TABLE Products (PRODUCTID INT PRIMARY KEY, ITEM VARCHAR(255), PRICE DOUBLE, STOCK INT, WARRANTY INT)");

                statement.addBatch("INSERT INTO Products VALUES (1, 'Laptop', 999.99, 6, 2),\n"
                        + "(2, 'Computer', 1499.99, 35, 3),\n"
                        + "(3, 'Phone', 599.99, 23, 1),\n"
                        + "(4, 'Tablet', 299.99, 8, 1),\n"
                        + "(5, 'Headphones', 99.99, 4, 1),\n"
                        + "(6, 'Earphones', 84.99, 12, 1),\n"
                        + "(7, 'Speaker', 109.99, 11, 2),\n"
                        + "(8, 'Printer', 199.99, 17, 2),\n"
                        + "(9, 'Camera', 899.99, 19, 2)");

                statement.executeBatch();

                System.out.println("Tables created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //creates users table if it doesn't already exist
    public void createUsersTable() { 
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            statement = conn.createStatement();
            checkExistedTable("USERS");
            if (!checkExistedTable("USERS")) {
                statement.addBatch("CREATE TABLE Users (USERNAME VARCHAR(15) PRIMARY KEY, PASSWORD VARCHAR(255), EMAIL VARCHAR(255))");

                statement.addBatch("INSERT INTO Users VALUES('user', 'user', 'user@gmail.com'),\n"
                        + "('neil', 'luna8', 'neilluna8@gmail.com'),\n"
                        + "('stephen', 'delacruz9', 'stephendelacruz@gmail.com')");

                statement.executeBatch();
                System.out.println("Tables created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //create transaction table if it doesn't already exist
    public void createTransactionsTable() { 
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            statement = conn.createStatement();
            checkExistedTable("TRANSACTIONS");
            if (!checkExistedTable("TRANSACTIONS")) {
                statement.addBatch("CREATE TABLE Transactions (ORDERID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), USERNAME VARCHAR(255), TOTALPRICE DOUBLE)");

                statement.executeBatch();
                System.out.println("Tables created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //checks to see if table exists. returns true or false depending on if it exists or not
    public boolean checkExistedTable(String name) throws SQLException { 
        DatabaseMetaData meta = conn.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = meta.getTables(null, null, name.toUpperCase(), types);
        return rs.next();
    }
}
