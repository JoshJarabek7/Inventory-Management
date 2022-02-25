package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Product.
 */
// Creates a class called Product
public class Product {

    //Declares each variable, as well as a private ObservableList called associatedParts
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int inv;
    private int min;
    private int max;

    /**
     * Instantiates a new Product.
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param inv   the inv
     * @param min   the min
     * @param max   the max
     */
// Defines the Product method for getters and setters
    public Product(int id, String name, double price, int inv, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.min = min;
        this.max = max;
    }

    /**
     * Add associated parts.
     *
     * @param part the part
     */
// Method that adds a new part to the list of associated parts
    public void addAssociatedParts(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Delete associated parts boolean.
     *
     * @param selectedAssociatedPart the selected associated part
     * @return the boolean
     */

    public boolean deleteAssociatedParts(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Gets all associated parts.
     *
     * @return the all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {

        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id to set
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {

        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {

        this.price = price;
    }

    /**
     * Gets inv.
     *
     * @return the inventory
     */
    public int getInv() {

        return inv;
    }

    /**
     * Sets inv.
     *
     * @param inv sets as inventory
     */
    public void setInv(int inv) {

        this.inv = inv;
    }

    /**
     * Gets min.
     *
     * @return the min
     */
    public int getMin() {

        return min;
    }

    /**
     * Sets min.
     *
     * @param min sets as minimum
     */
    public void setMin(int min) {

        this.min = min;
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public int getMax() {

        return max;
    }

    /**
     * Sets max.
     *
     * @param max the max to set
     */
    public void setMax(int max) {

        this.max = max;
    }


}