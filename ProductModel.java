/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mcste
 */
public class ProductModel {

    private static final String URL = "jdbc:derby:PearStoreDB_Ebd";

public List<Product> getAllProducts() {
    List<Product> products = new ArrayList<>();
    String query = "SELECT * FROM PRODUCTS";
    try (Connection conn = DriverManager.getConnection(URL, "pdc", "pdc");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            int id = rs.getInt("PRODUCTID");
            String item = rs.getString("ITEM");
            double price = rs.getDouble("PRICE");
            int stock = rs.getInt("STOCK");
            int warranty = rs.getInt("WARRANTY");  
            products.add(new Product(id, item, price, stock, warranty));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return products;
}
}
