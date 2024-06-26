*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8;

/**
 *
 * @author neill
 */
public class PearStoreApp {

    public static void main(String[] args) {

        DatabaseInitializer dbi = new DatabaseInitializer();
        dbi.createProductsTable();
        dbi.createUsersTable();
        dbi.createTransactionsTable();

        MainMenuView mainView = new MainMenuView();
        ProductModel productModel = new ProductModel();
        LoginModel loginModel = new LoginModel();
        OrderModel orderModel = new OrderModel();

        CartController cartController = new CartController(orderModel);
        ProductController productController = new ProductController(productModel, cartController);
        LoginController loginController = new LoginController(loginModel, cartController, mainView);

        MainMenuController mainController = new MainMenuController(mainView, productController, loginController, orderModel, cartController);
        
        mainView.setVisible(true);
    }
}
