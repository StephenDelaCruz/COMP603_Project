/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication8;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aweso
 */
public class Cart {
private List<Product> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(Product product) {
        items.add(product);
    }

    public List<Product> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice();
        }
        return total;
    }
}
