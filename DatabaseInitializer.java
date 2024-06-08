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

    public static void establishConnection() {
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

        Connection connection = this.conn;
        Statement statement = null;
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

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createProductsTable() {
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            this.statement = conn.createStatement();
            this.checkExistedTable("PRODUCTS");
            this.statement.addBatch("CREATE TABLE Products (PRODUCTID INT PRIMARY KEY, ITEM VARCHAR(255), PRICE DOUBLE, STOCK INT)");

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

    public void addWarrantyToProducts() {
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);  Statement stmt = conn.createStatement()) {

            stmt.execute("ALTER TABLE Products ADD COLUMN WARRANTY INT");
            System.out.println("Added WARRANTY column to Products table.");

            stmt.executeUpdate("UPDATE Products SET WARRANTY = CASE "
                    + "WHEN ITEM='Laptop' THEN 2 "
                    + "WHEN ITEM='Computer' THEN 3 "
                    + "WHEN ITEM='Phone' THEN 1 "
                    + "WHEN ITEM='Tablet' THEN 1 "
                    + "WHEN ITEM='Headphones' THEN 1 "
                    + "WHEN ITEM='Earphones' THEN 1 "
                    + "WHEN ITEM='Speaker' THEN 2 "
                    + "WHEN ITEM='Printer' THEN 2 "
                    + "WHEN ITEM='Camera' THEN 2 "
                    + "ELSE 0 END");
            System.out.println("Updated WARRANTY values for products.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            this.statement = conn.createStatement();
            this.checkExistedTable("USERS");
            this.statement.addBatch("CREATE TABLE Users (USERNAME VARCHAR(15) PRIMARY KEY, PASSWORD VARCHAR(15), EMAIL VARCHAR(255))");

            this.statement.addBatch("INSERT INTO Users VALUES('user', 'user', 'user@gmail.com'),\n"
                    + "('neil', 'luna8', 'neilluna8@gmail.com'),\n"
                    + "('stephen', 'delacruz9', 'stephendelacruz@gmail.com')");

            this.statement.executeBatch();
            System.out.println("Tables created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTransactionsTable() {
        try ( Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            this.statement = conn.createStatement();
            this.checkExistedTable("TRANSACTIONS");
            this.statement.addBatch("CREATE TABLE Transactions (ORDERID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), USERNAME VARCHAR(255), TOTALPRICE DOUBLE)");

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

    public void dropAllTables() {
        String[] tables = {"OrderItems", "Transactions", "Products", "Users"};
        for (String table : tables) {
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS PDC." + table);
                System.out.println("Dropped table: " + table);
            } catch (SQLException e) {
                System.err.println("Error dropping " + table + ": " + e.getMessage());
            }
        }
    }

}
