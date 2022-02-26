package controller;

import javafx.collections.FXCollections;
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
 * Creates a new instance of the AddProduct class
 */

public class AddProduct implements Initializable {

    /**
     * Functions for the "Add Product" screen
     */

    /**
     * The Prod id text.
     */
    @FXML
    private TextField prodIdText;

    /**
     * The Prod name text.
     */
    @FXML
    private TextField prodNameText;

    /**
     * The Prod inv text.
     */
    @FXML
    private TextField prodInvText;

    /**
     * The Prod price text.
     */
    @FXML
    private TextField prodPriceText;

    /**
     * The Prod max text.
     */
    @FXML
    private TextField prodMaxText;

    /**
     * The Prod min text.
     */
    @FXML
    private TextField prodMinText;

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
     * The Associated part table.
     */
    @FXML
    private TableView<Part> associatedPartTable;

    /**
     * The Associated part id col.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    /**
     * The Associated part name col.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    /**
     * The Associated part inv col.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;

    /**
     * The Associated part price col.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    /**
     * The Associated add button.
     */
    @FXML
    private Button associatedAddButton;

    /**
     * The Associated delete button.
     */
    @FXML
    private Button associatedDeleteButton;

    /**
     * The Associated cancel button.
     */
    @FXML
    private Button associatedCancelButton;

    /**
     * The Associated save button.
     */
    @FXML
    private Button associatedSaveButton;

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
     * The Associated parts list.
     *
     * Declares a variable of type ObservableList and assigns it to the variable associatedPartsList
     */

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();


    /**
     * On action add.
     * <p>
     * The method for what happens when the "Add" button is clicked
     * <p>
     * Functionality for adding associated parts to the product being added
     * If no parts are selected, it will return
     * If parts are selected, they will be added to the associated parts list
     *
     * @param event the event
     */

    @FXML
    private void onActionAdd(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        boolean repeated = false;
        if (selectedPart == null) {
            return;
        }
        else {
            int id = selectedPart.getId();
            for (Part part : associatedPartsList) {
                if (part.getId() == id) {
                    repeated = true;
                    break;
                }
            }
        }

        if(!repeated) {
            associatedPartsList.add(selectedPart);

        }
    }


    /**
     * On action delete.
     * <p>
     * The method for what happens when the "Remove Associated Part" button is clicked
     * <p>
     * If nothing is selected, an error alert will be shown to the user
     * If a part is selected, a confirmation alert will be shown where the user can confirm their decision
     *
     * @param event the event
     */

    @FXML
    private void onActionDelete(ActionEvent event) {

        // Error Alert shown when nothing has been selected
        if (associatedPartTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing selected to delete.");
            alert.showAndWait();
        }

        // Confirmation Alert shown when an item is selected
        else if (Inventory.getAllParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete models.Part");
            alert.setHeaderText("Deleting models.Part");
            alert.setContentText("Are you sure that you want to delete this part " + associatedPartTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                // If confirmed, the part will be removed from the associated parts list
                associatedPartsList.remove(associatedPartTable.getSelectionModel().getSelectedItem());

            }
        }
    }

    /**
     * On action save.
     * <p>
     * The method for what happens when the "Save" button is clicked
     * <p>
     * Error alerts set in place for:
     * 1. The product's maximum inventory entered is less than the product's minimum inventory entered.
     * 2. The product's current inventory entered is greater than the product's maximum inventory entered.
     * 3. The product's current inventory entered is less than the product's minimum inventory entered.
     * 4. Values entered are not valid (ex. Text was entered in an integer only field).
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");

            // Maximum inventory entered is less than the minimum inventory entered
            if (Integer.parseInt(prodMaxText.getText()) < Integer.parseInt(prodMinText.getText())) {
                alert.setContentText("Maximum value must be greater than the minimum value.");
                alert.showAndWait();
                return;
            }
            // Current inventory entered is greater than the maximum inventory entered
            if (Integer.parseInt(prodInvText.getText()) > Integer.parseInt(prodMaxText.getText())) {
                alert.setContentText("Inventory value must be less than the maximum value.");
                alert.showAndWait();
                return;
            }
            // Current inventory entered is less than the minimum inventory entered
            if (Integer.parseInt(prodInvText.getText()) < Integer.parseInt(prodMinText.getText())) {
                alert.setContentText("Inventory value must be greater than the minimum value.");
                alert.showAndWait();
            }

            // If no errors are thrown, the new product and its associated parts are added to the inventory
            else {
                Product p = new Product(Inventory.productIdCounter(), prodNameText.getText(), Double.parseDouble(prodPriceText.getText()), Integer.parseInt(prodInvText.getText()),
                        Integer.parseInt(prodMinText.getText()), Integer.parseInt(prodMaxText.getText()));
                Inventory.addProduct(p);

                for (Part part : associatedPartsList) {
                    p.addAssociatedParts(part);
                }

                // goToMain method is called and the user is returned to the MainScreen
                goToMain(event);
            }
        }
        // Exception thrown if the values entered are not valid
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing searched");
            alert.setContentText("Please enter a valid value in the search bar");
            alert.showAndWait();
        }
    }

    /**
     * Search parts.
     * <p>
     * The method for what happens when the search button is clicked
     * <p>
     * The method is a search function for parts
     * The purpose of the code is to search for parts in the inventory that contain a string value of "s"
     * When the search button is clicked, a search is done on the text within the partSearchField
     *
     * @param event the event
     */
    @FXML
    private void searchParts(ActionEvent event) {
        String s = partSearchField.getText().toLowerCase();
        ObservableList<Part> partsList = Inventory.lookupPart(s);
        try {
            if (partsList.size() == 0) {
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
     * On action cancel.
     * <p>
     * The method for what happens when the cancel button is clicked
     * <p>
     * Asks the user if they want to cancel adding the product to the inventory
     * A confirmation alert is thrown asking the user to confirm the cancellation
     * If the user clicks "OK" on the confirmation alert, they are taken back to MainScreen
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Product");
        alert.setHeaderText("Return to Main Screen");
        alert.setContentText("Are you sure that you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            goToMain(event);
        }
    }


    /**
     * Go to main.
     * <p>
     * The method for what happens when the goToMain method is actioned
     * <p>
     * The MainScreen view is loaded into a Node object and is passed to the goToMain() method
     * This sends the user back to the MainScreen
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void goToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main Screen.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Initialize Table
     *
     * Initializes the table with a list of parts and their associated information
     * The code sets the cell value factories for each column in the table to use PropertyValueFactory<> objects
     * The initialize method is called when the activity starts up, so it's called before any other methods
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

        associatedPartTable.setItems(associatedPartsList);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}