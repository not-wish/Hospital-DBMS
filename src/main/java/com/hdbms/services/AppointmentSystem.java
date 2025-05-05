package com.hdbms.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class AppointmentSystem {

    // Inner class representing a doctor
    public static class Doctor {
        String name;
        String specialization;

        public Doctor(String name, String specialization) {
            this.name = name;
            this.specialization = specialization;
        }

        @Override
        public String toString() {
            return "Dr. " + name + " (" + specialization + ")";
        }
    }

    // Inner class representing an appointment
    public static class Appointment {
        Doctor doctor;
        LocalDate date;
        LocalTime time;

        public Appointment(Doctor doctor, LocalDate date, LocalTime time) {
            this.doctor = doctor;
            this.date = date;
            this.time = time;
        }

        @Override
        public String toString() {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            return "Doctor: " + doctor + ", Date: " + date + ", Time: " + time.format(timeFormatter);
        }
    }

    // Lists to store available doctors and appointments

    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Initialize the list of doctors
    

    // Check for double booking
    private static boolean isDoubleBooked(Doctor doctor, LocalDate date, LocalTime time) {
        for (Appointment appointment : appointments) {
            if (appointment.doctor.equals(doctor) && appointment.date.equals(date) && appointment.time.equals(time)) {
                return true;
            }
        }
        return false;
    }

    // Schedule an appointment (for frontend)
    public static boolean scheduleAppointment(Doctor selectedDoctor, LocalDate date, LocalTime time) {
        if (date.isBefore(LocalDate.now())) {
            return false; // Date in the past
        }
        if (isDoubleBooked(selectedDoctor, date, time)) {
            return false; // Double booking
        }
        Appointment appointment = new Appointment(selectedDoctor, date, time);
        appointments.add(appointment);
        return true;
    }

    // Cancel an appointment (for frontend)
    public static boolean cancelAppointment(int index) {
        if (index >= 0 && index < appointments.size()) {
            appointments.remove(index);
            return true;
        }
        return false;
    }

    // Reschedule an appointment (for frontend)
    public static boolean rescheduleAppointment(int index, LocalDate newDate, LocalTime newTime) {
        if (index < 0 || index >= appointments.size()) {
            return false;
        }
        if (newDate.isBefore(LocalDate.now())) {
            return false; // Date in the past
        }
        Appointment appointment = appointments.get(index);
        if (isDoubleBooked(appointment.doctor, newDate, newTime)) {
            return false; // Double booking
        }
        appointment.date = newDate;
        appointment.time = newTime;
        return true;
    }

    // Display available doctors (for console)
    private static void displayDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
            return;
        }
        System.out.println("\nAvailable Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". " + doctors.get(i));
        }
    }

    // Get a valid date from user (for console)
    private static LocalDate getValidDate() {
        LocalDate date = null;
        while (date == null) {
            System.out.print("Enter date (yyyy-MM-dd): ");
            String dateString = scanner.nextLine();
            try {
                date = LocalDate.parse(dateString, DATE_FORMATTER);
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Date cannot be in the past. Please enter a future date.");
                    date = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        return date;
    }

    // Get a valid time from user (for console)
    private static LocalTime getValidTime() {
        LocalTime time = null;
        while (time == null) {
            System.out.print("Enter time (HH:mm): ");
            String timeString = scanner.nextLine();
            try {
                time = LocalTime.parse(timeString, TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm format.");
            }
        }
        return time;
    }

    // Get doctor selection (for console)
    private static int getDoctorSelection() {
        int doctorIndex = -1;
        while (doctorIndex == -1) {
            System.out.print("Enter the number of the doctor: ");
            try {
                int selectedDoctor = scanner.nextInt();
                scanner.nextLine();
                if (selectedDoctor > 0 && selectedDoctor <= doctors.size()) {
                    doctorIndex = selectedDoctor - 1;
                } else {
                    System.out.println("Invalid doctor number. Please enter a valid number from the list.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
            if (doctorIndex == -1) {
                System.out.print("Do you want to go back to main menu? (y/n) : ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    return -1;
                }
            }
        }
        return doctorIndex;
    }

    // Schedule an appointment (for console)
    private static void scheduleAppointment() {
        if (doctors.isEmpty()) {
            System.out.println("No doctors available to schedule an appointment.");
            return;
        }
        displayDoctors();
        int doctorIndex = getDoctorSelection();
        if (doctorIndex == -1) return;
        LocalDate date = getValidDate();
        LocalTime time = getValidTime();
        boolean success = scheduleAppointment(doctors.get(doctorIndex), date, time);
        if (success) {
            System.out.println("Appointment scheduled successfully: " + appointments.get(appointments.size() - 1));
        } else {
            System.out.println("Cannot schedule: Date in the past or doctor already booked.");
        }
    }

    // View all scheduled appointments (for console)
    private static void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        System.out.println("\nScheduled Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ". " + appointments.get(i));
        }
    }

    // Cancel an appointment (for console)
    private static void cancelAppointment() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments to cancel.");
            return;
        }
        viewAppointments();
        System.out.print("Enter the number of the appointment to cancel: ");
        try {
            int appointmentNumber = scanner.nextInt();
            scanner.nextLine();
            if (appointmentNumber > 0 && appointmentNumber <= appointments.size()) {
                cancelAppointment(appointmentNumber - 1);
                System.out.println("Appointment cancelled successfully.");
            } else {
                System.out.println("Invalid appointment number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
    }

    // Reschedule an appointment (for console)
    private static void rescheduleAppointment() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments to reschedule.");
            return;
        }
        viewAppointments();
        System.out.print("Enter the number of the appointment to reschedule: ");
        try {
            int appointmentNumber = scanner.nextInt();
            scanner.nextLine();
            if (appointmentNumber > 0 && appointmentNumber <= appointments.size()) {
                LocalDate newDate = getValidDate();
                LocalTime newTime = getValidTime();
                boolean success = rescheduleAppointment(appointmentNumber - 1, newDate, newTime);
                if (success) {
                    System.out.println("Appointment rescheduled successfully: " + appointments.get(appointmentNumber - 1));
                } else {
                    System.out.println("Cannot reschedule: Date in the past or doctor already booked.");
                }
            } else {
                System.out.println("Invalid appointment number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
    }

    // Main method for console-based interface
    public static void main(String[] args) {
        initializeDoctors();
        while (true) {
            System.out.println("\nAppointment Scheduling System");
            System.out.println("1. Schedule an Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Cancel an Appointment");
            System.out.println("4. Reschedule an Appointment");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        scheduleAppointment();
                        break;
                    case 2:
                        viewAppointments();
                        break;
                    case 3:
                        cancelAppointment();
                        break;
                    case 4:
                        rescheduleAppointment();
                        break;
                    case 5:
                        System.out.println("Exiting the system. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}