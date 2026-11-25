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
    private static final String GOALS_FILE = "data/goals.csv";

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

    // Returns null when no goals file exists yet, so Main can tell "never set" apart from "set to zero".
    public static Goals loadGoals() {
        File file = new File(GOALS_FILE);
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null) {
                return null;
            }
            return Goals.fromCSVLine(line);
        } catch (IOException | IllegalArgumentException e) {
            IO.println("Could not read " + GOALS_FILE + ": " + e.getMessage());
            return null;
        }
    }

    // Overwrites the file (no append flag) because there is only ever one current set of goals.
    public static void saveGoals(Goals goals) {
        new File(DATA_FOLDER).mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(GOALS_FILE))) {
            pw.println(goals.toCSVLine());
        } catch (IOException e) {
            IO.println("Could not save goals to " + GOALS_FILE + ": " + e.getMessage());
        }
    }


}
