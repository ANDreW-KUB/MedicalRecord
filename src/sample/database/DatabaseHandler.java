package sample.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseHandler {
    private final String  dbPath = "D:\\ANDreW\\test\\MedicalRecordJavaFX\\src\\sample\\resources\\MedicalRecord.db";

    private Connection connection;

    public Connection getConnection() {
        try {
           connection =  DriverManager.getConnection("jdbc:sqlite:" +dbPath);
            return connection;
        } catch (SQLException e) {
           return null;
        }
    }


    public void disconnect(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
