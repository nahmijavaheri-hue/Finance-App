package persistence;

import model.Transaction;
import model.TransactionCategory;
import model.TransactionType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTransaction(double amount, TransactionType type,
            TransactionCategory category, Transaction t) {
        assertEquals(amount, t.getAmount());
        assertEquals(type, t.getTransactionType());
        assertEquals(category, t.getCategory());
    }
}