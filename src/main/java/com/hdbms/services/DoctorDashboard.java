package com.hdbms.services;

import java.sql.*;
import java.util.Scanner;
import java.util.UUID;
import io.github.cdimascio.dotenv.Dotenv;

public class DoctorDashboard {
    private final String url;
    private final String user = "root";
    private final String password;
    private final String doctorHashId;
    public DoctorDashboard(String doctorHashId) {
        this.doctorHashId = doctorHashId;
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.password = dotenv.get("DB_PASSWORD");

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to Your Doctor Dashboard!");
        System.out.println("Your Unique Hash ID: " + doctorHashId);
        
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Scheduled Appointments");
            System.out.println("2. Prescribe Medicine");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAppointments();
                    break;
                case 2:
                    System.out.print("\nEnter Patient Hash ID: ");
                    String patientHashId = scanner.nextLine();
                    System.out.print("Enter Medicine Name: ");
                    String medicine = scanner.nextLine();
                    System.out.print("Enter Dosage: ");
                    String dosage = scanner.nextLine();
                    prescribeMedicine(patientHashId, medicine, dosage);
                    break;
                case 3:
                    System.out.println("Exiting dashboard. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // View scheduled appointments based on Hash ID
    public void viewAppointments() {
        String query = "SELECT patient_name, appointment_date, patient_hash_id FROM appointments WHERE doctor_hash_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctorHashId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- Scheduled Appointments ---");
            while (rs.next()) {
                System.out.println("Patient: " + rs.getString("patient_name"));
                System.out.println("Appointment Date: " + rs.getString("appointment_date"));
                System.out.println("Patient Hash ID: " + rs.getString("patient_hash_id"));
                System.out.println("------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Prescribe medicine to a specific patient
    public void prescribeMedicine(String patientHashId, String medicine, String dosage) {
        String query = "INSERT INTO prescriptions (patient_hash_id, medicine_name, dosage) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientHashId);
            stmt.setString(2, medicine);
            stmt.setString(3, dosage);
            stmt.executeUpdate();

            System.out.println("\nPrescription added successfully for Patient ID: " + patientHashId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}