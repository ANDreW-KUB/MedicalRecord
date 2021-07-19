package sample.dao;

import javafx.collections.FXCollections;
import sample.model.Patient;
import sample.model.Session;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SessionDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Session> getListSession(Patient patient) {
        String query = "SELECT * FROM Session WHERE fk_patient=" +patient.getId_patient();
        List<Session> sessionList = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Session session = new Session();
                session.setId_session(resultSet.getInt("id_session"));
                session.setSession_type(resultSet.getString("session_type"));
                session.setStart_date(resultSet.getString("start_date"));
                session.setDuration(resultSet.getInt("duration"));
                session.setEfficiency(resultSet.getString("efficiency"));
                session.setSession_number(resultSet.getString("session_number"));
                sessionList.add(session);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sessionList;
    }

    public void create(Session session, int fk_patient, int size) {
        String sql = "INSERT INTO Session(session_type, start_date, duration, efficiency, " +
                " fk_patient ,session_number)  VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, session.getSession_type());
            stmt.setString(2, session.getStart_date());
            stmt.setInt(3, session.getDuration());
            stmt.setString(4, session.getEfficiency()+"%");
            stmt.setInt(5, fk_patient);
            stmt.setString(6, "#"+String.valueOf(size+1));
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void edit(Session session, int fk_patient, int size) {
        String sql = "UPDATE Session SET session_type=?, start_date=?, duration=? , efficiency=?" +
                " WHERE id_session=? AND fk_patient = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, session.getSession_type());
            stmt.setString(2, session.getStart_date());
            stmt.setInt(3, session.getDuration());
            stmt.setString(4, session.getEfficiency()+"%");
            stmt.setInt(5, session.getId_session());
            stmt.setInt(6, fk_patient);
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
