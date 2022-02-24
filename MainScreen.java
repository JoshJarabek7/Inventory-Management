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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

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
    private TableView<Product> prodTable;
    @FXML
    private TableColumn<Product, Integer> prodIdCol;
    @FXML
    private TableColumn<Product, String> prodNameCol;
    @FXML
    private TableColumn<Product, Integer> prodInvCol;
    @FXML
    private TableColumn<Product, Double> prodPriceCol;
    @FXML
    private Button addPart;
    @FXML
    private Button modifyPart;
    @FXML
    private Button deletePart;
    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partSearchField;
    @FXML
    private Button addProduct;
    @FXML
    private Button modifyProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button productSearchButton;
    @FXML
    private TextField productSearchField;

    private static Part selectedPart = null;
    private static Product selectedProduct = null;

    public static Part getSelectedPart() {
        return selectedPart;
    }

    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    @FXML
    private void onAddPart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("controllerview/Add Part.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Part");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void onAddProduct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("controllerview/Add Product.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root));
        stage.show();
    }

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
            alert.setTitle("Delete Part");
            alert.setHeaderText("Deleting Part");
            alert.setContentText("Are you sure you want to delete " + partTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
            }
        }
    }

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

    @FXML
    private void onActionExit(ActionEvent event) {
        System.exit(0);
    }

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
            Parent root = FXMLLoader.load(getClass().getResource("controllerview/Modify Part.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

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
            Parent root = FXMLLoader.load(getClass().getResource("controllerview/Modify Product.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

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
            System.out.println("Please enter a valid value in the search bar.");
        }
    }

    @FXML
    private void searchProducts(ActionEvent event) {
        String s = productSearchField.getText().toLowerCase();
        ObservableList<Product> ProductsList = Inventory.lookupProduct(s);
        try {
            if (ProductsList.isEmpty()) {
                int i = Integer.parseInt(s);
                Product p = Inventory.lookupProduct(i);
                ProductsList.add(p);
            }
            prodTable.setItems(ProductsList);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid value in the search bar.");
        }
    }

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
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}