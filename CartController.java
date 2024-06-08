/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author neill
 */
public class CartController {
    
    private Cart cart;
    private String currentUser;
    private OrderModel orderModel;
    private MainMenuView mainView;

    public CartController(OrderModel orderDAO) {
        this.cart = new Cart();
        this.orderModel = orderDAO;
    }

    public void addToCart(Product product) {
        if (currentUser == null) { //checks to see if you have logged in
            JOptionPane.showMessageDialog(mainView, "You need to log in to add items to the cart.");
            return;
        }
        cart.addItem(product);
        JOptionPane.showMessageDialog(mainView, "Added to cart: " + product.getName());
    }

    public void viewCart() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(mainView, "You need to log in to view the cart.");
            return;
        }

        CartView cartView = new CartView();
        cartView.setCartList(cart.getItems());
        cartView.setVisible(true);

        cartView.getPlaceOrderButton().addActionListener(e -> placeOrder(cartView));
        cartView.getRemoveItemButton().addActionListener(e -> removeSelectedItem(cartView));
    }

    private void removeSelectedItem(CartView cartView) {
        Product selectedProduct = cartView.getCartList().getSelectedValue();
        if (selectedProduct != null) { //checks to see if you have selected an item to remove
            cart.removeItem(selectedProduct); //removes product from cart
            cartView.setCartList(cart.getItems());
            JOptionPane.showMessageDialog(cartView, "Removed from cart: " + selectedProduct.getName());
        } else {
            JOptionPane.showMessageDialog(cartView, "Please select an item to remove.");
        }
    }

    private void placeOrder(CartView cartView) {
        if (cart.getItems().isEmpty()) { //check if the cart is empty
            JOptionPane.showMessageDialog(cartView, "Your cart is empty. Please add items before placing an order.");
            return;
        }
        
        for (Product item : cart.getItems()) { 
            if (item.getStock() < 1) { //check if items there is stock for items in the cart
                JOptionPane.showMessageDialog(cartView, "Sorry, " + item.getName() + " is out of stock.");
                return;
            }
            if (item.getStock() < cart.getItems().stream().filter(p -> p.getId() == item.getId()).count()) { //ChatGPT helped us this if statement
                JOptionPane.showMessageDialog(cartView, "Sorry, there is not enough stock for " + item.getName());
                return; 
            }
        }
        
        //this sechtion places the order
        double totalPrice = cart.getTotalPrice();
        Order order = new Order(0, new ArrayList<>(cart.getItems()), totalPrice, currentUser);
        orderModel.saveOrder(order);
        JOptionPane.showMessageDialog(cartView, "Order placed successfully!");

        cart = new Cart();  //this resets the cart so that it is empty again
        cartView.setCartList(cart.getItems());
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }

    public String getCurrentUser() {
        return currentUser;
    }
    
}
