package com.hdbms.services;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;

public class ReceptionistDashboard {
    private final String url;
    private final String user = "root";
    private final String password;

    public ReceptionistDashboard() {
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.password = dotenv.get("DB_PASSWORD");

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {

            System.out.println("=================================================");
            System.out.println("\nWelcome to the Receptionist Dashboard!\n");
            System.out.println("Please choose an option:");
            System.out.println("=================================================");
            System.out.println("1. Schedule an Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Cancel an Appointment");
            System.out.println("4. Update Patient Information");
            System.out.println("5. View Patient Information");
            System.out.println("6. View Doctor Information");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character

                switch (choice) {
                    case 1:
                        scheduleAppointment(scanner);
                        break;
                    case 2:
                        // register(scanner);
                        break;
                    case 3:
                        System.out.println("Thank you for using the Hospital DBMS!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number (1, 2, or 3).");
                scanner.nextLine(); // Clear the invalid input
                choice = -1; // Reset to keep the loop running
            }
        } while (choice != 0);

        scanner.close();

    }

    public void scheduleAppointment(Scanner scanner) {
        System.out.println("Scheduling an appointment...");
        // Implement the logic to schedule an appointment
        System.out.print("Enter patient username: ");
        

    }
}