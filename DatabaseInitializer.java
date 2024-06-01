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
 * @author neill
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

    private static void establishConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println(URL + " connected.");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ResultSet queryDB(String sql) {

        java.sql.Connection connection = this.conn;
        java.sql.Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {

        java.sql.Connection connection = this.conn;
        java.sql.Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createProducts() {
        try ( java.sql.Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            this.statement = conn.createStatement();
            this.checkExistedTable("PRODUCTS");
            this.statement.addBatch("CREATE TABLE products (PRODUCTID INT PRIMARY KEY, ITEM VARCHAR(255), PRICE DOUBLE, STOCK INT)");

            this.statement.addBatch("INSERT INTO Products VALUES (1, 'Laptop', 999.99, 6),\n"
                    + "(2, 'Computer', 1499.99, 35),\n"
                    + "(3, 'Phone', 599.99, 23),\n"
                    + "(4, 'Tablet', 299.99, 8),\n"
                    + "(5, 'Headphones', 99.99, 4),\n"
                    + "(6, 'Earphones', 84.99, 12),\n"
                    + "(7, 'Speaker', 109.99, 11),\n"
                    + "(8, 'Printer', 199.99, 17),\n"
                    + "(9, 'Camera', 899.99, 19)");

            this.statement.executeBatch();
            System.out.println("Tables created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public void createUsers() {
        try ( java.sql.Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            this.statement = conn.createStatement();
            this.checkExistedTable("USERS");
            this.statement.addBatch("CREATE TABLE Users (USERNAME VARCHAR(20) PRIMARY KEY, PASSWORD VARCHAR(20), EMAIL VARCHAR(50))");

            this.statement.addBatch("INSERT INTO Users VALUES('user', 'user', 'user@gmail.com'),\n"
                    + "('neil', 'luna8', 'neilluna8@gmail.com'),\n"
                    + "('stephen', 'delacruz9', 'stephendelacruz@gmail.com')");
            
            this.statement.executeBatch();
            System.out.println("Tables created successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
     public void createTransactions(){
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            this.statement = conn.createStatement();
            this.checkExistedTable("TRANSACTIONS");
            this.statement.addBatch("CREATE TABLE Transactions (ORDERID INT PRIMARY KEY, USERNAME VARCHAR(255), TOTALPRICE DOUBLE, "
                    + "CONSTRAINT Transactions_USERNAME_fk FOREIGN KEY (USERNAME) REFERENCES Users (USERNAME))");
            
            this.statement.executeBatch();
            System.out.println("Tables created successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void createOrderItems(){
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            this.statement = conn.createStatement();
            this.checkExistedTable("ORDERITEMS");
            this.statement.addBatch("CREATE TABLE OrderItems (ORDERID INT, PRODUCTID INT, "
                    + "CONSTRAINT OrderItems_ORDERID_fk FOREIGN KEY (ORDERID) REFERENCES Transactions (ORDERID), "
                    + "CONSTRAINT OrderItems_PRODUCTID_fk FOREIGN KEY (PRODUCTID) REFERENCES Products(PRODUCTID))");
            
            this.statement.executeBatch();
            System.out.println("Tables created successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkExistedTable(String name) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String[] types = {"TABLE"};
            statement = this.conn.createStatement();
            ResultSet rs = dbmd.getTables(null, null, null, types);

            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                System.out.println(table_name);
                if (table_name.equalsIgnoreCase(name)) {
                    statement.executeUpdate("Drop table " + name);
                    System.out.println("Table " + name + " has been deleted.");
                    break;
                }
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
