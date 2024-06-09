/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

/**
 *
 * @author mcste
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    private static final String URL = "jdbc:derby:PearStoreDB_Ebd";

    //saves your order into our database and adds it to the transactions table
    public void saveOrder(Order order) {
        String insertTransaction = "INSERT INTO Transactions (ORDERID, USERNAME, TOTALPRICE) VALUES (DEFAULT, ?, ?)";
        String updateStock = "UPDATE Products SET STOCK = STOCK - ? WHERE PRODUCTID = ?";

        try ( Connection conn = DriverManager.getConnection(URL, "pdc", "pdc");  PreparedStatement pstmtOrder = conn.prepareStatement(insertTransaction, Statement.RETURN_GENERATED_KEYS);  PreparedStatement pstmtStock = conn.prepareStatement(updateStock)) {

            conn.setAutoCommit(false);

            pstmtOrder.setString(1, order.getUsername());
            pstmtOrder.setDouble(2, order.getTotalPrice());
            pstmtOrder.executeUpdate();

            ResultSet generatedKeys = pstmtOrder.getGeneratedKeys();
            int orderId;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated order ID.");
            }

            for (Product product : order.getItems()) {
                pstmtStock.setInt(1, 1);
                pstmtStock.setInt(2, product.getId());
                pstmtStock.addBatch();
            }

            pstmtStock.executeBatch();

            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //retrieves your order from our database, which is then used for view orders button
    public List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Transactions WHERE USERNAME = ?";

        try ( Connection conn = DriverManager.getConnection(URL, "pdc", "pdc");  PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);

            try ( ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("ORDERID");
                    double totalPrice = rs.getDouble("TOTALPRICE");
                    orders.add(new Order(orderId, new ArrayList<>(), totalPrice, username));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
