package com.hdbms.services;

import java.sql.*;
import java.util.Scanner;
import io.github.cdimascio.dotenv.Dotenv;

public class PatientDashboard {
    private final String url;
    private final String user = "root";
    private final String password;

    private final String patientId;

    public PatientDashboard(String patientId) {
        this.patientId = patientId;
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.password = dotenv.get("DB_PASSWORD");

        Scanner scanner = new Scanner(System.in);
        if (!authenticatePatient(patientId)) {
            System.out.println("Invalid Patient ID! Access Denied.");
            return;
        }

        System.out.println("\nWelcome to Your Patient Dashboard!");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Appointments");
            System.out.println("2. View Prescriptions");
            System.out.println("3. View Payment Status");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAppointments();
                    break;
                case 2:
                    viewPrescriptions();
                    break;
                case 3:
                    viewPaymentStatus();
                    break;
                case 4:
                    System.out.println("Exiting dashboard. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    // Authentication check
    public boolean authenticatePatient(String patientId) {
        String query = "SELECT id FROM patients WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If record exists, authentication succeeds
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch appointments
    public void viewAppointments() {
        String query = "SELECT appointment_date, doctor_name, status FROM appointments WHERE patient_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- Your Appointments ---");
            while (rs.next()) {
                System.out.println("Date: " + rs.getString("appointment_date"));
                System.out.println("Doctor: " + rs.getString("doctor_name"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch prescriptions
    public void viewPrescriptions() {
        String query = "SELECT medicine_name, dosage FROM prescriptions WHERE patient_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- Your Prescriptions ---");
            while (rs.next()) {
                System.out.println("Medicine: " + rs.getString("medicine_name"));
                System.out.println("Dosage: " + rs.getString("dosage"));
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check payment status
    public void viewPaymentStatus() {
        String query = "SELECT payment_status FROM payments WHERE patient_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- Payment Status ---");
            if (rs.next()) {
                System.out.println("Status: " + (rs.getBoolean("payment_status") ? "Completed" : "Pending"));
            } else {
                System.out.println("No payment record found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Interactive menu for patients
    public void showDashboard() {
    }
}