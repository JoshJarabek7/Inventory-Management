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

public class AddProduct implements Initializable {
    @FXML
    private TextField prodIdText;
    @FXML
    private TextField prodNameText;
    @FXML
    private TextField prodInvText;
    @FXML
    private TextField prodPriceText;
    @FXML
    private TextField prodMaxText;
    @FXML
    private TextField prodMinText;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;
    @FXML
    private Button associatedAddButton;
    @FXML
    private Button associatedDeleteButton;
    @FXML
    private Button associatedCancelButton;
    @FXML
    private Button associatedSaveButton;
    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partSearchField;

    private final ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

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

    @FXML
    private void onActionDelete(ActionEvent event) {
        if (associatedPartTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Nothing selected to delete.");
            alert.showAndWait();
        } else if (Inventory.getAllParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete models.Part");
            alert.setHeaderText("Deleting models.Part");
            alert.setContentText("Are you sure that you want to delete this part " + associatedPartTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                associatedPartsList.remove(associatedPartTable.getSelectionModel().getSelectedItem());

            }
        }
    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");

            if (Integer.parseInt(prodMaxText.getText()) < Integer.parseInt(prodMinText.getText())) {
                alert.setContentText("Maximum value must be greater than the minimum value.");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(prodInvText.getText()) > Integer.parseInt(prodMaxText.getText())) {
                alert.setContentText("models.Inventory value must be less than the maximum value.");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(prodInvText.getText()) < Integer.parseInt(prodMinText.getText())) {
                alert.setContentText("models.Inventory value must be greater than the minimum value.");
                alert.showAndWait();
            }
            else {
                Product p = new Product(Inventory.productIdCounter(), prodNameText.getText(), Double.parseDouble(prodPriceText.getText()), Integer.parseInt(prodInvText.getText()),
                        Integer.parseInt(prodMinText.getText()), Integer.parseInt(prodMaxText.getText()));
                Inventory.addProduct(p);

                for (Part part : associatedPartsList) {
                    p.addAssociatedParts(part);
                }
                goToMain(event);
            }
        }
        catch (NumberFormatException e){ System.out.println("Please enter valid value into the field."); }
    }

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
        catch (NumberFormatException e){ System.out.println("Please enter a valid value into the search bar."); }
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding models.Product");
        alert.setHeaderText("Return to Main Screen");
        alert.setContentText("Are you sure that you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            goToMain(event);
        }
    }





    public void goToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("src/views/Main Screen.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.show();
    }

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