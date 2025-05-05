package com.hdbms.services;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class PatientDoctorService {
    private final String url;
    private final String user = "root";
    private final String password;

    public PatientDoctorService() {
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.password = dotenv.get("DB_PASSWORD");
    }

    public boolean addPatient(String hashId, String name, String surname, String gender, int age,
                              String dob, String doctorHashId) {
        String query = "INSERT INTO patient (hash_id, name, surname, gender, age, date_of_birth, doctor_hash_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hashId);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, gender);
            stmt.setInt(5, age);
            stmt.setDate(6, Date.valueOf(dob));
            stmt.setString(7, doctorHashId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePatientDoctor(String patientHashId, String newDoctorHashId) {
        String query = "UPDATE patient SET doctor_hash_id = ? WHERE hash_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newDoctorHashId);
            stmt.setString(2, patientHashId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePatient(String hashId) {
        String query = "DELETE FROM patient WHERE hash_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hashId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}