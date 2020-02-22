package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

// A writer that will write the course data to a file
// CREDITS: TellerApp
//          Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp/
public class Writer {
    private PrintWriter printWriter;

    // EFFECTS: constructs a Writer to write data to given file
    public Writer(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: writes saveable to file
    public void write(Saveable saveable) {
        saveable.save(printWriter);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    public void close() {
        printWriter.close();
    }
}
