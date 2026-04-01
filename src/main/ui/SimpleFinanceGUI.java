package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.EventLog;
import model.Event;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Graphical user interface for personal finance tracking
public class SimpleFinanceGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/account.json";
    private Account account;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // GUI Visual Components
    private JLabel balanceLabel;
    private DefaultListModel<String> listModel;
    private JList<String> transactionDisplayList;
    private JTextField filterField;
    private FinanceGraphPanel graphPanel;

    /*
     * MODIFIES: this
     * EFFECTS: initializes account data, persistence, and constructs the
     * main graphical layout for the user interface.
     */
    public SimpleFinanceGUI() {
        super("SimpleFinance Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 550);

        initData();
        initializeLayout();

        setLocationRelativeTo(null);
        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
                System.exit(0);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: instantiates the data structures and IO utilities for persistence.
     */
    private void initData() {
        account = new Account("Primary Account", 1, 0.0);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: builds structural panels for viewing, adding, filtering, and
     * graphing.
     */
    private void initializeLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // Balance display
        balanceLabel = new JLabel("Current Balance: $0.00", JLabel.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(balanceLabel, BorderLayout.NORTH);

        // Transactions on Left, Graph View on Right
        listModel = new DefaultListModel<>();
        transactionDisplayList = new JList<>(listModel);
        transactionDisplayList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(transactionDisplayList);

        graphPanel = new FinanceGraphPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, graphPanel);
        splitPane.setDividerLocation(400);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Controls & Metrics
        mainPanel.add(createControlPanel(), BorderLayout.EAST);

        // File Persistence
        mainPanel.add(createPersistencePanel(), BorderLayout.SOUTH);

        refreshUI();
    }

    /*
     * EFFECTS: bundles interaction buttons for adding transactions and metrics
     * viewing.
     */
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        JButton addBtn = createWiredButton("Add Transaction", "add");
        JButton filterBtn = createWiredButton("Filter by Category", "filter");
        JButton metricsBtn = createWiredButton("Graph Month metrics", "metrics");
        JButton removeBtn = createWiredButton("Remove Selected", "remove");

        filterField = new JTextField(10);
        filterField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        controlPanel.add(new JLabel("Control Panel:"));
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(addBtn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(new JLabel("Filter Category Keyword:"));
        controlPanel.add(filterField);
        controlPanel.add(filterBtn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(metricsBtn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(removeBtn);

        return controlPanel;
    }

    /*
     * EFFECTS: creates and assigns standard action commands to persistence buttons.
     */
    private JPanel createPersistencePanel() {
        JPanel persistencePanel = new JPanel(new FlowLayout());
        persistencePanel.add(createWiredButton("Save Data", "save"));
        persistencePanel.add(createWiredButton("Load Data", "load"));
        return persistencePanel;
    }

    /*
     * EFFECTS: helper method to generate a JButton pre-wired with an action
     * command.
     */
    private JButton createWiredButton(String label, String command) {
        JButton button = new JButton(label);
        button.setActionCommand(command);
        button.addActionListener(this);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    /*
     * MODIFIES: this
     * EFFECTS: interprets user interactions with side panel triggers.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                promptAddTransaction();
                break;
            case "filter":
                doVisualFiltering();
                break;
            case "metrics":
                promptViewMetrics();
                break;
            case "remove":
                doVisualRemoval();
                break;
            case "save":
                saveAccount();
                break;
            case "load":
                loadAccount();
                break;
        }
    }

    /*
     * MODIFIES: this, account
     * EFFECTS: launches sequence of dialog popups to gather payload for a new
     * transaction.
     */
    private void promptAddTransaction() {
        try {
            String amountStr = JOptionPane.showInputDialog(this, "Enter amount (must be > 0):");
            if (amountStr == null) {
                return;
            }
            double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive!");
                return;
            }

            LocalDateTime dt = promptDateTime();
            TransactionType type = promptForType();
            TransactionCategory category = promptForCategory(type);

            Transaction t = new Transaction(amount, dt, type, category);
            account.addTransaction(t);
            refreshUI();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid amount payload.");
        }
    }

    /*
     * EFFECTS: prompts user for a custom date and custom time, defaulting to
     * Today/Now if left blank.
     */
    @SuppressWarnings("methodlength")
    private LocalDateTime promptDateTime() {
        String dateIn = JOptionPane.showInputDialog(this, "Enter date (yyyy-MM-dd) or 't' for today:");
        if (dateIn == null) {
            return LocalDateTime.now();
        }
        LocalDate date;
        try {
            date = dateIn.equalsIgnoreCase("t") || dateIn.trim().isEmpty() ? LocalDate.now() : LocalDate.parse(dateIn);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Defaulting to today.");
            date = LocalDate.now();
        }
        String timeIn = JOptionPane.showInputDialog(this,
                "Enter time (HH:mm:ss) or press OK/Enter for current system time:");

        LocalTime time;
        if (timeIn == null || timeIn.trim().isEmpty()) {
            time = LocalTime.now().withNano(0);
        } else {
            try {
                time = LocalTime.parse(timeIn);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid time format. Defaulting to current system time.");
                time = LocalTime.now().withNano(0);
            }
        }

        return LocalDateTime.of(date, time);
    }

    /*
     * EFFECTS: prompts user to select transaction type from a standard dropdown
     * dropdown menu.
     */
    private TransactionType promptForType() {
        TransactionType[] options = { TransactionType.EXPENSE, TransactionType.INCOME, TransactionType.REFUND };
        return (TransactionType) JOptionPane.showInputDialog(this, "Select Transaction Type:",
                "Type", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    /*
     * EFFECTS: prompts user to select categories using localized enum dropdown.
     */
    private TransactionCategory promptForCategory(TransactionType type) {
        if (type == TransactionType.INCOME || type == TransactionType.REFUND) {
            TransactionCategory[] choices = { TransactionCategory.PAYCHEQUE,
                    TransactionCategory.TRANSFER_IN, TransactionCategory.OTHER_IN };
            return (TransactionCategory) JOptionPane.showInputDialog(this, "Select Category:",
                    "Category", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        } else {
            TransactionCategory[] choices = { TransactionCategory.FOOD, TransactionCategory.RENT,
                    TransactionCategory.SHOPPING, TransactionCategory.TRANSFER_OUT, TransactionCategory.OTHER_OUT };
            return (TransactionCategory) JOptionPane.showInputDialog(this, "Select Category:",
                    "Category", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: filters graphical list viewport based on active keyword field.
     */
    private void doVisualFiltering() {
        String keyword = filterField.getText().toUpperCase();
        listModel.clear();
        for (Transaction t : account.getTransactions()) {
            if (t.getCategory().name().contains(keyword)) {
                listModel.addElement(formatTransactionString(t));
            }
        }
    }

    /*
     * MODIFIES: this, account
     * EFFECTS: removes whichever item is currently selected in the center JList
     * viewport.
     */
    private void doVisualRemoval() {
        int selectedIndex = transactionDisplayList.getSelectedIndex();
        if (selectedIndex != -1) {
            Transaction toRemove = account.getTransactionsNewToOld().get(selectedIndex);
            account.removeTransaction(toRemove);
            refreshUI();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item from the list to remove.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: queries month metrics and repaints the custom visual bar chart.
     */
    private void promptViewMetrics() {
        try {
            String monthStr = JOptionPane.showInputDialog(this, "Enter Month (1-12):");
            if (monthStr == null) {
                return;
            }
            int month = Integer.parseInt(monthStr);

            String yearStr = JOptionPane.showInputDialog(this, "Enter Year (yyyy):");
            if (yearStr == null) {
                return;
            }
            int year = Integer.parseInt(yearStr);

            double avgIncome = account.getAverageIncomeByMonth(month, year);
            double avgExpenses = account.getAverageExpensesByMonth(month, year);

            graphPanel.updateMetrics(month, year, avgIncome, avgExpenses);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid numerical inputs.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: synchronizes visual lists and balance indicators with current
     * account state.
     */
    private void refreshUI() {
        listModel.clear();
        for (Transaction t : account.getTransactionsNewToOld()) {
            listModel.addElement(formatTransactionString(t));
        }
        balanceLabel.setText(String.format("Current Balance: $%.2f", account.getBalance()));
        graphPanel.repaint(); // Updates metrics overlay
    }

    /*
     * EFFECTS: normalizes spacing for uniform list visualization.
     */
    private String formatTransactionString(Transaction t) {
        return String.format("%-12s | %-12s | %-12s | $%.2f",
                t.getDateAndTime().toLocalDate(), t.getTransactionType(), t.getCategory(), t.getSignedAmount());
    }

    /*
     * EFFECTS: writes accounting structures to JSON files.
     */
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved data successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error writing file.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: pulls account state from local JSON structures.
     */
    private void loadAccount() {
        try {
            account = jsonReader.read();
            refreshUI();
            JOptionPane.showMessageDialog(this, "Loaded data successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
        }
    }

    // --- INNER CLASS: Custom Visual Graphical Canvas ---

    /*
     * Inner class representing the visual graphical component requirement (Bar
     * Chart).
     */
    private class FinanceGraphPanel extends JPanel {
        private double avgIncome = 0;
        private double avgExpense = 0;
        private int month = 0;
        private int year = 0;

        /*
         * REQUIRES: month 1-12, year > 0
         * MODIFIES: this
         * EFFECTS: records metric values and instructs graphical redrawing.
         */
        public void updateMetrics(int month, int year, double income, double expense) {
            this.month = month;
            this.year = year;
            this.avgIncome = income;
            this.avgExpense = Math.abs(expense); // Use absolute value for drawing heights
            repaint();
        }

        /*
         * MODIFIES: g
         * EFFECTS: paints drawing surfaces with visual bar graphs comparing income vs
         * expenses.
         */
        @Override
        @SuppressWarnings("methodlength")
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            int width = getWidth();
            int height = getHeight();

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);

            g2.setColor(Color.BLACK);
            g2.drawString("Visual Summary Metrics (Month: " + month + "/" + year + ")", 20, 30);

            if (avgIncome == 0 && avgExpense == 0) {
                g2.drawString("No analytical data queried. Click 'Graph Month metrics' to draw.", 20, height / 2);
                return;
            }

            double maxVal = Math.max(avgIncome, avgExpense);
            if (maxVal == 0) {
                maxVal = 1;
            }

            int barWidth = 80;
            int chartBase = height - 50;
            int maxBarHeight = height - 120;
            int incomeHeight = (int) ((avgIncome / maxVal) * maxBarHeight);
            int expenseHeight = (int) ((avgExpense / maxVal) * maxBarHeight);

            g2.setColor(new Color(34, 139, 34));
            g2.fillRect(70, chartBase - incomeHeight, barWidth, incomeHeight);
            g2.setColor(Color.BLACK);
            g2.drawString(String.format("Inc: $%.1f", avgIncome), 70, chartBase - incomeHeight - 5);

            g2.setColor(new Color(178, 34, 34));
            g2.fillRect(200, chartBase - expenseHeight, barWidth, expenseHeight);
            g2.setColor(Color.BLACK);
            g2.drawString(String.format("Exp: $%.1f", avgExpense), 200, chartBase - expenseHeight - 5);

            g2.drawLine(40, chartBase, width - 40, chartBase);
            g2.drawString("Avg Income", 75, chartBase + 20);
            g2.drawString("Avg Expense", 205, chartBase + 20);
        }

    }
}