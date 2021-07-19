package sample.dao;


import javafx.collections.FXCollections;
import sample.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PatientDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public List<Patient> getListPatient() {
        String query = "SELECT * FROM Patient";
        List<Patient> patientList = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId_patient(resultSet.getInt("id_patient"));
                patient.setGiven_name(resultSet.getString("given_name"));
                patient.setFamily_name(resultSet.getString("family_name"));
                patient.setNote(resultSet.getString("note"));
                patientList.add(patient);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return patientList;
    }

    public void create(Patient patient) {
        String sql = "INSERT INTO Patient(given_name, family_name, note) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, patient.getGiven_name());
            stmt.setString(2, patient.getFamily_name());
            stmt.setString(3, patient.getNote());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void edit(Patient patient) {
        String sql = "UPDATE Patient SET given_name=?, family_name=?, note=? WHERE id_patient=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setString(1, patient.getGiven_name());
            stmt.setString(2, patient.getFamily_name());
            stmt.setString(3, patient.getNote());
            stmt.setInt(4, patient.getId_patient());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Patient patient) {
        String sql = "DELETE FROM Patient WHERE id_patient=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, patient.getId_patient());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
