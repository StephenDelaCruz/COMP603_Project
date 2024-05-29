/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment1;

/**
 *
 * @author mcste
 */
import java.io.*;
import java.util.*;

public class OrderProcess {

    private Stock stockOnHand;
    private Cart cart;
    private List<Order> orders;

    public OrderProcess() {
        stockOnHand = new Stock();
        cart = new Cart();
        orders = new ArrayList<>();
    }

    public void loadProducts(String filename) {
        try ( BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int stock = Integer.parseInt(parts[2]);
                    Product product = new Product(name, price, stock);
                    this.stockOnHand.addProduct(product);
                } else if (parts.length == 4) {
                    String name = parts[0];
                    String brand = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int stock = Integer.parseInt(parts[3]);
                    Phone phone = new Phone(name, brand, price, stock);
                    this.stockOnHand.addPhones(phone);
                } else if (parts.length == 5) {
                    String name = parts[0];
                    String brand = parts[1];
                    int megaPixels = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    int stock = Integer.parseInt(parts[4]);
                    Camera camera = new Camera(name, brand, megaPixels, price, stock);
                    this.stockOnHand.addCameras(camera);
                }
            }
        } catch (IOException e) {
            System.out.println("Error, IOException.");
        }
    }

    public void displayProducts() {
        List<Product> products = stockOnHand.getProducts();
        List<Phone> phones = stockOnHand.getPhones();
        List<Camera> cameras = stockOnHand.getCameras();

        System.out.println("\nAvailable Products:");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println((i + 1) + ". " + product.getName() + ": $" + product.getPrice() + " - Stock: " + product.getStock() + " - " + product.toString());
        }
        for (int i = 0; i < phones.size(); i++) {
            Phone phone = phones.get(i);
            System.out.println((products.size() + i + 1) + ". " + phone.getName() + " (" + phone.getBrand() + "): $" + phone.getPrice() + " - Stock: " + phone.getStock() + " - " + phone.toString());
        }
        for (int i = 0; i < cameras.size(); i++) {
            Camera camera = cameras.get(i);
            System.out.println((products.size() + phones.size() + i + 1) + ". " + camera.getName() + " (" + camera.getBrand() + ")" + camera.getMegaPixels() + "mp: $" + camera.getPrice() + " - Stock: " + camera.getStock()+ " - " + camera.toString());
        }
        System.out.println();
    }

    public void addToCart(int index) {
        List<Product> products = stockOnHand.getProducts();
        List<Phone> phones = stockOnHand.getPhones();
        List<Camera> cameras = stockOnHand.getCameras();

        if (index >= 0 && index < products.size()) {
            Product product = products.get(index);
            if (stockOnHand.checkStock(product, 1)) {
                cart.addItem(product);
                System.out.println(product.getName() + " added to cart.");
            } else {
                System.out.println("\nSorry, " + product.getName() + " is currently out of stock.");
            }
        } else if (index >= products.size() && index < products.size() + phones.size()) {
            Phone phone = phones.get(index - products.size());
            if (stockOnHand.checkStock(phone, 1)) {
                cart.addItem(phone);
                System.out.println(phone.getName() + " (" + phone.getBrand() + ") added to cart.");
            } else {
                System.out.println("\nSorry, " + phone.getName() + " (" + phone.getBrand() + ") is currently out of stock.");
            }
        } else if (index >= products.size() + phones.size() && index < products.size() + phones.size() + cameras.size()) {
            Camera camera = cameras.get(index - products.size() - phones.size());
            if (stockOnHand.checkStock(camera, 1)) {
                cart.addItem(camera);
                System.out.println(camera.getName() + " (" + camera.getBrand() + ")" + camera.getMegaPixels() + "mp, added to cart. " + camera.toString());
            } else {
                System.out.println("\nSorry, " + camera.getName() + " (" + camera.getBrand() + ")" + camera.getMegaPixels() + "mp, is currently out of stock.");
            }
        } else {
            System.out.println("Invalid product index.");
        }
    }

    public void displayCart() {
        List<Product> items = cart.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            for (Product item : items) {
                if (item instanceof Phone) {
                    Phone phone = (Phone) item;
                    System.out.println("- " + phone.getName() + " (" + phone.getBrand() + "): $" + phone.getPrice() + " - " + phone.toString());
                } else if (item instanceof Camera) {
                    Camera camera = (Camera) item;
                    System.out.println("- " + camera.getName() + " (" + camera.getBrand() + ")" + camera.getMegaPixels() + "mp: $" + camera.getPrice() + " - " + camera.toString());
                } else {
                    System.out.println("- " + item.getName() + ": $" + item.getPrice() + " - " + item.toString());
                }
            }
            System.out.println("Total: $" + cart.getTotalPrice());
        }
    }

    public void saveStockToFile(String filename) {
        try ( FileWriter writer = new FileWriter(filename)) {
            List<Product> products = stockOnHand.getProducts();
            List<Phone> phones = stockOnHand.getPhones();
            List<Camera> cameras = stockOnHand.getCameras();
            
            for (Product product : products) {
                writer.write(product.getName() + "," + product.getPrice() + "," + product.getStock() + "\n");
            }
            for (Phone phone : phones) {
                writer.write(phone.getName() + "," + phone.getBrand() + "," + phone.getPrice() + "," + phone.getStock() + "\n");
            }
            for (Camera camera : cameras) {
                writer.write(camera.getName() + "," + camera.getBrand() + "," + camera.getMegaPixels() + "," + camera.getPrice() + "," + camera.getStock() + "\n");
            }
            
            System.out.println("Stock updated and saved to file.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving inventory to file.");
        }
    }
    
    //ChatGPT helped us with this code
    private int getNextOrderNumber() {
        int nextOrderNumber = 0;
        try ( BufferedReader reader = new BufferedReader(new FileReader("./rsc/orders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] orderInfo = line.split(",");
                if (orderInfo.length > 0 && orderInfo[0].matches("\\d+")) {
                    nextOrderNumber = Math.max(nextOrderNumber, Integer.parseInt(orderInfo[0]));
                }
            }
            nextOrderNumber++;
        } catch (IOException e) {
            System.out.println("Error: Orders.txt file not found.");
        }
        return nextOrderNumber;
    }

    public void placeOrder(String username) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty. Please add items before placing an order.");
            return;
        }

        List<Product> items = cart.getItems();
        double totalPrice = cart.getTotalPrice();
        Order order = new Order(getNextOrderNumber(), items, totalPrice, username);
        orders.add(order);

        for (Product item : items) {
            stockOnHand.updateStock(item, -1);
            System.out.println("Stock for " + item.getName() + " updated to: " + item.getStock());
        }

        saveStockToFile("./rsc/Products.txt");

        cart.getItems().clear();
        System.out.println("Order placed successfully. Thank you for shopping with us!");
        System.out.println("Order ID: " + order.getOrderId());

        saveOrderToFile(order);
    }

    private void saveOrderToFile(Order order) {
        try ( PrintWriter writer = new PrintWriter(new FileWriter("./rsc/orders.txt", true))) {
            writer.println(order.getOrderId() + "," + order.getUsername() + ",$" + order.getTotalPrice());
            writer.println();
        } catch (IOException e) {
            System.out.println("Error saving order to file");
        }
    }
}
