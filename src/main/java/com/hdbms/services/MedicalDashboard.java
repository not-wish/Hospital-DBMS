package com.hdbms.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



// Interface
interface Reportable {
    void viewHistory();
}

// Encapsulation
class MedicalRecord {
    private String diagnosis, prescription;

    MedicalRecord(String diagnosis, String prescription) {
        this.diagnosis = diagnosis;
        this.prescription = prescription;
    }

    public String toString() {
        return "Diagnosis: " + diagnosis + ", Prescription: " + prescription;
    }
}

class Patient implements Reportable {
    private String patientId;
    private ArrayList<MedicalRecord> records = new ArrayList<>();
    static int count = 0;

    Patient(String patientId, String name, Integer age) {
        // super(name, age);
        this.patientId = patientId;
        count++;
    }

    // Method Overloading
    void addRecord(String diagnosis, String prescription) {
        records.add(new MedicalRecord(diagnosis, prescription));
    }

    void addRecord(MedicalRecord record) {
        records.add(record);
    }

    @Override
    public void viewHistory() {
        if (records.isEmpty()) System.out.println("No records.");
        else for (MedicalRecord r : records) System.out.println(r);
    }

    public static int getPatientCount() {
        return count;
    }

    public String getId() {
        return patientId;
    }
}

public class MedicalDashboard {
    static HashMap<String, Patient> db = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n1. Add Patient  2. Add Record  3. View History  4. Count  5. Exit");
                int ch = Integer.parseInt(sc.nextLine());
                switch (ch) {
                    case 1: addPatient();
                    case 2: addRecord();
                    case 3: viewHistory();
                    case 4: System.out.println("Patients: " + Patient.getPatientCount());
                    case 5: { System.out.println("Goodbye!"); return; }
                    default: System.out.println("Invalid");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void addPatient() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        Integer age = Integer.valueOf(sc.nextLine());
        db.put(id, new Patient(id, name, age));
        System.out.println("Patient added.");
    }

    static void addRecord() {
        System.out.print("Patient ID: ");
        String id = sc.nextLine();
        Patient p = db.get(id);
        if (p == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.print("Diagnosis: ");
        String d = sc.nextLine();
        System.out.print("Prescription: ");
        String pr = sc.nextLine();
        p.addRecord(d, pr);
        System.out.println("Record added.");
    }

    static void viewHistory() {
        System.out.print("Patient ID: ");
        String id = sc.nextLine();
        Patient p = db.get(id);
        if (p != null) p.viewHistory();
        else System.out.println("Not found.");
    }
}