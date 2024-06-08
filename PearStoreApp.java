*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8;

/**
 *
 * @author mcste
 */
public class PearStoreApp {

    public static void main(String[] args) {

        DatabaseInitializer dbi = new DatabaseInitializer();
        dbi.createProducts();
        dbi.addWarrantyColumnToProducts();  
        dbi.createUsers();
        dbi.createTransactions();

        MainMenuView mainView = new MainMenuView();
        ProductModel productDAO = new ProductModel();
        UserModel userDAO = new UserModel();
        OrderModel orderDAO = new OrderModel();

        CartController cartController = new CartController(orderDAO);
        ProductController productController = new ProductController(productDAO, cartController);

        MainMenuController mainController = new MainMenuController(mainView, productController, userDAO, orderDAO, cartController);
        mainView.setVisible(true);
    }
}
