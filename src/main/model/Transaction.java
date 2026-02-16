package model;

import java.time.LocalDateTime;

public class Transaction {
    private final double amount; // amount of transaction
    private final LocalDateTime dateTime; // time and date of transaction
    private final transactionType type; // type of transaction (INCOME, EXPENSE, or REFUND)
    private final transactionCategory category; // category of transaction (FOOD, SHOPPING, etc.)

    /*
     * REQUIRES: amount > 0.0
     * MODIFIES: this
     * EFFECTS: creates transaction with specified positive amount, time and date,
     * transaction type, and description.
     */
    public Transaction(double amount, LocalDateTime dateTime, transactionType type, transactionCategory category) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.type = type;
        this.category = category;
    }

    /*
     * REQUIRES: amount > 0.0
     * MODIFIES: this
     * EFFECTS: creates transaction with specified positive amount, current time and
     * date,
     * specified transaction type, and unspecified description.
     */
    public Transaction(double amount, LocalDateTime dateTime, transactionType type) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.type = type;
        if (type == transactionType.INCOME || type == transactionType.REFUND) {
            this.category = transactionCategory.OTHER_IN;
        } else {
            this.category = transactionCategory.OTHER_OUT;
        }
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateAndTime() {
        return dateTime;
    }

    public transactionCategory getCategory() {
        return category;
    }

    public transactionType getTransactionType() {
        return type;
    }

    // EFFECTS: Returns signed amount of transaction, negative if it is an expense,
    // positive otherwise
    public double getSignedAmount() {
        if (this.type == transactionType.EXPENSE) {
            return -this.amount;
        }
        return this.amount;
    }

}
