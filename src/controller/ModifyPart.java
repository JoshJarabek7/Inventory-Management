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


// Creates a new instance of the ModifyPart class
public class ModifyPart implements Initializable {

    // The functions for the "Modify Part" screen

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


    // Sets selectedPart's value to null
    private Part selectedPart = null;

    /*
    The method for what happens when each radio button is clicked

    If the In-House radio button is clicked:
    The Label is set to "Machine ID" and the Prompt Text in the Text Field is set to "Mach ID"

    If the OutSourced radio button is clicked:
    The Label is set to "Company Name" and the Prompt Text in the Text Field is set to "Company Name"
     */
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

    /*
    The method for what happens when the "Save" button is clicked

    Error alerts set in place for:
    1. The part's maximum inventory entered is less than the part's minimum inventory entered.
    2. The part's current inventory entered is greater than the part's maximum inventory entered.
    3. The part's current inventory entered is less than the part's minimum inventory entered.
    4. Values entered are not valid (ex. Text was entered in an integer only field).

    If the In-House radio button is clicked, the corresponding fields are loaded for In-House parts.
    If the Outsourced radio button is clicked, the corresponding fields are loaded for Outsourced parts.

    If no alerts are triggered after clicking save, the parts are then saved and the user is returned to the Main Screen.
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");

            // Maximum inventory entered is less than the minimum inventory entered
            if (Integer.parseInt(partMaxText.getText()) < Integer.parseInt(partMinText.getText())) {
                alert.setContentText("Maximum value must be greater than the minimum value.");
                alert.showAndWait();
                return;
            }
            // Current inventory entered is greater than the maximum inventory entered
            if (Integer.parseInt(partInvText.getText())> Integer.parseInt(partMaxText.getText())) {
                alert.setContentText("models.Inventory must be less than the maximum value.");
                alert.showAndWait();
                return;
            }
            // Current inventory entered is less than the minimum inventory entered
            if (Integer.parseInt(partInvText.getText()) < Integer.parseInt(partMinText.getText()))  {
                alert.setContentText("models.Inventory must be greater than the minimum value.");
                alert.showAndWait();
            }
            // Checks which radio button is selected, and creates a new instance and modifies it
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

                // User is taken back to MainScreen
                goToMain(event);

            }
        }

        // Exception thrown if the values entered are not valid
        catch (NumberFormatException e) {
            System.out.println("Please enter valid values in the fields.");
        }

    }

    // Modifications for In-House parts are saved
    private void modifyPartInHouse() {
        Part inHousePart = new InHouse(selectedPart.getId(),selectedPart.getName(),selectedPart.getPrice(),selectedPart.getStock(),selectedPart.getMin(),selectedPart.getMax(),Integer.parseInt(mLabelText.getText()));

        Inventory.modifyPart(selectedPart.getId(),inHousePart);
    }

    // Modifications for Outsourced parts are saved
    private void modifyPartOutsource() {
        Part outsourcePart = new Outsource(selectedPart.getId(),selectedPart.getName(),selectedPart.getPrice(),selectedPart.getStock(),selectedPart.getMin(),selectedPart.getMax(),mLabelText.getText());

        Inventory.modifyPart(selectedPart.getId(),outsourcePart);

    }

    /*
    The method for what happens when the "Cancel" button is clicked

    Asks the user if they want to cancel adding a part to the inventory system
    If the user clicks "OK", then the user is taken back to MainScreen
     */
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

    /*
    The method for what happens when the goToMain method is actioned

    The MainScreen view is loaded into a Node object and is passed to the goToMain() method
    This sends the application back to the MainScreen
     */
    public void goToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main Screen.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /*
    Initializes the radio buttons and text fields to show information about a selectedPart
    The method is called when the application starts up, before any other methods are called
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainScreen.getSelectedPart();

        // If the part being modified is sourced In-House, the "Machine ID" label is shown
        if (selectedPart instanceof InHouse) {
            mIHRadio.setVisible(true);
            mIHRadio.setSelected(true);
            mLabel.setText("Machine ID");
            mLabelText.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }

        // If the part being modified is Outsourced, the "Company Name" label is shown
        else if (selectedPart instanceof Outsource) {
            mOSRadio.setVisible(true);
            mOSRadio.setSelected(true);
            mLabel.setText(((Outsource)selectedPart).getCompanyName());
        }

        // The 6 other fields that are the same regardless of sourcing
        partIdText.setText(Integer.toString(selectedPart.getId()));
        partNameText.setText(selectedPart.getName());
        partInvText.setText(Integer.toString(selectedPart.getStock()));
        partPriceText.setText(Double.toString(selectedPart.getPrice()));
        partMaxText.setText(Integer.toString(selectedPart.getMax()));
        partMinText.setText(Integer.toString(selectedPart.getMin()));
    }

}

























