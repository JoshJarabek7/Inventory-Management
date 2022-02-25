package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsource;
import models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPart implements Initializable {
    @FXML
    private AnchorPane IHRadio;
    @FXML
    private Label aLabel;
    @FXML
    private TextField aLabelText;
    @FXML
    private TextField partIdText;
    @FXML
    private TextField partNameText;
    @FXML
    private TextField partInvText;
    @FXML
    private TextField partPriceText;
    @FXML
    private TextField partMaxText;
    @FXML
    private TextField partMinText;
    @FXML
    private RadioButton aIHRadio;
    @FXML
    private ToggleGroup SourceToggle;
    @FXML
    private RadioButton aOSRadio;
    @FXML
    private Button aSaveButton;
    @FXML
    private Button aCancelButton;
    @FXML
    private void sourceToggle(ActionEvent event) {
        if (aIHRadio.isSelected()) {
            aLabelText.setVisible(true);
            aLabel.setText("Machine ID");
            aLabelText.setPromptText("Mach ID");
        }
        else if (aOSRadio.isSelected()) {
            aLabel.setText("Company Name");
            aLabelText.setPromptText("Company Name");
        }

    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");


            if (Integer.parseInt(partMaxText.getText()) < Integer.parseInt(partMinText.getText())) {
                alert.setContentText("Max value must be greater than the minimum");
                alert.showAndWait();
                return;
            }

            if (Integer.parseInt(partInvText.getText()) > Integer.parseInt(partMaxText.getText())) {
                alert.setContentText("Inventory value must be less than the maximum");
                alert.showAndWait();
                return;
            }

            if (Integer.parseInt(partInvText.getText()) < Integer.parseInt(partMinText.getText())) {
                alert.setContentText("Inventory value must be greater than the minimum");
                alert.showAndWait();
                return;
            }

            if (!aIHRadio.isSelected() && !aOSRadio.isSelected()) {
                alert.setContentText("Please click either InHouse or Outsourced.");
                alert.showAndWait();
                return;
            }

            if (aIHRadio.isSelected()) {
                Part p = new InHouse(Inventory.partIdCounter(), partNameText.getText(), Double.parseDouble(partPriceText.getText()), Integer.parseInt(partInvText.getText()), Integer.parseInt(partMinText.getText()), Integer.parseInt(partMaxText.getText()), Integer.parseInt(aLabelText.getText()));
                Inventory.addPart(p);
            } else if (aOSRadio.isSelected()) {
                Part p = new Outsource(Inventory.partIdCounter(), partNameText.getText(), Double.parseDouble(partPriceText.getText()), Integer.parseInt(partInvText.getText()), Integer.parseInt(partMinText.getText()), Integer.parseInt(partMaxText.getText()), aLabelText.getText());
                Inventory.addPart(p);
            }

            goToMain(event);
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid values in text box.");
        }

    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Part");
        alert.setHeaderText("Return to Main Screen");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            goToMain(event);
        }

    }

    public void goToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main Screen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        //TODO

    }
}