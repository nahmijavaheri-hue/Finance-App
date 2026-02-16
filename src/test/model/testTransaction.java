package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class testTransaction {
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;

    @BeforeEach
    void runBefore() {
        transaction1 = new Transaction(100.0, LocalDateTime.of(2026, 2, 11, 12, 0, 0), transactionType.INCOME,
                transactionCategory.PAYCHEQUE);
        transaction2 = new Transaction(50.0, LocalDateTime.of(2026, 2, 9, 12, 0, 0), transactionType.EXPENSE);
        transaction3 = new Transaction(100.0, LocalDateTime.of(2026, 2, 11, 12, 0, 0), transactionType.INCOME);
        transaction4 = new Transaction(100.0, LocalDateTime.of(2026, 2, 11, 12, 0, 0), transactionType.REFUND);
    }

    @Test
    void testConstructor() {
        assertEquals(100.0, transaction1.getAmount());
        assertEquals(50.0, transaction2.getAmount());
        assertEquals(LocalDateTime.of(2026, 2, 11, 12, 0, 0), transaction1.getDateAndTime());
        assertEquals(LocalDateTime.of(2026, 2, 9, 12, 0, 0), transaction2.getDateAndTime());
        assertEquals(transactionCategory.PAYCHEQUE, transaction1.getCategory());
        assertEquals(transactionCategory.OTHER_OUT, transaction2.getCategory());
        assertEquals(transactionCategory.OTHER_IN, transaction3.getCategory());
        assertEquals(transactionType.INCOME, transaction1.getTransactionType());
        assertEquals(transactionType.EXPENSE, transaction2.getTransactionType());
    }

    @Test
    void testGetAmount() {
        assertEquals(100.0, transaction1.getAmount());
        assertEquals(50.0, transaction2.getAmount());
    }

    @Test
    void testGetDateAndTime() {
        assertEquals(LocalDateTime.of(2026, 2, 11, 12, 0, 0), transaction1.getDateAndTime());
        assertEquals(LocalDateTime.of(2026, 2, 9, 12, 0, 0), transaction2.getDateAndTime());
    }

    @Test
    void testGetCategory() {
        assertEquals(transactionCategory.PAYCHEQUE, transaction1.getCategory());
        assertEquals(transactionType.INCOME, transaction1.getCategory().getType());
        assertEquals(transactionCategory.OTHER_OUT, transaction2.getCategory());
        assertEquals(transactionCategory.OTHER_IN, transaction3.getCategory());
        assertEquals(transactionCategory.OTHER_IN, transaction4.getCategory());

    }

    @Test
    void testGetTransactionType() {
        assertEquals(transactionType.INCOME, transaction1.getTransactionType());
        assertEquals(transactionType.EXPENSE, transaction2.getTransactionType());
    }

    @Test
    void testGetSignedAmount() {
        assertEquals(100, transaction1.getSignedAmount());
        assertEquals(-50, transaction2.getSignedAmount());
    }
}
