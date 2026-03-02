package model;

import java.time.LocalDateTime;
import org.json.JSONObject;
import persistence.Writable;

//Represents a transaction with an amount, date and time, type, and category.
public class Transaction implements Writable {
    private final double amount; // amount of transaction
    private final LocalDateTime dateTime; // time and date of transaction
    private final TransactionType type; // type of transaction (INCOME, EXPENSE, or REFUND)
    private final TransactionCategory category; // category of transaction (FOOD, SHOPPING, etc.)

    /*
     * REQUIRES: amount > 0.0
     * MODIFIES: this
     * EFFECTS: creates transaction with specified positive amount, time and date,
     * transaction type, and description.
     */
    public Transaction(double amount, LocalDateTime dateTime, TransactionType type, TransactionCategory category) {
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
    public Transaction(double amount, LocalDateTime dateTime, TransactionType type) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.type = type;
        if (type == TransactionType.INCOME || type == TransactionType.REFUND) {
            this.category = TransactionCategory.OTHER_IN;
        } else {
            this.category = TransactionCategory.OTHER_OUT;
        }
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateAndTime() {
        return dateTime;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public TransactionType getTransactionType() {
        return type;
    }

    // EFFECTS: Returns signed amount of transaction, negative if it is an expense,
    // positive otherwise
    public double getSignedAmount() {
        if (this.type == TransactionType.EXPENSE) {
            return -this.amount;
        }
        return this.amount;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("dateTime", dateTime.toString());
        json.put("type", type.name());
        json.put("category", category.name());
        return json;
    }
}
