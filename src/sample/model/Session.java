package sample.model;

import java.util.Date;

public class Session {

    private int id_session;
    private String session_number;
    private Patient fk_patient;
    private String session_type;
    private String start_date;
    private int duration;
    private String efficiency;


    public int getId_session() {
        return id_session;
    }

    public void setId_session(int id_session) {
        this.id_session = id_session;
    }

    public Patient getFk_patient() {
        return fk_patient;
    }

    public void setFk_patient(Patient fk_patient) {
        this.fk_patient = fk_patient;
    }

    public String getSession_type() {
        return session_type;
    }

    public void setSession_type(String session_type) {
        this.session_type = session_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }

    public String getSession_number() {
        return session_number;
    }

    public void setSession_number(String session_number) {
        this.session_number = session_number;
    }
}
