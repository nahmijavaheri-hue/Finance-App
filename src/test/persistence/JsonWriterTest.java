package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Account acc = new Account("My Account", 1);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            Account acc = new Account("My Account", 1);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals("My Account", acc.getName());
            assertEquals(0, acc.getTransactions().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            Account acc = new Account("My Account", 1);
            acc.addTransaction(new Transaction(100.0, LocalDateTime.of(2026, 2, 3, 12, 30, 30),
                    TransactionType.INCOME, TransactionCategory.PAYCHEQUE));
            acc.addTransaction(new Transaction(50.0, LocalDateTime.of(2026, 6, 20, 12, 30, 30),
                    TransactionType.EXPENSE, TransactionCategory.FOOD));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            acc = reader.read();
            assertEquals("My Account", acc.getName());
            List<Transaction> ts = acc.getTransactions();
            assertEquals(2, ts.size());
            checkTransaction(100.0, TransactionType.INCOME, TransactionCategory.PAYCHEQUE, ts.get(0));
            checkTransaction(50.0, TransactionType.EXPENSE, TransactionCategory.FOOD, ts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}