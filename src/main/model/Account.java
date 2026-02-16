package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Account {
    private List<Transaction> transactions; // list of all transactions associated with this account
    private String name; // name of this account
    private int id; // id of this account
    private double balance; // balance of this account

    /*
     * REQUIRES: name has non-zero length and id >= 0
     * MODIFIES: this
     * EFFECTS: creates an account with the given name and an id, with no recorded
     * transactions and a 0.0 balance.
     */
    public Account(String name, int id) {
        this.name = name;
        this.id = id;
        transactions = new ArrayList<>();
        balance = 0.0;
    }

    /*
     * REQUIRES: name has non-zero length and id >= 0 and initialBalance > 0.0
     * MODIFIES: this
     * EFFECTS: creates an account with the given name and an id, with no recorded
     * transactions and a positive initial balance.
     */
    public Account(String name, int id, double initialBalance) {
        this.name = name;
        this.id = id;
        this.transactions = new ArrayList<>();
        this.balance = initialBalance;
    }

    public String getName() {
        return ""; // stub
    }

    public int getId() {
        return -1; // stub
    }

    public List<Transaction> getTransactions() {
        return null; // stub
    }

    public double getBalance() {
        return 0.0; // stub
    }

    /*
     * REQUIRES: !transactions.contains(transaction)
     * MODIFIES: this
     * EFFECTS: adds transaction to list of transactions if not already contained in
     * list
     */
    public void addTransaction(Transaction transaction) {

    }

    /*
     * MODIFIES: this
     * EFFECTS: removes transaction from list of transactions and returns true
     * indicating a success,
     * and returns false if transaction is not contained in transactions
     */
    public boolean removeTransaction(Transaction transaction) {
        return false; // stub
    }

    /*
     * MODIFIES: this
     * EFFECTS: clears list of transactions
     */
    public void clearTransactions() {

    }

    public int getTransactionCount() {
        return -1; // stub
    }

    /*
     * EFFECTS: returns true if transaction was found in list of transactions in
     * this account
     */
    public boolean canFindTransaction(Transaction transaction) {
        return false; // stub
    }

    /*
     * EFFECTS: returns latest transaction in list of transactions by date and not
     * by order added to list
     */
    public Transaction getLastTransaction() {
        return null;
    }

    /*
     * EFFECTS: returns list of transactions which occured in that month and year
     */
    public List<Transaction> getTransactionsByMonth(int month, int year) {
        return null; // stub
    }

    /*
     * EFFECTS: returns average amount spent within EXPENSE transactions occuring in
     * given month and year
     */
    public double getAverageExpensesByMonth(int month, int year) {
        return 0.0; // stub
    }

    /*
     * EFFECTS: returns average amount recieved within INCOME transactions occuring
     * in given month and year
     */
    public double getAverageIncomeByMonth(int month, int year) {
        return 0.0; // stub
    }

    public List<Transaction> getTransactionsNewToOld() {
        return null; // stub
    }

    /*
     * REQUIRES: order is one of NEWEST, OLDEST, LARGEST, SMALLEST, or NONE.
     * type is either REFUNDS, INCOME, EXPENSES or null (which would represent all
     * transactions)
     * EFFECTS: return list of sorted transactions based off the given filters,
     * which is a combination of
     * filter and type. For example, if filter is "NEWEST" and type is INCOME, then
     * the returned list will comprise of
     * income transactions sorted from newest to oldest.
     */
    public List<Transaction> filterByType(List<Transaction> toFilter, sortOrder order, transactionType type) {
        return null; // stub
    }

    /*
     * REQUIRES: order is one of NEWEST, OLDEST, LARGEST, SMALLEST, or NONE.
     * category is either PAYCHEQUE, OTHER_IN, TRANSFER_IN, FOOD, RENT, SHOPPING,
     * OTHER_OUT, TRANSFER_OUT, or null (which would represent all transactions)
     * EFFECTS: return list of sorted transactions based off the given filters,
     * which is a combination of
     * filter and category. For example, if filter is "NEWEST" and type is
     * PAYCHEQUE, then the returned list will comprise of
     * PAYCHEQUE transactions sorted from newest to oldest.
     */
    public List<Transaction> filterByCategory(List<Transaction> toFilter, sortOrder order,
            transactionCategory category) {
        return null; // stub
    }

    /*
     * EFFECTS: sorts given list of transactions (toSort) by date, either from
     * newest to oldest if newest is true
     * or from oldest to newest if newest is false, returning sorted list of
     * transactions by date
     */
    protected List<Transaction> sortByDate(List<Transaction> toSort, boolean newest) {
        return null; // stub
    }

    /*
     * EFFECTS: sorts given list of transactions (toSort) by type, returning list of
     * transactions with that given type,
     * unless type is null which returns the given list of transactions
     */
    protected List<Transaction> sortByType(List<Transaction> toSort, transactionType type) {
        return null; // stub
    }

    /*
     * EFFECTS: sorts given list of transactions (toSort) by amount, either from
     * largest to smallest if largest is true
     * or from smallest to largest if largest is false, returning sorted list of
     * transactions by amount
     */
    protected List<Transaction> sortByAmount(List<Transaction> toSort, boolean largest) {
        return null; // stub
    }

    /*
     * EFFECTS: sorts given list of transactions (toSort) by category, returning
     * list of transactions with that given category,
     * unless type is null which returns the given list of transactions
     */
    protected List<Transaction> sortByCategory(List<Transaction> toSort, transactionCategory category) {
        return null; // stub
    }
}
