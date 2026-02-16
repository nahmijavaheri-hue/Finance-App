package model;

public enum transactionCategory {
    // Income Categories 
    PAYCHEQUE(transactionType.INCOME),
    OTHER_IN(transactionType.INCOME),
    TRANSFER_IN(transactionType.INCOME),

    // Expense Categories
    FOOD(transactionType.EXPENSE),
    RENT(transactionType.EXPENSE),
    SHOPPING(transactionType.EXPENSE),
    OTHER_OUT(transactionType.EXPENSE),
    TRANSFER_OUT(transactionType.EXPENSE);

    private final transactionType type;

    transactionCategory(transactionType type) {
        this.type = type;
    }

    public transactionType getType() {
        return type;
    }

}
