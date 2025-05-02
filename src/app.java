import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

class HashUtil {
    public static String generateKey(String user) throws NoSuchAlgorithmException {
        try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
        byte[] hash = digest.digest(user.getBytes());

        StringBuilder hexID = new StringBuilder();

        for (byte b : hash) {
            hexID.append(String.format("%02x", b));  // %02x = 2-digit hex
        }

        return hexID.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        return "Null";
    }
}

class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private int age;
    private String gender;
    private String hashid;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHashID(String hashid) {
        this.hashid = hashid;
    }

    public String getHashID() {
        return this.hashid;
    }

    // create and set hash id
    public void createHashID() {
        try {
        setHashID(HashUtil.generateKey(getUsername()));
        
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Encountered No Such Algorithm Exception");
            System.out.println(e);
        }
    }
}

class Patient extends User {
    private String bloodGroup;
    private String pastSurgeries;
    private String referredBy;
    private String dateOfBirth;

    // Getters and Setters
    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPastSurgeries() {
        return pastSurgeries;
    }

    public void setPastSurgeries(String pastSurgeries) {
        this.pastSurgeries = pastSurgeries;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}

class Doctor extends User {
    private String department;
    private String idNumber;
    private String dateOfJoining;

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }
}

public class app {
    static HashMap<String, User> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println("\nWelcome to the Hospital DBMS!\n");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character

                switch (choice) {
                    case 1:
                        login(scanner);
                        break;
                    case 2:
                        register(scanner);
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
        } while (choice != 3);

        scanner.close();
    }

    public static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userDatabase.containsKey(username) && userDatabase.get(username).getPassword().equals(password)) {
            System.out.println("Login successful! Welcome, " + username + ".");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    public static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (userDatabase.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();

        String gender;
        do {
            System.out.print("Enter gender (F/M/Other): ");
            gender = scanner.nextLine();
            if (!gender.equals("F") && !gender.equals("M") && !gender.equals("Other")) {
                System.out.println("Invalid gender. Please enter F, M, or Other exactly.");
            }
        } while (!gender.equals("F") && !gender.equals("M") && !gender.equals("Other"));

        System.out.print("Are you a Patient or Doctor? (P/D): ");
        String role = scanner.nextLine();
        if (!role.equalsIgnoreCase("P") && !role.equalsIgnoreCase("D")) {
            System.out.println("Invalid role. Please enter P for Patient or D for Doctor.");
            return;
        }

        if (role.equalsIgnoreCase("P")) {
            Patient patient = new Patient();
            patient.setUsername(username);
            patient.setPassword(password);
            patient.setName(name);
            patient.setSurname(surname);
            patient.setGender(gender);
            patient.createHashID();

            System.out.print("Enter date of birth (YYYY-MM-DD): ");
            String dob = scanner.nextLine();
            patient.setDateOfBirth(dob);

            // Calculate age from DOB
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate birthDate = LocalDate.parse(dob, formatter);
                int age = Period.between(birthDate, LocalDate.now()).getYears();
                patient.setAge(age);
                System.out.println("Patient's age is: " + age);
            } catch (Exception e) {
                System.out.println("Invalid date format. Registration failed.");
                return;
            }

            System.out.print("Enter blood group: ");
            patient.setBloodGroup(scanner.nextLine());
            System.out.print("Enter past surgeries (if any): ");
            patient.setPastSurgeries(scanner.nextLine());
            System.out.print("Enter referred by: ");
            patient.setReferredBy(scanner.nextLine());

            userDatabase.put(username, patient);
        } else if (role.equalsIgnoreCase("D")) {
            Doctor doctor = new Doctor();
            doctor.setUsername(username);
            doctor.setPassword(password);
            doctor.setName(name);
            doctor.setSurname(surname);
            doctor.setGender(gender);
            doctor.createHashID();

            System.out.print("Enter department: ");
            doctor.setDepartment(scanner.nextLine());
            System.out.print("Enter ID number: ");
            doctor.setIdNumber(scanner.nextLine());
            System.out.print("Enter date of joining: ");
            doctor.setDateOfJoining(scanner.nextLine());

            userDatabase.put(username, doctor);
        }

        System.out.println("You are registered successfully!");
        System.out.print("Do you want to proceed for login? (Yes/No): ");
        String proceedForLogin = scanner.nextLine();
        if (proceedForLogin.equalsIgnoreCase("Yes")) {
            login(scanner);
        }
    }
}
// database code


public class HospitalDatabaseSetup {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        // Database connection parameters
        String baseUrl = "jdbc:mysql://localhost:3306/";
        String dbName = "hospital_db";
        @SuppressWarnings("unused")
        String url = baseUrl + dbName + "?useSSL=false&serverTimezone=UTC";
        String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
        String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "9347384518";

        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found. Ensure the MySQL Connector/J library is included.");
            e.printStackTrace();
            return;
        }

        // Establish database connection
        try (Connection conn = DriverManager.getConnection(baseUrl, user, password);
             Statement stmt = conn.createStatement()) {

            // Create database
            try {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
              //  System.out.println("Database '" + dbName + "' created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating database: " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
                return;
            }

            // Select the database
            try {
                stmt.executeUpdate("USE " + dbName);
               // System.out.println("Using database '" + dbName + "'.");
            } catch (SQLException e) {
                System.err.println("Error selecting database: " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
                return;
            }

            // Create admin table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS admin (
                        hash_id VARCHAR(64) PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        surname VARCHAR(100) NOT NULL,
                        username VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        gender ENUM('M', 'F', 'Other') NOT NULL,
                        address VARCHAR(255),
                        email VARCHAR(255) UNIQUE,
                        phone_number VARCHAR(20),
                        additional_info TEXT
                    )
                """);
              //  System.out.println("Table 'admin' created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating table 'admin': " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
            }

            // Create doctor table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS doctor (
                        hash_id VARCHAR(64) PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        surname VARCHAR(100) NOT NULL,
                        username VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        gender ENUM('M', 'F', 'Other') NOT NULL,
                        department VARCHAR(100) NOT NULL,
                        id_number VARCHAR(50) NOT NULL,
                        date_of_joining DATE NOT NULL,
                        admin_hash_id VARCHAR(64),
                        address VARCHAR(255),
                        email VARCHAR(255) UNIQUE,
                        phone_number VARCHAR(20),
                        additional_info TEXT,
                        FOREIGN KEY (admin_hash_id) REFERENCES admin(hash_id) ON DELETE SET NULL
                    )
                """);
               // System.out.println("Table 'doctor' created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating table 'doctor': " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
            }

            // Create receptionist table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS receptionist (
                        hash_id VARCHAR(64) PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        surname VARCHAR(100) NOT NULL,
                        username VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        gender ENUM('M', 'F', 'Other') NOT NULL,
                        address VARCHAR(255),
                        email VARCHAR(255) UNIQUE,
                        phone_number VARCHAR(20),
                        admin_hash_id VARCHAR(64),
                        additional_info TEXT,
                        FOREIGN KEY (admin_hash_id) REFERENCES admin(hash_id) ON DELETE SET NULL
                    )
                """);
               // System.out.println("Table 'receptionist' created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating table 'receptionist': " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
            }

            // Create lab_technician table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS lab_technician (
                        hash_id VARCHAR(64) PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        surname VARCHAR(100) NOT NULL,
                        username VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        gender ENUM('M', 'F', 'Other') NOT NULL,
                        address VARCHAR(255),
                        email VARCHAR(255) UNIQUE,
                        phone_number VARCHAR(20),
                        admin_hash_id VARCHAR(64),
                        additional_info TEXT,
                        FOREIGN KEY (admin_hash_id) REFERENCES admin(hash_id) ON DELETE SET NULL
                    )
                """);
               // System.out.println("Table 'lab_technician' created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating table 'lab_technician': " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
            }

            // Create patient table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS patient (
                        hash_id VARCHAR(64) PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        surname VARCHAR(100) NOT NULL,
                        username VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        gender ENUM('M', 'F', 'Other') NOT NULL,
                        age INT NOT NULL,
                        blood_group VARCHAR(10),
                        past_surgeries TEXT,
                        referred_by VARCHAR(100),
                        date_of_birth DATE NOT NULL,
                        address VARCHAR(255),
                        email VARCHAR(255) UNIQUE,
                        phone_number VARCHAR(20),
                        doctor_hash_id VARCHAR(64),
                        receptionist_hash_id VARCHAR(64),
                        technician_hash_id VARCHAR(64),
                        additional_info TEXT,
                        FOREIGN KEY (doctor_hash_id) REFERENCES doctor(hash_id) ON DELETE SET NULL,
                        FOREIGN KEY (receptionist_hash_id) REFERENCES receptionist(hash_id) ON DELETE SET NULL,
                        FOREIGN KEY (technician_hash_id) REFERENCES lab_technician(hash_id) ON DELETE SET NULL
                    )
                """);
              //  System.out.println("Table 'patient' created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating table 'patient': " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
            }

            // Create appointment table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS appointment (
                        hash_id VARCHAR(64) PRIMARY KEY,
                        patient_hash_id VARCHAR(64) NOT NULL,
                        doctor_hash_id VARCHAR(64) NOT NULL,
                        appointment_date DATETIME NOT NULL,
                        status ENUM('Scheduled', 'Completed', 'Cancelled') NOT NULL,
                        additional_info TEXT,
                        FOREIGN KEY (patient_hash_id) REFERENCES patient(hash_id) ON DELETE CASCADE,
                        FOREIGN KEY (doctor_hash_id) REFERENCES doctor(hash_id) ON DELETE CASCADE
                    )
                """);
               // System.out.println("Table 'appointment' created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating table 'appointment': " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
            }

            // Create bill table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS bill (
                        hash_id VARCHAR(64) PRIMARY KEY,
                        patient_hash_id VARCHAR(64) NOT NULL,
                        amount DECIMAL(10, 2) NOT NULL,
                        bill_date DATE NOT NULL,
                        status ENUM('Pending', 'Paid') NOT NULL,
                        additional_info TEXT,
                        FOREIGN KEY (patient_hash_id) REFERENCES patient(hash_id) ON DELETE CASCADE
                    )
                """);
              //  System.out.println("Table 'bill' created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating table 'bill': " + e.getMessage() +
                                   ", SQL State: " + e.getSQLState() +
                                   ", Error Code: " + e.getErrorCode());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage() +
                               ", SQL State: " + e.getSQLState() +
                               ", Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
    }
}
