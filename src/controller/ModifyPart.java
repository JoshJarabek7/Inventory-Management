package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsource;
import models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {
    @FXML
    private Label mLabel;
    @FXML
    private TextField mLabelText;
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
    private RadioButton mIHRadio;
    @FXML
    private ToggleGroup SourceToggle;
    @FXML
    RadioButton mOSRadio;
    @FXML
    private Button mSaveButton;
    @FXML
    private Button mCancelButton;

    private Part selectedPart = null;

    @FXML
    private void sourceToggle(ActionEvent event) {
        if (mIHRadio.isSelected()) {
            mLabel.setText("Machine ID");
            mLabelText.setPromptText("Mach ID");
        }
        else if (mOSRadio.isSelected()) {
            mLabel.setText("Company Name");
            mLabelText.setPromptText("Comp Name");
        }
    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");

            if (Integer.parseInt(partMaxText.getText()) < Integer.parseInt(partMinText.getText())) {
                alert.setContentText("Maximum value must be greater than the minimum value.");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(partInvText.getText())> Integer.parseInt(partMaxText.getText())) {
                alert.setContentText("models.Inventory must be less than the maximum value.");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(partInvText.getText()) < Integer.parseInt(partMinText.getText()))  {
                alert.setContentText("models.Inventory must be greater than the minimum value.");
                alert.showAndWait();
            }
            else {
                selectedPart.setId(Integer.parseInt(partIdText.getText()));
                selectedPart.setName(partNameText.getText());
                selectedPart.setPrice(Double.parseDouble(partPriceText.getText()));
                selectedPart.setStock(Integer.parseInt(partInvText.getText()));
                selectedPart.setMin(Integer.parseInt(partMinText.getText()));
                selectedPart.setMax(Integer.parseInt(partMaxText.getText()));

                if (mIHRadio.isSelected() & selectedPart instanceof InHouse) {
                    modifyPartInHouse();
                }
                else if (mIHRadio.isSelected() & selectedPart instanceof Outsource) {
                    modifyPartInHouse();
                }
                else if (mOSRadio.isSelected() & selectedPart instanceof Outsource) {
                    modifyPartOutsource();
                }
                else if (mOSRadio.isSelected() & selectedPart instanceof InHouse) {
                    modifyPartOutsource();
                }
                goToMain(event);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter valid values in the fields.");
        }

    }

    private void modifyPartInHouse() {
        Part inHousePart = new InHouse(selectedPart.getId(),selectedPart.getName(),selectedPart.getPrice(),selectedPart.getStock(),selectedPart.getMin(),selectedPart.getMax(),Integer.parseInt(mLabelText.getText()));

        Inventory.modifyPart(selectedPart.getId(),inHousePart);
    }

    private void modifyPartOutsource() {
        Part outsourcePart = new Outsource(selectedPart.getId(),selectedPart.getName(),selectedPart.getPrice(),selectedPart.getStock(),selectedPart.getMin(),selectedPart.getMax(),mLabelText.getText());

        Inventory.modifyPart(selectedPart.getId(),outsourcePart);

    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modify models.Part");
        alert.setHeaderText("Return to Main");
        alert.setContentText("Are you sure you want to cancel?");
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
        selectedPart = MainScreen.getSelectedPart();
        if (selectedPart instanceof InHouse) {
            mIHRadio.setVisible(true);
            mIHRadio.setSelected(true);
            mLabel.setText("Machine ID");
            mLabelText.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }
        else if (selectedPart instanceof Outsource) {
            mOSRadio.setVisible(true);
            mOSRadio.setVisible(true);
            mLabel.setText(((Outsource)selectedPart).getCompanyName());
        }
        partIdText.setText(Integer.toString(selectedPart.getId()));
        partNameText.setText(selectedPart.getName());
        partInvText.setText(Integer.toString(selectedPart.getStock()));
        partPriceText.setText(Double.toString(selectedPart.getPrice()));
        partMaxText.setText(Integer.toString(selectedPart.getMax()));
        partMinText.setText(Integer.toString(selectedPart.getMin()));
    }

}

























