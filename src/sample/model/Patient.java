package sample.model;

import java.util.Objects;

public class Patient {

    private int id_patient;
    private String given_name;
    private String family_name;
    private  String note;

    public Patient(int id_patient, String given_name, String family_name, String note) {
        this.id_patient = id_patient;
        this.given_name = given_name;
        this.family_name = family_name;
        this.note = note;
    }

    public Patient() {

    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
