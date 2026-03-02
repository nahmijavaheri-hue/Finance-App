package persistence;

import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.time.LocalDateTime;
import org.json.*;

//Reads Json given a source string
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        return null; //stub
    }

    private String readFile(String source) throws IOException {
        return ""; //stub
    }

    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        return null; //stub
    }

    // MODIFIES: acc
    // EFFECTS: parses transactions from JSON object and adds them to account
    private void addTransactions(Account acc, JSONObject jsonObject) {}

    // MODIFIES: acc
    // EFFECTS: parses transaction from JSON object and adds it to account
    private void addTransaction(Account acc, JSONObject jsonObject) {}
}