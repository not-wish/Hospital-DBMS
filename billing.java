import java.time.LocalDate;

class Billing {
    private String billingID;
    private String patientID;
    private double amount;
    private LocalDate dateOfBilling;

    public Billing(String billingID, String patientID, double amount) {
        this.billingID = billingID;
        this.patientID = patientID;
        this.amount = amount;
        this.dateOfBilling = LocalDate.now();
    }

    public String getBillingID() {
        return billingID;
    }

    public String getPatientID() {
        return patientID;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDateOfBilling() {
        return dateOfBilling;
    }

    public void generateBill() {
        System.out.println("Generating bill...");
        System.out.println("Billing ID: " + billingID);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Amount: $" + amount);
        System.out.println("Date of Billing: " + dateOfBilling);
    }

    public void processPayment(double paymentAmount) {
        if (paymentAmount >= amount) {
            System.out.println("Payment of $" + paymentAmount + " processed successfully.");
            amount = 0; // Mark bill as paid
        } else {
            System.out.println("Insufficient payment. Remaining amount: $" + (amount - paymentAmount));
        }
    }

    public void retrieveBillingInfo() {
        System.out.println("Billing Information:");
        System.out.println("Billing ID: " + billingID);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Amount Due: $" + amount);
        System.out.println("Date of Billing: " + dateOfBilling);
    }
}