package model;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private final double amount; //amount of transaction
    private final LocalDateTime dateTime; //time and date of transaction
    private final transactionType type; //type of transaction (INCOME, EXPENSE, or REFUND)
    private final String description; //description of transaction (like Food, Shopping, etc.)

    /* 
    * REQUIRES: amount >= 0
    * MODIFIES: this
    * EFFECTS: creates transaction with specified positive amount, time and date, 
    *          transaction type, and description.
    */
    public Transaction(double amount, LocalDateTime dateTime, transactionType type, String description) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.type = type;
        this.description = description;
    }

    /* 
    * REQUIRES: amount >= 0
    * MODIFIES: this
    * EFFECTS: creates transaction with specified positive amount, current time and date,
    *          specified transaction type, and description.
    */
    public Transaction(double amount, transactionType type, String description) {
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.type = type;
        this.description = description;
    }

    /* 
    * REQUIRES: amount >= 0
    * MODIFIES: this
    * EFFECTS: creates transaction with specified positive amount, current time and date,
    *          specified transaction type, and unspecified description.
    */
    public Transaction(double amount, transactionType type) {
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.type = type;
        this.description = "Unspecified";
    }

    public double getAmount() {
        return 0.0; //stub
    }

    public LocalDate getDateAndTime() {
        return null; //stub
    }

    public String getDescription() {
        return ""; //stub
    }
 
    public transactionType getTransactionType() {
        return null; //stub
    }

}
