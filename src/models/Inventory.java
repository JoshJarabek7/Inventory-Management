package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Josh Jarabek
 */

/**
 * Creates a class called "Inventory"
 */
public class Inventory {

    /**
     * The allParts
     * Declares the variable allParts and creates an ObservableList for it
     */

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * The allProducts
     * Declares the variable allProducts and creates an ObservableList for it
     */

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add part.
     * Adds a new part to the list of all the parts
     * @param newPart the new part
     */

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add product.
     * Adds a new product to the list of all the products
     * @param newProduct the new product
     */

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Lookup part part.
     * A method that looks for a part with the given ID
     * If it finds one, it returns the part
     * If it doesn't find one, it returns null
     * @param partId the part id
     * @return the part
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

    /**
     * Lookup product product.
     * A method that looks for a product with the given ID
     * If it finds one, it returns the product
     * If it doesn't find one, it returns null
     * @param productId the product id
     * @return the product
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

    /**
     * Lookup part observable list.
     * A method that looks for a part with the given name
     * If it finds one, it returns the part
     * If it doesn't find one, it returns null
     * @param partName the part name
     * @return the observable list
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

    /**
     * Lookup product observable list.
     * A method that looks for a product with the given name
     * If it finds one, it returns the product
     * If it doesn't find one, it returns null
     * @param productName the product name
     * @return the observable list
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

    /**
     * Modify part.
     * A method that modifies the selected part
     * @param id           the id
     * @param selectedPart the selected part
     */

    public static void updatePart(int id, Part selectedPart) {
        int index = -1;
        for (Part part : getAllParts()) {
            index++;
            if (part.getId() == id) {
                getAllParts().set(index, selectedPart);
            }
        }
    }

    /**
     * Modify product.
     * A method that modifies the selected product
     * @param id         the id
     * @param newProduct the new product
     */

    public static void updateProduct(int id, Product newProduct) {
        int index = -1;
        for (Product product : getAllProducts()) {
            index++;
            if (product.getId() == id) {
                getAllProducts().set(index, newProduct);
            }
        }
    }

    /**
     * Delete part boolean.
     * A method that deletes the selected part
     * @param selectedPart the selected part
     * @return the boolean
     */

    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * Delete product boolean.
     * A method that deletes the selected product
     * @param selectedProduct the selected product
     * @return the boolean
     */

    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * Gets all parts.
     * A method that returns all parts
     * @return the all parts
     */

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products.
     * A method that returns all products
     * @return the all products
     */

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Part id counter int.
     *  A method that counts the number of parts in a list
     *  It's initialized to 1 and then iterates through all the parts in the list
     *  Checks if the part's ID is greater than or equal to the id
     *  Allows for a new and unique ID for each part
     * @return the int
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

    /**
     * Product id counter int.
     * A method that counts the number of products in a list
     * It's initialized to 100 and then iterates through all the products in the list
     * Checks if the product's ID is greater than or equal to the id
     * Allows for a new and unique ID for each product
     * @return the int
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

