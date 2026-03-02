package model;

public enum TransactionCategory {
    // Income Categories 
    PAYCHEQUE(TransactionType.INCOME),
    OTHER_IN(TransactionType.INCOME),
    TRANSFER_IN(TransactionType.INCOME),

    // Expense Categories
    FOOD(TransactionType.EXPENSE),
    RENT(TransactionType.EXPENSE),
    SHOPPING(TransactionType.EXPENSE),
    OTHER_OUT(TransactionType.EXPENSE),
    TRANSFER_OUT(TransactionType.EXPENSE);

    private final TransactionType type;

    TransactionCategory(TransactionType type) {
        this.type = type;
    }

    public TransactionType getType() {
        return type;
    }

}
