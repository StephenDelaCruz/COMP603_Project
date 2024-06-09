/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author neill
 */
public class MainMenuController {

    private MainMenuView mainMenuView;
    private ProductController productController;
    private OrderModel orderModel;
    private CartController cartController;
    private LoginController loginController;

    public MainMenuController(MainMenuView mainView, ProductController productController, LoginController loginController, OrderModel orderModel, CartController cartController) {
        this.mainMenuView = mainView;
        this.productController = productController;
        this.loginController = loginController;
        this.orderModel = orderModel;
        this.cartController = cartController;

        setupActionListeners();
        updateMenuView();
    }

    public void setupActionListeners() {
        mainMenuView.getBrowseButton().addActionListener(e -> productController.displayProducts());
        mainMenuView.getCartButton().addActionListener(e -> cartController.viewCart());
        mainMenuView.getLoginButton().addActionListener(e -> loginController.login());
        mainMenuView.getCreateUserButton().addActionListener(e -> loginController.createUser());
        mainMenuView.getViewOrdersButton().addActionListener(e -> viewOrders());
        mainMenuView.getLogoutButton().addActionListener(e -> loginController.logout());
    }

    //creates the view order pop up message
    public void viewOrders() {
        String username = cartController.getCurrentUser();
        List<Order> orders = orderModel.getOrdersByUsername(username);

        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(mainMenuView, "No orders found for the current user.");
            return;
        }

        //creates the message itself which includes your orderID and the total price you paid
        StringBuilder orderDetails = new StringBuilder("Orders:\n");
        for (Order order : orders) {
            orderDetails.append("Order ID: ").append(order.getOrderId())
                    .append(", Total Price: $")
                    .append(order.getTotalPrice()).append("\n");
        }

        JOptionPane.showMessageDialog(mainMenuView, orderDetails.toString(), "Orders", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateMenuView() {
        if (cartController.getCurrentUser() == null) {
            mainMenuView.setLoggedOutMenu();
        }
    }
}
