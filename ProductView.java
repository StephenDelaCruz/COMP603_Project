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
import java.awt.event.ActionListener;
import java.util.List;

public class ProductView extends JFrame {
    private JList<Product> productList;
    private JButton addToCartButton;
    private JButton moreInfoButton;  
    private DefaultListModel<Product> listModel;

    public ProductView() {
        setTitle("Browse Products");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        productList = new JList<>(listModel);

        addToCartButton = new JButton("Add to Cart");
        moreInfoButton = new JButton("More Info");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton);
        buttonPanel.add(moreInfoButton);

        add(new JScrollPane(productList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JList<Product> getProductList() {
        return productList;
    }

    public JButton getAddToCartButton() {
        return addToCartButton;
    }

    public JButton getMoreInfoButton() {
        return moreInfoButton;
    }

    public void setProductList(List<Product> products) {
        listModel.clear();
        for (Product product : products) {
            listModel.addElement(product);
        }
    }

    public void setAddToCartButtonListener(ActionListener listener) {
        addToCartButton.addActionListener(listener);
    }

    public void setMoreInfoButtonListener(ActionListener listener) {
        moreInfoButton.addActionListener(listener);
    }

    public Product getSelectedProduct() {
        return productList.getSelectedValue();
    }
}
