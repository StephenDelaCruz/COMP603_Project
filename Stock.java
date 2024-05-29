/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment1;

/**
 *
 * @author aweso
 */
import java.util.ArrayList;
import java.util.List;

public class Stock {
    private List<Product> products;
    private List<Phone> phones;
    private List<Camera> cameras;

    public Stock() {
        products = new ArrayList<>();
        phones = new ArrayList<>();
        cameras = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }
    
    public void addPhones(Phone phone){
        phones.add(phone);
    }
    
    public void addCameras(Camera camera){
        cameras.add(camera);
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public List<Phone> getPhones(){
        return phones;
    }
    
    public List<Camera> getCameras(){
        return cameras;
    }

    public boolean checkStock(Product product, int quantity) {
        return product.getStock() >= quantity;
    }

    public void updateStock(Product product, int quantity) {
        product.updateStock(-quantity);
    }
}
