package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dao.PatientDAO;
import sample.dao.SessionDAO;
import sample.database.DatabaseHandler;
import sample.model.Patient;
import sample.model.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLMainPageController implements Initializable {

    @FXML
    private Button buttonPatientCreate;

    @FXML
    private Button buttonPatientEdit;

    @FXML
    private Button buttonPatientRemove;

    @FXML
    private Button buttonSessionAdd;

    @FXML
    private Button buttonSessionEdit;

    @FXML
    private Button buttonClose;

    @FXML
    private TableView<Patient> tablePatient;

    @FXML
    private TableColumn<Patient, String> tableColumnPatientName;

    @FXML
    private TableColumn<Patient, String> tableColumnPatientSurname;

    @FXML
    private TableView<Session> tableSession;

    @FXML
    private TableColumn<?, ?> tableColumnSessionNumber;

    @FXML
    private TableColumn<?, ?> tableColumnSessionDate;

    @FXML
    private TableColumn<?, ?> tableColumnSessionDuration;

    @FXML
    private TableColumn<?, ?> tableColumnSessionEfficincy;

    private Patient pickPatient;
    private List<Patient> listPatient;
    private ObservableList<Patient> observableListPatient;
    private List<Session> listSession;
    private ObservableList<Session> observableListSession;

    private final PatientDAO patientDAO = new PatientDAO();
    private final SessionDAO sessionDAO = new SessionDAO();

    private final DatabaseHandler database = new DatabaseHandler();
    private final Connection connection = database.getConnection();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientDAO.setConnection(connection);
        sessionDAO.setConnection(connection);

        showObservableListPatient();

        tableSession.managedProperty().bind(tableSession.visibleProperty());
        tableSession.setVisible(false);
    }

    public void showObservableListPatient() {
        tableColumnPatientName.setCellValueFactory(new PropertyValueFactory<>("given_name"));
        tableColumnPatientSurname.setCellValueFactory(new PropertyValueFactory<>("family_name"));

        listPatient = patientDAO.getListPatient();

        observableListPatient = FXCollections.observableArrayList(listPatient);
        tablePatient.setItems(observableListPatient);


    }

    public void showObservableListSession(Patient patient) {
        tableColumnSessionNumber.setCellValueFactory(new PropertyValueFactory<>("session_number"));
        tableColumnSessionDate.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        tableColumnSessionDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tableColumnSessionEfficincy.setCellValueFactory(new PropertyValueFactory<>("efficiency"));

        listSession = sessionDAO.getListSession(patient);

        observableListSession = FXCollections.observableArrayList(listSession);
        tableSession.setItems(observableListSession);
    }


    @FXML
    public void handleButtonCreatPatient() throws IOException {
        Patient patient = new Patient();
        tableSession.setVisible(false);
        boolean buttonCreatClicked = showFXMLCreatePatientPageController(patient);
        if (buttonCreatClicked) {
            patientDAO.create(patient);
            showObservableListPatient();
        }
    }

    @FXML
    public void handleButtonEditPatient() throws IOException {
        Patient patient = tablePatient.getSelectionModel().getSelectedItem();
        tableSession.setVisible(false);
        if (patient != null) {
            boolean buttonCreatClicked = showFXMLCreatePatientPageController(patient);
            if (buttonCreatClicked) {
                patientDAO.edit(patient);
                showObservableListPatient();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select one patient from the table!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemovePatient() throws IOException {
        Patient patient = tablePatient.getSelectionModel().getSelectedItem();
        tableSession.setVisible(false);
        if (patient != null) {
            patientDAO.remove(patient);
            showObservableListPatient();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select one patient from the table!");
            alert.show();
        }
    }

    @FXML
    public void handleTablePatient() {
        pickPatient = tablePatient.getSelectionModel().getSelectedItem();
        if (pickPatient != null) {
            tableSession.setVisible(true);
            showObservableListSession(pickPatient);
        } else {
            tableSession.setVisible(false);
        }
    }

    @FXML
    void handleButtonAddSession() throws IOException {
        if (tableSession.isVisible()) {
            Session session = new Session();
            boolean buttonCreatClicked = showFXMLCreateSessionPageController(session);
            System.out.println(buttonCreatClicked);
            if (buttonCreatClicked) {
                System.out.println(pickPatient.getId_patient());
                sessionDAO.create(session, pickPatient.getId_patient(), tableSession.getItems().size());
                showObservableListSession(pickPatient);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select one patient from the table!");
            alert.show();}
    }

    @FXML
    void handleButtonEditSession() throws IOException {
        Session session = tableSession.getSelectionModel().getSelectedItem();
        if (session != null) {
            boolean buttonCreatClicked = showFXMLCreateSessionPageController(session);
            if (buttonCreatClicked) {
                sessionDAO.edit(session, pickPatient.getId_patient(), tableSession.getItems().size());
                showObservableListSession(pickPatient);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select one session from the table!");
            alert.show();
        }
    }

    @FXML
    void handleButtonCancel(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void handleMainPane() {
        tableSession.setVisible(false);
    }

    public boolean showFXMLCreatePatientPageController(Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCreatePatientPageController.class.getResource("/sample/view/createPatientPage.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Create/Update patient");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLCreatePatientPageController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setPatient(patient);

        dialogStage.showAndWait();

        return controller.isButtonCreateClicked();
    }

    public boolean showFXMLCreateSessionPageController(Session session) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCreateSessionPageController.class.getResource("/sample/view/createSessionPage.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Create/Update session");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLCreateSessionPageController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setSession(session);

        dialogStage.showAndWait();

        return controller.isButtonCreateClicked();

    }


}
