package ui;

import model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

// Console-based personal finance application
public class SimpleFinanceApp {
    private Account account;
    private Scanner input;

    // EFFECTS: sets up the account and starts the application
    public SimpleFinanceApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input and maintains the application loop
    public void runApp() {
        boolean keepGoing = true;
        String command;
        init();

        System.out.println("Welcome to SimpleFinance!");

        while (keepGoing) {
            displayOptions();
            command = input.next().toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                handleInput(command);
            }
        }

        System.out.println("\nStay financially healthy. Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: routes the user command to the appropriate helper method
    public void handleInput(String command) {
        if (command.equals("a")) {
            makeTransaction();
        } else if (command.equals("r")) {
            doRemoveTransaction();
        } else if (command.equals("v")) {
            viewTransactions();
        } else if (command.equals("f")) {
            filterTransactions();
        } else if (command.equals("m")) {
            viewMetrics();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the account with a starting balance and sets up the
    // scanner
    public void init() {
        account = new Account("Primary Account", 1, 0.0);
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r"); //
    }

    // EFFECTS: displays the main menu of options to the user
    public void displayOptions() {
        System.out.println("\n--- " + account.getName() + " ---");
        System.out.println("Current Balance: $" + String.format("%.2f", account.getBalance()));
        System.out.println("Select from:");
        System.out.println("\ta -> Add Transaction");
        System.out.println("\tr -> Remove Transaction");
        System.out.println("\tv -> View All (Chronological)");
        System.out.println("\tf -> Filter by Keyword");
        System.out.println("\tm -> View Monthly Metrics");
        System.out.println("\tq -> Quit");
        System.out.print("> ");
    }

    // MODIFIES: this
    // EFFECTS: adds a transaction with custom date/time, type, category, and amount
    private void makeTransaction() {
        LocalDateTime dt = readDateTime();
        transactionType type = readType();
        transactionCategory category = readCategory(type);

        System.out.print("Enter amount (must be > 0): ");
        double amount = input.nextDouble();

        if (amount > 0) {
            Transaction t = new Transaction(amount, dt, type, category);
            account.addTransaction(t); //
            System.out.println("Transaction recorded successfully.");
        } else {
            System.out.println("Invalid amount. Transaction cancelled.");
        }
    }

    // EFFECTS: prompts for date and time; defaults to current system time if time
    // is skipped
    private LocalDateTime readDateTime() {
        System.out.print("Enter date (yyyy-MM-dd) or 't' for today: ");
        String dateIn = input.next();
        LocalDate date = dateIn.equalsIgnoreCase("t") ? LocalDate.now() : LocalDate.parse(dateIn);

        System.out.print("Enter time (HH:mm:ss) or press enter for current system time: ");
        input.nextLine(); // Clear the scanner buffer
        String timeIn = input.nextLine();

        LocalTime time;
        if (timeIn.isEmpty()) {
            // Uses current system time as default
            time = LocalTime.now().withNano(0);
        } else {
            time = LocalTime.parse(timeIn);
        }

        return LocalDateTime.of(date, time);
    }

    // EFFECTS: prompts user to select a transaction type
    private transactionType readType() {
        System.out.println("Select type: (I)ncome, (E)xpense, (R)efund");
        String choice = input.next().toUpperCase();
        if (choice.equals("I")) {
            return transactionType.INCOME;
        }
        if (choice.equals("R")) {
            return transactionType.REFUND;
        }
        return transactionType.EXPENSE;
    }

    // EFFECTS: prompts user for a category based on the transaction type
    private transactionCategory readCategory(transactionType type) {
        System.out.println("Select Category Number:");
        if (type == transactionType.INCOME || type == transactionType.REFUND) {
            System.out.println("1: PAYCHEQUE, 2: TRANSFER_IN, 3: OTHER_IN");
            int choice = input.nextInt();
            return (choice == 1) ? transactionCategory.PAYCHEQUE
                    : (choice == 2) ? transactionCategory.TRANSFER_IN : transactionCategory.OTHER_IN;
        } else {
            System.out.println("1: FOOD, 2: RENT, 3: SHOPPING, 4: TRANSFER_OUT, 5: OTHER_OUT");
            int choice = input.nextInt();
            return (choice == 1) ? transactionCategory.FOOD
                    : (choice == 2) ? transactionCategory.RENT
                            : (choice == 3) ? transactionCategory.SHOPPING
                                    : (choice == 4) ? transactionCategory.TRANSFER_OUT : transactionCategory.OTHER_OUT;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a transaction selected by the user from the list
    private void doRemoveTransaction() {
        List<Transaction> ts = account.getTransactions();
        if (ts.isEmpty()) {
            System.out.println("No transactions to remove.");
            return;
        }

        for (int i = 0; i < ts.size(); i++) {
            System.out.println(i + ": " + ts.get(i).getAmount() + " [" + ts.get(i).getCategory() + "]");
        }

        System.out.print("Enter index to remove: ");
        int index = input.nextInt();
        if (index >= 0 && index < ts.size()) {
            account.removeTransaction(ts.get(index)); //
            System.out.println("Transaction removed.");
        }
    }

    // EFFECTS: prints all transactions in chronological order (newest first)
    private void viewTransactions() {
        displayList(account.getTransactionsNewToOld());
    }

    // EFFECTS: prints transactions where the category name contains the provided
    // keyword
    private void filterTransactions() {
        System.out.print("Enter keyword (e.g., 'FOOD'): ");
        String keyword = input.next().toUpperCase();
        List<Transaction> ts = account.getTransactions();

        System.out.println("\n--- Search Results for '" + keyword + "' ---");
        for (Transaction t : ts) {
            if (t.getCategory().name().contains(keyword)) {
                System.out.printf("%s | %-12s | $%.2f\n", t.getDateAndTime(), t.getCategory(), t.getSignedAmount());
            }
        }
    }

    // EFFECTS: prints the monthly averages for income and expenses
    private void viewMetrics() {
        System.out.print("Enter Month (1-12): ");
        int m = input.nextInt();
        System.out.print("Enter Year (yyyy): ");
        int y = input.nextInt();

        System.out.println("\n--- Monthly Metrics ---");
        System.out.printf("Avg Income:   $%.2f\n", account.getAverageIncomeByMonth(m, y)); //
        System.out.printf("Avg Expenses: $%.2f\n", account.getAverageExpensesByMonth(m, y)); //
    }

    // EFFECTS: helper to print a list of transactions
    private void displayList(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.printf("%s | %-12s | %-10s | $%.2f\n",
                    t.getDateAndTime(), t.getTransactionType(), t.getCategory(), t.getSignedAmount());
        }
    }
}