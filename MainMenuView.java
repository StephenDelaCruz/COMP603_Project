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
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {

    private JButton browseButton;
    private JButton cartButton;
    private JButton loginButton;
    private JButton createUserButton;
    private JButton viewOrdersButton;
    private JButton logoutButton;
    
    public MainMenuView() {
        setTitle("Pear Store");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //creates buttons by calling createButton method
        browseButton = createButton("Browse Products", Color.BLUE);
        cartButton = createButton("View Cart", Color.GREEN);
        loginButton = createButton("Log In", Color.GRAY);
        createUserButton = createButton("Create User", Color.darkGray);
        viewOrdersButton = createButton("View Orders", Color.MAGENTA);
        logoutButton = createButton("Log Out", Color.RED);

        //adds the buttons onto our panel
        buttonPanel.add(browseButton);
        buttonPanel.add(cartButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(createUserButton);
        buttonPanel.add(viewOrdersButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    //button creation method where we can edit buttons visuals
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16)); //text font
        button.setForeground(Color.WHITE); //text colour
        button.setBackground(color); //background colour
        button.setFocusPainted(false); //remove focus outline around text
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }

    public JButton getBrowseButton() {
        return browseButton;
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

    public JButton getViewOrdersButton() {
        return viewOrdersButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public void setLoggedOutMenu() {
        browseButton.setVisible(true);
        loginButton.setVisible(true);
        createUserButton.setVisible(true);
        viewOrdersButton.setVisible(false); //doesn't show buttons that are only visible when logged in
        logoutButton.setVisible(false);
        cartButton.setVisible(false);
    }

    public void setLoggedInMenu() {
        browseButton.setVisible(true);
        loginButton.setVisible(false); //doesn't show buttons that are only visible when logged out
        createUserButton.setVisible(false);
        viewOrdersButton.setVisible(true);
        logoutButton.setVisible(true);
        cartButton.setVisible(true);
    }
}
