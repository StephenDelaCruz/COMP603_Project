/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import javax.swing.JOptionPane;

/**
 *
 * @author mcste
 */
public class LoginController {

    private LoginModel loginModel;
    private CartController cartController;
    private MainMenuView mainMenuView;
    

    public LoginController(LoginModel loginModel, CartController cartController, MainMenuView mainView) {
        this.loginModel = loginModel;
        this.cartController = cartController;
        this.mainMenuView = mainView;
    }

    //logs users in
    public void login() { 
        LoginView loginView = new LoginView();
        loginView.setVisible(true);

        loginView.getLoginButton().addActionListener(e -> {
            String username = loginView.getUsernameField().getText();
            String password = new String(loginView.getPasswordField().getPassword());

            if (loginModel.authenticateUser(username, password)) {
                cartController.setCurrentUser(username);
                JOptionPane.showMessageDialog(loginView, "Login successful!");
                loginView.dispose();
                mainMenuView.setLoggedInMenu();
            } else {
                JOptionPane.showMessageDialog(loginView, "Invalid username or password.");
            }
        });
    }

    //handles new user creation
    public void createUser() { 
        CreateUserView createUserView = new CreateUserView();
        createUserView.setVisible(true);

        createUserView.getCreateButton().addActionListener(e -> {
            String username = createUserView.getUsernameField().getText().trim();
            String password = new String(createUserView.getPasswordField().getPassword()).trim();
            String email = createUserView.getEmailField().getText().trim();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) { //checks to see if the fields have been filled in and not empty
                JOptionPane.showMessageDialog(createUserView, "All fields must be filled. Please enter username, password, and email.");
                return;
            } else if (username.length() > 15 || password.length() > 15) { //checks to see if the username and password are within our character limit
                JOptionPane.showMessageDialog(createUserView, "Username and or Password exceeds the limit. Must be 15 characters.");
                System.out.println("Invalid Username");
                return;
            }
            
            if (loginModel.insertIntoUserTable(username, password, email)) { //create user if the fields have been filled to our standards
                JOptionPane.showMessageDialog(createUserView, "User created successfully!");
                createUserView.dispose();
            } else {
                JOptionPane.showMessageDialog(createUserView, "User creation failed. \n Username might already be taken.");
            }
        });
    }

    //logs users out
    public void logout() { 
        cartController.setCurrentUser(null);
        JOptionPane.showMessageDialog(mainMenuView, "Logged out successfully!");
        mainMenuView.setLoggedOutMenu();
    }
}
