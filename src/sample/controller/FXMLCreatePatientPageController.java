package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.model.Patient;

public class FXMLCreatePatientPageController {

    @FXML
    private Label labelPatientName;

    @FXML
    private Label labelPatientSurname;

    @FXML
    private Label labelPatientNote;

    @FXML
    private TextField textFieldPatientName;

    @FXML
    private TextField textFieldPatientSurname;

    @FXML
    private TextField textFieldPatientNote;

    @FXML
    private Button buttonCreate;

    @FXML
    private Button buttonCancel;

    @FXML
    private TableView<?> tablePatient;

    @FXML
    private TableColumn<?, ?> tableColumnPatientName;

    @FXML
    private TableColumn<?, ?> tableColumnPatientSurname;

    private Stage dialogStage;
    private boolean buttonCreateClicked = false;
    private Patient patient;

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonCreateClicked() {
        return buttonCreateClicked;
    }

    public void setButtonCreateClicked(boolean buttonCreateClicked) {
        this.buttonCreateClicked = buttonCreateClicked;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        this.textFieldPatientName.setText(patient.getGiven_name());
        this.textFieldPatientSurname.setText(patient.getFamily_name());
        this.textFieldPatientNote.setText(patient.getNote());
    }

    @FXML
    void handleButtonCancel() {
        dialogStage.close();
    }

    @FXML
    void handleButtonCreate() {
        if (isValidPatient()) {
            patient.setGiven_name(textFieldPatientName.getText());
            patient.setFamily_name(textFieldPatientSurname.getText());
            patient.setNote(textFieldPatientNote.getText());

            buttonCreateClicked = true;
            dialogStage.close();
        }
    }

    private boolean isValidPatient() {
        String errorMessage = "";
        if (textFieldPatientName.getText() == null || textFieldPatientName.getText().length() <3) {
            errorMessage += "Invalid name!\n";
        }
        if (textFieldPatientSurname.getText() == null || textFieldPatientSurname.getText().length() ==0) {
            errorMessage += "Invalid surname!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid fields, correct...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
