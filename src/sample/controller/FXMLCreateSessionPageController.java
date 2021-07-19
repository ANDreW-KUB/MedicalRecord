package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Session;
import sample.model.SessionType;

public class FXMLCreateSessionPageController {

    @FXML
    private Label labelSessionDuration;

    @FXML
    private Label labelPatientNote;

    @FXML
    private TextField textFieldSessionDate;

    @FXML
    private TextField textFieldSessionDuration;

    @FXML
    private TextField textFieldSessionEfficiency;

    @FXML
    private Label labelPatientName;

    @FXML
    private Label labelPatientDate;

    @FXML
    private Label labelPatientSessionType;

    @FXML
    private TextField textFieldSessionType;

    @FXML
    private Button buttonCreate;

    @FXML
    private Button buttonCancel;

    private Stage dialogStage;
    private boolean buttonCreateClicked = false;
    private Session session;

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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
        this.textFieldSessionDate.setText(session.getStart_date());
        this.textFieldSessionDuration.setText(String.valueOf(session.getDuration()));
        this.textFieldSessionEfficiency.setText(String.valueOf(session.getEfficiency()));
        this.textFieldSessionType.setText(session.getSession_type());
    }

    @FXML
    void handleButtonCreate() {
        if (isValidSession()) {

            session.setStart_date(textFieldSessionDate.getText());
            session.setDuration(Integer.parseInt(textFieldSessionDuration.getText()));
            session.setEfficiency(textFieldSessionEfficiency.getText());
            session.setSession_type(textFieldSessionType.getText());

            buttonCreateClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    void handleButtonCancel(ActionEvent event) {
        dialogStage.close();
    }

    private boolean isValidSession() {
        String errorMessage = "";
        if (textFieldSessionDate.getText() == null ||
                !textFieldSessionDate.getText().matches("\\d{2}.\\d{2}.\\d{2}\\s\\d{2}:\\d{2}")) {
            errorMessage += "Invalid date!\n" + "Format: (DD.MM.YY HH:MM)\n";
        }

        if (textFieldSessionDuration.getText() == null
                || Integer.parseInt(textFieldSessionDuration.getText()) <= 0
                || textFieldSessionDuration.getText().length() == 0
                || !textFieldSessionDuration.getText().matches("[0-9]+")) {
            errorMessage += "Invalid duration!\n";
        }

        if (textFieldSessionEfficiency.getText() == null
                || Double.parseDouble(textFieldSessionEfficiency.getText()) <= 0
                || Double.parseDouble(textFieldSessionEfficiency.getText()) > 100
                || textFieldSessionEfficiency.getText().length() == 0) {
            errorMessage += "Invalid efficiency!\n" + "Correct: [0-100)\n";
        }

        if (textFieldSessionType.getText() == null
                || (!textFieldSessionType.getText().equals(SessionType.PLAY.getType())
                && !textFieldSessionType.getText().equals(SessionType.WATCHSLIDES.getType())
                && !textFieldSessionType.getText().equals(SessionType.WATCHVIDE.getType()))) {
            errorMessage += "Invalid SessionType!\n" +
                    "Type: watch video, watch slides, play";
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
