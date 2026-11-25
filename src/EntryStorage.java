import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class EntryStorage {

    private static final String DATA_FOLDER = "data";
    private static final String ENTRIES_FILE = "data/entries.csv";

    //A missing file normal on first run, returns an empty list, not error
    public static ArrayList<FoodEntry> loadEntries() {
        ArrayList<FoodEntry> entries = new ArrayList<>();
        File file = new File(ENTRIES_FILE);
        if (!file.exists()) {
            return entries;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                //One damaged line wont stop log from loading, so skip it and say which one
                try {
                    entries.add(FoodEntry.fromCSVLine(line));
                } catch (IllegalArgumentException e) {
                    IO.println("Skipped line " + lineNumber + " in " + ENTRIES_FILE
                            + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            IO.println("Could not read " + ENTRIES_FILE + ": " + e.getMessage());
        }
        return entries;
    }

    //Appends 1 line, doesnt rewrite file, earlier entries not lost if somethign fails
    public static void saveEntry(FoodEntry entry) {
        //Data folder may not exist if someone clones the repo without it
        new File(DATA_FOLDER).mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(ENTRIES_FILE, true))) {
            writer.println(entry.toCSVLine());
        } catch (IOException e) {
            IO.println("Could not save entry to " + ENTRIES_FILE + ": " + e.getMessage());
        }
    }


}
