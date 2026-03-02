package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account acc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account acc = reader.read();
            assertEquals("My Account", acc.getName());
            assertEquals(0, acc.getTransactions().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccount() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account acc = reader.read();
            assertEquals("My Account", acc.getName());
            List<Transaction> ts = acc.getTransactions();
            assertEquals(2, ts.size());
            checkTransaction(100.0, TransactionType.INCOME, TransactionCategory.PAYCHEQUE, ts.get(0));
            checkTransaction(50.0, TransactionType.EXPENSE, TransactionCategory.FOOD, ts.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}