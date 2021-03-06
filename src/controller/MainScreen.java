package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Inventory;
import models.Part;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Josh Jarabek
 */


/**
 * The type Main screen.
 * Defines the MainScreen class, which is a controller for the Main Menu screen of the application
 */
public class MainScreen implements Initializable {



    /**
     * The functions for the MainScreen
     *
    The code starts with a private variable called partTable
    The table is used to display all the parts within the inventory management application
    It also includes 3 button which are "Add Part", "Modify Part", and "Delete Part"
    It also includes 3 variables that allow for a search feature

    The same thing is done for the prodTable to display all the products within the inventory management application
    Each column in the two tables represents different attributes for each part or product
    These attributes are things like ID Numbers, Names, Amount in Inventory, and Price divided by Cost per Unit
     */

    /**
     * The Part table.
     */
    @FXML
    private TableView<Part> partTable;

    /**
     * The Part id col.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /**
     * The Part name col.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * The Part inv col.
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;

    /**
     * The Part price col.
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /**
     * The Prod table.
     */
    @FXML
    private TableView<Product> prodTable;

    /**
     * The Prod id col.
     */
    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    /**
     * The Prod name col.
     */
    @FXML
    private TableColumn<Product, String> prodNameCol;

    /**
     * The Prod inv col.
     */
    @FXML
    private TableColumn<Product, Integer> prodInvCol;

    /**
     * The Prod price col.
     */
    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    /**
     * The Add part.
     */
    @FXML
    private Button addPart;

    /**
     * The Modify part.
     */
    @FXML
    private Button modifyPart;

    /**
     * The Delete part.
     */
    @FXML
    private Button deletePart;

    /**
     * The Part search button.
     */
    @FXML
    private Button partSearchButton;

    /**
     * The Part search field.
     */
    @FXML
    private TextField partSearchField;

    /**
     * The Add product.
     */
    @FXML
    private Button addProduct;

    /**
     * The Modify product.
     */
    @FXML
    private Button modifyProduct;

    /**
     * The Delete product.
     */
    @FXML
    private Button deleteProduct;

    /**
     * The Product search button.
     */
    @FXML
    private Button productSearchButton;

    /**
     * The Product search field.
     */
    @FXML
    private TextField productSearchField;


    /**
     * The constant selectedPart.
     * Assigns a null value to the selectedPart variable
     */

    private static Part selectedPart = null;

    /**
     * The constant selectedProduct.
     * Assigns a null value to the selectedProduct variable
     */

    private static Product selectedProduct = null;


    /**
     * Gets selected part.
     * Getter to return selectedPart
     *
     * @return the selected part
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     * Gets selected product.
     * Getter to return selectedProduct
     *
     * @return the selected product
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }


    /**
     * On add part.
     * <p>
     * Add parts to inventory
     * Loads the Add Part screen from the fxml file
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    private void onAddPart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Add Part.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Part");
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * On add product.
     * <p>
     * Add products to inventory
     * Loads the Add Product screen from the fxml file
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    private void onAddProduct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Add Product.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * On delete part.
     * <p>
     * Delete parts from inventory
     * Checks the part table to see if any items are selected
     * If no items are selected, then an alert is shown saying "Nothing was selected to delete"
     * If an item is selected, then an alert is shown asking to confirm their decision
     * If decision is confirmed, the part will be deleted from inventory
     *
     * @param event the event
     */
    @FXML
    private void onDeletePart(ActionEvent event) {
        if (partTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing was selected to delete");
            alert.showAndWait();
        } else if (Inventory.getAllParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete models.Part");
            alert.setHeaderText("Deleting Part");
            alert.setContentText("Are you sure you want to delete " + partTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
            }
        }
    }


    /**
     * On delete product.
     * <p>
     * Delete products from inventory
     * <p>
     * Checks the product table to see if any items are selected
     * If no items are selected, then an alert is shown saying "Nothing was selected to delete"
     * If an item is selected, then an alert is shown asking to confirm their decision
     * If decision is confirmed, the product will be deleted from inventory
     *
     * @param event the event
     */
    @FXML
    void onDeleteProduct(ActionEvent event) {
        if (prodTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing was selected to delete");
            alert.showAndWait();
        } else if (Inventory.getAllParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Deleting Product");
            alert.setContentText("Are you sure you want to delete " + prodTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                Inventory.deleteProduct(prodTable.getSelectionModel().getSelectedItem());
            }
        }
    }


    /**
     * On action exit.
     * <p>
     * Exit the application
     * <p>
     * This code is used to exit the application when a user presses the "X" button
     *
     * @param event the event
     */
    @FXML
    private void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * On modify part.
     * <p>
     * Modify parts in inventory
     * <p>
     * This code determines what happens when the "Modify Part" button is clicked
     * If no part is selected when clicked, then an Error Alert is shown
     * If a part is selected when clicked, then a new scene object is created and set as the stage for the event
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    private void onModifyPart(ActionEvent event) throws IOException {
        selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing was selected to modify");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Modify Part.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * On modify product.
     * <p>
     * Modify products in inventory
     * <p>
     * This code determines what happens when the "Modify Product" button is clicked
     * If no product is selected when clicked, then an Error Alert is shown
     * If a product is selected when clicked, then a new scene object is created and set as the stage for the event
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    private void onModifyProduct(ActionEvent event) throws IOException {
        selectedProduct = prodTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing was selected to modify");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Modify Product.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Search parts.
     * <p>
     * Search for parts in inventory
     * <p>
     * The code starts by searching for the text entered in the search bar
     * If there is no text, it will prompt the user to enter a value
     * After that, it searches for parts using the ObservableList, and adds them to the partTable if they are not already present.
     *
     * @param event the event
     */
    @FXML
    private void searchParts(ActionEvent event) {
        String s = partSearchField.getText().toLowerCase();
        ObservableList<Part> partsList = Inventory.lookupPart(s);
        try {
            if (partsList.isEmpty()) {
                int i = Integer.parseInt(s);
                Part p = Inventory.lookupPart(i);
                partsList.add(p);
            }
            partTable.setItems(partsList);
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing searched");
            alert.setContentText("Please enter a valid value in the search bar");
            alert.showAndWait();
        }
    }

    /**
     * Search products.
     * <p>
     * Search for products in inventory
     * <p>
     * The code starts by searching for the text entered in the search bar
     * If there is no text, it will prompt the user to enter a value
     * After that, it searched for products using the ObservableList, and adds them to the productTable if they are not already present.
     *
     * @param event the event
     */
    @FXML
    private void searchProducts(ActionEvent event) {
        String st = productSearchField.getText().toLowerCase();
        ObservableList<Product> productsList = Inventory.lookupProduct(st);
        try {
            if (productsList.isEmpty()) {
                int i = Integer.parseInt(st);
                Product pr = Inventory.lookupProduct(i);
                productsList.add(pr);
            }
            prodTable.setItems(productsList);
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing searched");
            alert.setContentText("Please enter a valid value in the search bar");
            alert.showAndWait();
        }
    }

    /**
     * Initialize Tables
     *
     * The code starts by initializing the table with all parts and products
     * The partTable is initialized with Inventory.getAllParts(), which returns a list of Part objects, while prodTable is initialized with Inventory.getAllProducts()
     * The Columns are set to be a PropertyValueFactory<> that will return a  value for each row in the table
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTable.setItems(Inventory.getAllProducts());
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}