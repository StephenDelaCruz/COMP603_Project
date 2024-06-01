/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8;

/**
 *
 * @author mcste
 */
import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    private JButton viewButton;
    private JButton cartButton;
    private JButton loginButton;
    private JButton createUserButton;

    public MainMenuView() {
        setTitle("Pear Store");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        viewButton = new JButton("Browse Products");
        cartButton = new JButton("View Cart");
        loginButton = new JButton("Log In");
        createUserButton = new JButton("Create User");

        add(viewButton);
        add(cartButton);
        add(loginButton);
        add(createUserButton);
    }

    public JButton getBrowseButton() {
        return viewButton;
    }

    public JButton getCartButton() {
        return cartButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCreateUserButton() {
        return createUserButton;
    }
}
