import java.time.LocalDate;
import java.util.ArrayList;

public class DailyLog {

    private final ArrayList<FoodEntry> entries = new ArrayList<>();

    public void addEntry(FoodEntry entry) {
        entries.add(entry);
    }

    //Returns entries for one date, "view today" ignores older days once entries saved to file
    public ArrayList<FoodEntry> getEntriesFor(LocalDate date) {
        ArrayList<FoodEntry> matches = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getDate().equals(date)) {
                matches.add(entries.get(i));
            }
        }
        return matches;
    }
    public double totalCalories(LocalDate date) {
        ArrayList<FoodEntry> todaysEntries = getEntriesFor(date);
        double total = 0;
        for (int i = 0; i < todaysEntries.size(); i++) {
            total += todaysEntries.get(i).getCalories();
        }
        return total;
    }

    public double totalProtein(LocalDate date) {
        ArrayList<FoodEntry> todaysEntries = getEntriesFor(date);
        double total = 0;
        for (int i = 0; i < todaysEntries.size(); i++) {
            total += todaysEntries.get(i).getProtein();
        }
        return total;
    }

    public double totalCarbs(LocalDate date) {
        ArrayList<FoodEntry> todaysEntries = getEntriesFor(date);
        double total = 0;
        for (int i = 0; i < todaysEntries.size(); i++) {
            total += todaysEntries.get(i).getCarbs();
        }
        return total;
    }

    public double totalFat(LocalDate date) {
        ArrayList<FoodEntry> todaysEntries = getEntriesFor(date);
        double total = 0;
        for (int i = 0; i < todaysEntries.size(); i++) {
            total += todaysEntries.get(i).getFat();
        }
        return total;
    }

    public double totalSugar(LocalDate date) {
        ArrayList<FoodEntry> todaysEntries = getEntriesFor(date);
        double total = 0;
        for (int i = 0; i < todaysEntries.size(); i++) {
            total += todaysEntries.get(i).getSugar();
        }
        return total;
    }

    public double totalSodiumMg(LocalDate date) {
        ArrayList<FoodEntry> todaysEntries = getEntriesFor(date);
        double total = 0;
        for (int i = 0; i < todaysEntries.size(); i++) {
            total += todaysEntries.get(i).getSodiumMg();
        }
        return total;
    }

    public double totalPotassiumMg(LocalDate date) {
        ArrayList<FoodEntry> todaysEntries = getEntriesFor(date);
        double total = 0;
        for (int i = 0; i < todaysEntries.size(); i++) {
            total += todaysEntries.get(i).getPotassiumMg();
        }
        return total;
    }
}
