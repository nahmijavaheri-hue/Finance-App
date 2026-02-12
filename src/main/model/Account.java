package model;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Account {
    private List<Transaction> transactions;
    private String name;
    private int id;
    private double balance;

    public Account(String name, int id) {
        this.name = name;
        this.id = id;
        transactions = new ArrayList<>();
        balance = 0.0;
    }

    public Account(String name, int id, List<Transaction> transactions) {
        this.name = name;
        this.id = id;
        this.transactions = transactions;
        balance = 0.0;
    }

    public String getName() {
        return "";
    }

    public int getId() {
        return -1;
    }

    public List<Transaction> geTransactions() {
        return null;
    }

    public double getBalance() {
        return 0.0;
    }

    public void addTransaction(Transaction transaction) {

    }

    public boolean findTransaction(Transaction transaction) {
        return false;
    }

    public Transaction getLastTransaction() {
        return null;
    }

    public List<Transaction> getTransactionsByMonth(LocalDate date) {
        return null;
    }
    
    public double getAverageExpensesByMonth(LocalDate date) {
        return 0.0;
    }

    public double getAverageIncomeByMonth(LocalDate date) {
        return 0.0;
    }

    //REQUIRES: filter is one of "NEWEST", "OLDEST", "LARGEST", or "SMALLEST". 
    //          type is either +1 (representing income transactions), 0 (all transactions), -1 (representing expenses)
    //EFFECTS:
    public List<Transaction> filterBy(String filter, int type) {
        return null;
    }

    
}
