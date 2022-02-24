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

public class ModifyProduct implements Initializable {
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
    private Button mAddButton;
    @FXML
    private Button mDeleteButton;
    @FXML
    private Button mCancelButton;
    @FXML
    private Button mSaveButton;
    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partSearchField;

    private final Product selectedProduct = MainScreen.getSelectedProduct();
    ObservableList<Part> associatedPartsList = selectedProduct.getAllAssociatedParts();

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
        System.out.println("Please enter a valid value into the search field.");
    }
    }




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

        if (!repeated) {
            selectedProduct.addAssociatedParts(selectedPart);
        }
    }

    @FXML
    private void onActionDelete(ActionEvent event) {
        if (associatedPartTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing selected");
            alert.setContentText("Nothing was selected for deletion.");
            alert.showAndWait();
        }
        else if (Inventory.getAllParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete models.Part");
            alert.setHeaderText("Deleting the part selected");
            alert.setContentText("Are you sure that you want to delete " + associatedPartTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                selectedProduct.deleteAssociatedParts(associatedPartTable.getSelectionModel().getSelectedItem());

            }
        }
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modification");
        alert.setHeaderText("Return to the JoshJarabek_Inventory screen");
        alert.setContentText("Are you sure that you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            goToMain(event);
        }
    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error has occurred");

            if (Integer.parseInt(prodMaxText.getText()) < Integer.parseInt(prodMinText.getText())) {
                alert.setContentText("Max value must be greater than the minimum value.");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(prodInvText.getText()) > Integer.parseInt(prodMaxText.getText())) {
                alert.setContentText("models.Inventory must be less than the maximum value.");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(prodInvText.getText()) < Integer.parseInt(prodMinText.getText())) {
                alert.setContentText("models.Inventory must be greater than the minimum value.");
                alert.showAndWait();
            }
            else {
                selectedProduct.setId(Integer.parseInt(prodIdText.getText()));
                selectedProduct.setName(prodNameText.getText());
                selectedProduct.setPrice(Double.parseDouble(prodPriceText.getText()));
                selectedProduct.setStock(Integer.parseInt(prodInvText.getText()));
                selectedProduct.setMin(Integer.parseInt(prodMinText.getText()));
                selectedProduct.setMax(Integer.parseInt(prodMaxText.getText()));

                Product modifiedProduct = new Product(selectedProduct.getId(), selectedProduct.getName(), selectedProduct.getPrice(), selectedProduct.getStock(), selectedProduct.getMin(), selectedProduct.getMax());
                Inventory.modifyProduct(selectedProduct.getId(),modifiedProduct);

                for (Part part : associatedPartsList) {
                    modifiedProduct.addAssociatedParts(part);
                }
                goToMain(event);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter valid values.");
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

        Product selectedProduct = MainScreen.getSelectedProduct();
        associatedPartTable.setItems(selectedProduct.getAllAssociatedParts());
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodIdText.setText(Integer.toString(selectedProduct.getId()));
        prodNameText.setText(selectedProduct.getName());
        prodInvText.setText(Integer.toString(selectedProduct.getStock()));
        prodInvText.setText(Integer.toString(selectedProduct.getStock()));
        prodPriceText.setText(Double.toString(selectedProduct.getPrice()));
        prodMaxText.setText(Integer.toString(selectedProduct.getMax()));
        prodMinText.setText(Integer.toString(selectedProduct.getMin()));

    }
}