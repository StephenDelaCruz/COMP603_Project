/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8;

import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mcste
 */
public class ProductController {

    private ProductModel productDAO;
    private CartController cartController;

    public ProductController(ProductModel productDAO, CartController cartController) {
        this.productDAO = productDAO;
        this.cartController = cartController;
    }

    public void displayProducts() {
        List<Product> products = productDAO.getAllProducts();
        ProductView productView = new ProductView();
        productView.setProductList(products);
        productView.setVisible(true);

        productView.getAddToCartButton().addActionListener(e -> {
            Product selectedProduct = productView.getSelectedProduct();
            if (selectedProduct != null) {
                cartController.addToCart(selectedProduct);
            } else {
                JOptionPane.showMessageDialog(productView, "Please select a product.");
            }
        });
    }
}
