package persistence;

import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.time.LocalDateTime;
import org.json.*;

// Reads Json given a source string
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");
        double balance = jsonObject.getDouble("balance");
        
        // Use the constructor with initial balance
        Account acc = new Account(name, id, balance);
        addTransactions(acc, jsonObject);
        return acc;
    }

    // MODIFIES: acc
    // EFFECTS: parses transactions from JSON object and adds them to account
    private void addTransactions(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(acc, nextTransaction);
        }
    }

    // MODIFIES: acc
    // EFFECTS: parses transaction from JSON object and adds it to account
    private void addTransaction(Account acc, JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        LocalDateTime dateTime = LocalDateTime.parse(jsonObject.getString("dateTime"));
        TransactionType type = TransactionType.valueOf(jsonObject.getString("type"));
        TransactionCategory category = TransactionCategory.valueOf(jsonObject.getString("category"));
        
        Transaction t = new Transaction(amount, dateTime, type, category);
        acc.addTransaction(t);
    }
}