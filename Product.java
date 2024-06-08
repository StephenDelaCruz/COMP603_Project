/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8;

/**
 *
 * @author mcste
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
        private int warranty;  


    public Product(int id, String name, double price, int stock, int warranty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.warranty = warranty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
    
    public int getWarranty() {
        return warranty;
    }


    public void updateStock(int quantity) {
        stock -= quantity;
    }
    
    public String toString(){
        return String.format("ProductID: %d, Item: %s, Price: $%.2f, Stock: %d", id, name, price, stock);
    }
}

