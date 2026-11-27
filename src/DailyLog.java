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
    //One loop for every nutrient, adding a new nutrient later means one new case, not new method.
    public double totalFor(LocalDate date, String nutrient) {
        ArrayList<FoodEntry> todaysEntries = getEntriesFor(date);
        double total = 0;
        for (int i = 0; i < todaysEntries.size(); i++) {
            FoodEntry entry = todaysEntries.get(i);
            switch (nutrient) {
                case "calories":
                    total += entry.getCalories();
                    break;
                case "protein":
                    total += entry.getProtein();
                    break;
                case "carbs":
                    total += entry.getCarbs();
                    break;
                case "fat":
                    total += entry.getFat();
                    break;
                case "sugar":
                    total += entry.getSugar();
                    break;
                case "sodium":
                    total += entry.getSodiumMg();
                    break;
                case "potassium":
                    total += entry.getPotassiumMg();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown nutrient: " + nutrient);
            }
        }
        return total;
    }
}
