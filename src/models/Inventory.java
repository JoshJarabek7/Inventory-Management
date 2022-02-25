package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Creates a class called "Inventory"
public class Inventory {

    // Declares the variable allParts and creates an ObservableList for it
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    // Declares the variable allProducts and creates an ObservableList for it
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // Adds a new part to the list of all the parts
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    // Adds a new product to the list of all the products
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /*
    A method that looks for a part with the given ID
    If it finds one, it returns the part
    If it doesn't find one, it returns null
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part p : allParts) {
            if (p.getId() == partId) {
                return p;
            }
        }

        return null;
    }

    /*
    A method that looks for a product with the given ID
    If it finds one, it returns the product
    If it doesn't find one, it returns null
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product p : allProducts) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    /*
    A method that looks for a part with the given name
    If it finds one, it returns the part
    If it doesn't find one, it returns null
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part p : allParts) {
            if (p.getName().toLowerCase().contains(partName)) {
                foundParts.add(p);
            }
        }
        return foundParts;
    }

    /*
    A method that looks for a product with the given name
    If it finds one, it returns the product
    If it doesn't find one, it returns null
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product pr : allProducts) {
            if (pr.getName().toLowerCase().contains(productName)) {
                foundProducts.add(pr);
            }
        }
        return foundProducts;
    }

    /*
    A method that modifies the selected part
     */
    public static void modifyPart(int id, Part selectedPart) {
        int index = -1;
        for (Part part : getAllParts()) {
            index++;
            if (part.getId() == id) {
                getAllParts().set(index, selectedPart);
            }
        }
    }
    /*
    A method that modifies the selected product
     */
    public static void modifyProduct(int id, Product newProduct) {
        int index = -1;
        for (Product product : getAllProducts()) {
            index++;
            if (product.getId() == id) {
                getAllProducts().set(index, newProduct);
            }
        }
    }

    /*
    A method that deletes the selected part
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /*
    A method that deletes the selected product
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /*
    A method that returns all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /*
    A method that returns all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /*
    A method that counts the number of parts in a list
    It's initialized to 1 and then iterates through all the parts in the list
    Checks if the part's ID is greater than or equal to the id
    Allows for a new and unique ID for each part
     */
    public static int partIdCounter() {
        int id = 1;
        for (Part a : getAllParts()) {
            if (a.getId() >= id) {
                id = a.getId() + 1;
            }
        }
        return id;
    }

    /*
    A method that counts the number of products in a list
    It's initialized to 100 and then iterates through all the products in the list
    Checks if the product's ID is greater than or equal to the id
    Allows for a new and unique ID for each product
     */
    public static int productIdCounter() {
        int id = 100;
        for (Product p : getAllProducts()) {
            if (p.getId() >= id) {
                id = p.getId() + 1;
            }
        }
        return id;
    }
}

