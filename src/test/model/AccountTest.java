package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

public class AccountTest {
    Transaction transaction1;
    Transaction transaction2;
    Transaction transaction3;
    Transaction transaction4;
    Transaction transaction5;
    Transaction transaction6;
    Transaction transaction7;
    Transaction transaction8;
    Transaction transaction9;
    Transaction transaction10;
    Account account1;
    Account account2;

    @BeforeEach
    void runBefore() {
        transaction1 = new Transaction(100, LocalDateTime.of(2026, 2, 3, 12, 30, 30),
                transactionType.INCOME, transactionCategory.PAYCHEQUE);
        transaction2 = new Transaction(50, LocalDateTime.of(2026, 6, 20, 12, 30, 30),
                transactionType.EXPENSE, transactionCategory.FOOD);
        transaction3 = new Transaction(70, LocalDateTime.of(2026, 6, 14, 12, 30, 30),
                transactionType.EXPENSE, transactionCategory.SHOPPING);
        transaction4 = new Transaction(200, LocalDateTime.of(2026, 5, 17, 12, 30, 30),
                transactionType.INCOME, transactionCategory.TRANSFER_IN);
        transaction5 = new Transaction(20, LocalDateTime.of(2026, 10, 22, 12, 30, 30),
                transactionType.EXPENSE, transactionCategory.SHOPPING);
        transaction6 = new Transaction(20, LocalDateTime.of(2026, 2, 5, 12, 30, 30),
                transactionType.REFUND, transactionCategory.OTHER_IN);
        transaction7 = new Transaction(300, LocalDateTime.of(2026, 6, 9, 12, 30, 30),
                transactionType.EXPENSE, transactionCategory.RENT);
        transaction8 = new Transaction(400, LocalDateTime.of(2026, 6, 20, 12, 30, 30),
                transactionType.INCOME, transactionCategory.PAYCHEQUE);
        transaction9 = new Transaction(50, LocalDateTime.of(2026, 8, 10, 12, 30, 30),
                transactionType.EXPENSE, transactionCategory.FOOD);
        transaction10 = new Transaction(100, LocalDateTime.of(2026, 2, 12, 12, 30, 30),
                transactionType.EXPENSE, transactionCategory.TRANSFER_OUT);
        account1 = new Account("Chequing", 123);
        account2 = new Account("Savings", 456, 100);
    }

    @Test
    void testConstructor() {
        assertEquals("Chequing", account1.getName());
        assertEquals(123, account1.getId());
        assertEquals("Savings", account2.getName());
        assertEquals(456, account2.getId());
        assertEquals(100, account2.getBalance());

    }

    @Test
    void testGetName() {
        assertEquals("Chequing", account1.getName());
        assertEquals("Savings", account2.getName());
    }

    @Test
    void testGetId() {
        assertEquals(123, account1.getId());
        assertEquals(456, account2.getId());
    }

    @Test
    void testGetTransactions() {
        assertTrue(account1.getTransactions().isEmpty());
        assertTrue(account2.getTransactions().isEmpty());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction9);
        assertEquals(List.of(transaction1, transaction2, transaction6, transaction9), account1.getTransactions());
    }

    @Test
    void testGetBalance() {
        assertEquals(0, account1.getBalance());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        assertEquals(50, account1.getBalance());
        account1.addTransaction(transaction3);
        assertEquals(-20, account1.getBalance());
        account1.addTransaction(transaction4);
        assertEquals(180, account1.getBalance());

    }

    @Test
    void testAddTransaction() {
        assertTrue(account1.getTransactions().isEmpty());
        assertTrue(account2.getTransactions().isEmpty());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction9);
        assertEquals(List.of(transaction1, transaction2, transaction3, transaction6, transaction9),
                account1.getTransactions());
    }

    @Test
    void testRemoveTransaction() {
        assertTrue(account1.getTransactions().isEmpty());
        assertTrue(account2.getTransactions().isEmpty());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction9);
        assertEquals(List.of(transaction1, transaction2, transaction3, transaction6, transaction9),
                account1.getTransactions());
        assertTrue(account1.removeTransaction(transaction1));
        assertTrue(account1.removeTransaction(transaction6));
        assertFalse(account1.removeTransaction(transaction4));
        assertEquals(List.of(transaction2, transaction3, transaction9), account1.getTransactions());
    }

    @Test
    void testClearTransactions() {
        assertTrue(account1.getTransactions().isEmpty());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        assertEquals(List.of(transaction1, transaction2, transaction3), account1.getTransactions());
        account1.clearTransactions();
        assertTrue(account1.getTransactions().isEmpty());
    }

    @Test
    void testGetTransactionCount() {
        assertEquals(0, account1.getTransactionCount());
        assertTrue(account1.getTransactions().isEmpty());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction9);
        assertEquals(5, account1.getTransactionCount());
    }

    @Test
    void testCanFindTransaction() {
        assertFalse(account1.canFindTransaction(transaction1));
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction9);
        assertTrue(account1.canFindTransaction(transaction1));
        assertTrue(account1.canFindTransaction(transaction3));
        assertTrue(account1.canFindTransaction(transaction6));
        assertFalse(account1.canFindTransaction(transaction10));

    }

    @Test
    void testGetLastTransaction() {
        assertNull(account1.getLastTransaction());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        assertEquals(transaction2, account1.getLastTransaction());
        account1.addTransaction(transaction3);
        assertEquals(transaction2, account1.getLastTransaction());
        account1.addTransaction(transaction5);
        assertEquals(transaction5, account1.getLastTransaction());
        account1.addTransaction(transaction9);

    }

    @Test
    void testGetTransactionsByMonth() {
        assertTrue(account1.getTransactionsByMonth(6, 2026).isEmpty());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction4);
        account1.addTransaction(transaction5);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction7);
        account1.addTransaction(transaction8);
        account1.addTransaction(transaction9);
        account1.addTransaction(transaction10);
        assertEquals(List.of(transaction2, transaction3, transaction7, transaction8),
                account1.getTransactionsByMonth(6, 2026));
        assertTrue(account1.getTransactionsByMonth(1, 2026).isEmpty());
        assertFalse(account1.getTransactionsByMonth(5, 2026).isEmpty());
        assertTrue(account1.getTransactionsByMonth(6, 2025).isEmpty());
    }

    @Test
    void testGetAverageExpensesByMonth() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction4);
        account1.addTransaction(transaction5);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction7);
        account1.addTransaction(transaction8);
        account1.addTransaction(transaction9);
        account1.addTransaction(transaction10);
        assertEquals(-140, account1.getAverageExpensesByMonth(6, 2026));
    }

    @Test
    void testGetAverageIncomeByMonth() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction4);
        account1.addTransaction(transaction5);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction7);
        account1.addTransaction(transaction8);
        account1.addTransaction(transaction9);
        account1.addTransaction(transaction10);
        assertEquals(400, account1.getAverageIncomeByMonth(6, 2026));
    }

    @Test
    void testGetTransactionsNewToOld() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction4);
        account1.addTransaction(transaction5);
        account1.addTransaction(transaction6);
        account1.addTransaction(transaction7);
        account1.addTransaction(transaction8);
        account1.addTransaction(transaction9);
        account1.addTransaction(transaction10);
        assertEquals(List.of(transaction5, transaction9, transaction2, transaction8, transaction3, transaction7,
                transaction4, transaction10, transaction6, transaction1), account1.getTransactionsNewToOld());
    }

    @Test
    void testFilterByTypeNewest() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction4);
        account1.addTransaction(transaction8);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction6);
        assertEquals(List.of(transaction8, transaction4, transaction1),
                account1.filterByType(account1.getTransactions(), sortOrder.NEWEST, transactionType.INCOME));
        assertEquals(List.of(transaction2),
                account1.filterByType(account1.getTransactions(), sortOrder.NEWEST, transactionType.EXPENSE));
        assertEquals(List.of(transaction6),
                account1.filterByType(account1.getTransactions(), sortOrder.NEWEST, transactionType.REFUND));
        assertEquals(List.of(transaction8, transaction2, transaction4, transaction6, transaction1),
                account1.filterByType(account1.getTransactions(), sortOrder.NEWEST, null));
    }

    @Test
    void testFilterByTypeOldest() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction4);
        account1.addTransaction(transaction8);
        account1.addTransaction(transaction6);
        assertEquals(List.of(transaction1, transaction4, transaction8),
                account1.filterByType(account1.getTransactions(), sortOrder.OLDEST, transactionType.INCOME));
        assertTrue(
                account1.filterByType(account1.getTransactions(), sortOrder.OLDEST, transactionType.EXPENSE).isEmpty());
        assertEquals(List.of(transaction6),
                account1.filterByType(account1.getTransactions(), sortOrder.OLDEST, transactionType.REFUND));
        assertEquals(List.of(transaction1, transaction6, transaction4, transaction8),
                account1.filterByType(account1.getTransactions(), sortOrder.OLDEST, null));
    }

    @Test
    void testFilterByTypeLargest() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction4);
        account1.addTransaction(transaction8);
        assertEquals(List.of(transaction8, transaction4, transaction1),
                account1.filterByType(account1.getTransactions(), sortOrder.LARGEST, transactionType.INCOME));
        assertEquals(0,
                account1.filterByType(account1.getTransactions(), sortOrder.LARGEST, transactionType.EXPENSE).size());
        assertEquals(0,
                account1.filterByType(account1.getTransactions(), sortOrder.LARGEST, transactionType.REFUND).size());
        assertEquals(transaction8, account1.filterByType(account1.getTransactions(), sortOrder.LARGEST, null).get(0));
    }

    @Test
    void testFilterByTypeSmallest() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction4);
        account1.addTransaction(transaction8);
        assertEquals(List.of(transaction1, transaction4, transaction8),
                account1.filterByType(account1.getTransactions(), sortOrder.SMALLEST, transactionType.INCOME));
        assertEquals(0,
                account1.filterByType(account1.getTransactions(), sortOrder.SMALLEST, transactionType.EXPENSE).size());
        assertEquals(0,
                account1.filterByType(account1.getTransactions(), sortOrder.SMALLEST, transactionType.REFUND).size());
        assertEquals(transaction1, account1.filterByType(account1.getTransactions(), sortOrder.SMALLEST, null).get(0));
    }

    @Test
    void testFilterByTypeNone() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction4);
        assertEquals(2,
                account1.filterByType(account1.getTransactions(), sortOrder.NONE, transactionType.INCOME).size());
        assertEquals(0,
                account1.filterByType(account1.getTransactions(), sortOrder.NONE, transactionType.EXPENSE).size());
        assertEquals(0,
                account1.filterByType(account1.getTransactions(), sortOrder.NONE, transactionType.REFUND).size());
        assertEquals(2, account1.filterByType(account1.getTransactions(), sortOrder.NONE, null).size());
    }

    @Test
    void testFilterByCategoryNewest() {
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction9);
        assertEquals(List.of(transaction9, transaction2),
                account1.filterByCategory(account1.getTransactions(), sortOrder.NEWEST, transactionCategory.FOOD));
        assertEquals(0, account1
                .filterByCategory(account1.getTransactions(), sortOrder.NEWEST, transactionCategory.PAYCHEQUE).size());
        account1.addTransaction(transaction6);
        assertEquals(List.of(transaction6),
                account1.filterByCategory(account1.getTransactions(), sortOrder.NEWEST, transactionCategory.OTHER_IN));
        assertEquals(List.of(transaction9, transaction2, transaction6),
                account1.filterByCategory(account1.getTransactions(), sortOrder.NEWEST, null));
    }

    @Test
    void testFilterByCategoryOldest() {
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction9);
        assertEquals(List.of(transaction2, transaction9),
                account1.filterByCategory(account1.getTransactions(), sortOrder.OLDEST, transactionCategory.FOOD));
        assertEquals(0, account1
                .filterByCategory(account1.getTransactions(), sortOrder.OLDEST, transactionCategory.PAYCHEQUE).size());
        assertEquals(0, account1
                .filterByCategory(account1.getTransactions(), sortOrder.OLDEST, transactionCategory.OTHER_IN).size());
        assertEquals(List.of(transaction2, transaction9),
                account1.filterByCategory(account1.getTransactions(), sortOrder.OLDEST, null));
    }

    @Test
    void testFilterByCategoryLargest() {
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction5);
        assertEquals(List.of(transaction5, transaction3),
                account1.filterByCategory(account1.getTransactions(), sortOrder.LARGEST, transactionCategory.SHOPPING));
        assertEquals(0, account1
                .filterByCategory(account1.getTransactions(), sortOrder.LARGEST, transactionCategory.PAYCHEQUE).size());
        assertEquals(0, account1
                .filterByCategory(account1.getTransactions(), sortOrder.LARGEST, transactionCategory.OTHER_IN).size());
        assertEquals(List.of(transaction5, transaction3),
                account1.filterByCategory(account1.getTransactions(), sortOrder.LARGEST, null));
    }

    @Test
    void testFilterByCategorySmallest() {
        account1.addTransaction(transaction3);
        account1.addTransaction(transaction5); // SHOPPING: -70, -20
        assertEquals(List.of(transaction3, transaction5), account1.filterByCategory(account1.getTransactions(),
                sortOrder.SMALLEST, transactionCategory.SHOPPING));
        assertEquals(0,
                account1.filterByCategory(account1.getTransactions(), sortOrder.SMALLEST, transactionCategory.PAYCHEQUE)
                        .size());
        assertEquals(0, account1
                .filterByCategory(account1.getTransactions(), sortOrder.SMALLEST, transactionCategory.OTHER_IN).size());
        assertEquals(List.of(transaction3, transaction5),
                account1.filterByCategory(account1.getTransactions(), sortOrder.SMALLEST, null));
    }

    @Test
    void testFilterByCategoryNone() {
        account1.addTransaction(transaction2);
        assertEquals(1,
                account1.filterByCategory(account1.getTransactions(), sortOrder.NONE, transactionCategory.FOOD).size());
        assertEquals(0, account1
                .filterByCategory(account1.getTransactions(), sortOrder.NONE, transactionCategory.PAYCHEQUE).size());
        assertEquals(0, account1
                .filterByCategory(account1.getTransactions(), sortOrder.NONE, transactionCategory.OTHER_IN).size());
        assertEquals(List.of(transaction2),
                account1.filterByCategory(account1.getTransactions(), sortOrder.NONE, null));
    }

    @Test
    void testSortByDate() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        List<Transaction> sorted = account1.sortByDate(account1.getTransactions(), true);
        assertEquals(transaction2, sorted.get(0));
        List<Transaction> sortedOld = account1.sortByDate(account1.getTransactions(), false);
        assertEquals(transaction1, sortedOld.get(0));
    }

    @Test
    void testSortByType() {
        assertTrue(account1.sortByType(account1.getTransactions(), null).isEmpty());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        List<Transaction> incomeOnly = account1.sortByType(account1.getTransactions(), transactionType.INCOME);
        assertEquals(1, incomeOnly.size());
        assertEquals(transactionType.INCOME, incomeOnly.get(0).getTransactionType());
    }

    @Test
    void testSortByAmount() {
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        List<Transaction> largest = account1.sortByAmount(account1.getTransactions(), true);
        assertEquals(transaction1, largest.get(0));
        assertEquals(transaction2, account1.sortByAmount(account1.getTransactions(), false).get(0));

    }

    @Test
    void testSortByCategory() {
        assertTrue(account1.sortByCategory(account1.getTransactions(), null).isEmpty());
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction7);
        List<Transaction> rentOnly = account1.sortByCategory(account1.getTransactions(), transactionCategory.RENT);
        assertEquals(1, rentOnly.size());
        assertEquals(transactionCategory.RENT, rentOnly.get(0).getCategory());
    }

}
