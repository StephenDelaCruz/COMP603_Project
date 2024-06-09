/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author neill
 */
public class ProductController {

    private ProductModel productModel;
    private CartController cartController;

    public ProductController(ProductModel productModel, CartController cartController) {
        this.productModel = productModel;
        this.cartController = cartController;
    }

    //displays our products along with the add to cart button and more info action button listeners
    public void displayProducts() {
        List<Product> products = productModel.getAllProducts();
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

        productView.getMoreInfoButton().addActionListener(e -> {
            Product selectedProduct = productView.getSelectedProduct();
            if (selectedProduct != null) {
                String message = String.format("%s comes with a %d-year warranty.",
                        selectedProduct.getName(), selectedProduct.getWarranty());
                JOptionPane.showMessageDialog(productView, message, "Product Warranty", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(productView, "Please select a product.");
            }
        });
    }
}
