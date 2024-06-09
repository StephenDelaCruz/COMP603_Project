/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

/**
 *
 * @author neill
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartView extends JFrame {

    private JList<Product> cartList;
    private JButton placeOrderButton;
    private JButton removeItemButton;
    private DefaultListModel<Product> listModel;

    public CartView() {
        setTitle("Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        cartList = new JList<>(listModel);
        placeOrderButton = new JButton("Place Order");
        removeItemButton = new JButton("Remove Selected Item");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(placeOrderButton);
        buttonPanel.add(removeItemButton);

        add(new JScrollPane(cartList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JList<Product> getCartList() {
        return cartList;
    }

    public JButton getPlaceOrderButton() {
        return placeOrderButton;
    }

    public JButton getRemoveItemButton() {
        return removeItemButton;
    }

    public void setCartList(List<Product> products) {
        listModel.clear();
        for (Product product : products) {
            listModel.addElement(product);
        }
    }
}
