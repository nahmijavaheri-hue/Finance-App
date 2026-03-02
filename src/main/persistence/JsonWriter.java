package persistence;

import model.Account;
import org.json.JSONObject;
import java.io.*;

// Represents a writer that writes JSON representation of account to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {}

    // MODIFIES: this
    // EFFECTS: writes JSON representation of account to file
    public void write(Account acc) {}

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {}

    private void saveToFile(String json) {}
}