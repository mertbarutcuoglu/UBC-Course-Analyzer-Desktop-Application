package persistence;

import java.io.PrintWriter;

// Represents information that can be saved to a file
// CREDITS: TellerApp
//          Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp/
public interface Saveable {
    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to printWriter
    void save(PrintWriter printWriter);
}
