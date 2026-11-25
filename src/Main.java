import java.time.LocalDate;
import java.util.ArrayList;
public class Main {

    private static void printMenu() {
        IO.println("\n=== Nutrition Tracker ===");
        IO.println("1. Add food");
        IO.println("2. View Today");
        IO.println("0. Exit");
    }

    private static void viewToday(DailyLog log) {
        LocalDate today = LocalDate.now();
        ArrayList<FoodEntry> todaysEntries = log.getEntriesFor(today);

        if (todaysEntries.isEmpty()) {
            IO.println("No food logged today.");
            return;
        }

        IO.println("\n--- Today's Foods ---");
        for (int i = 0; i < todaysEntries.size(); i++) {
            IO.println((i + 1) + ". " + todaysEntries.get(i).getName()
                    + " (" + todaysEntries.get(i).getCalories() + " cal)");
        }

        IO.println("Calories: " + log.totalCalories(today));
        IO.println("Protein: " + log.totalProtein(today) + " g");
        IO.println("Carbs: " + log.totalCarbs(today) + " g");
        IO.println("Fat: " + log.totalFat(today) + " g");
        IO.println("Sugar: " + log.totalSugar(today) + " g");
        IO.println("Sodium: " + log.totalSodiumMg(today) + " mg");
        IO.println("Potassium: " + log.totalPotassiumMg(today) + " mg");
    }
    public static void main(String[] args) {
        DailyLog log = new DailyLog();
        //Reload yesterdays and earlier entries so log survives closing the program
        ArrayList<FoodEntry> savedEntries = EntryStorage.loadEntries();
        for (int i = 0; i < savedEntries.size(); i++) {
            log.addEntry(savedEntries.get(i));
        }

        boolean namra = true;

        while (namra) {
            printMenu();

            //read whole line. Not parsing a number so when a letter is typed program doesnt crash
            String input = IO.readln().trim();

            if (input.equals("1")) {
                addFood(log);
            } else if (input.equals("0")) {
                namra = false;
            } else if (input.equals("2")) {
                viewToday(log);
            } else {
                IO.println("Invalid choice. Please enter a number from the menu.");
            }

        }
        IO.println("Goodbye!");
    }

    private static void addFood(DailyLog log) {
        String name = IO.readln("Food name: ");
        double calories = readNonNegativeNumber("Calories: ");
        double protein = readNonNegativeNumber("Protein (g): ");
        double carbs = readNonNegativeNumber("Carbs (g): ");
        double fat = readNonNegativeNumber("Fat (g): ");
        double sugar = readNonNegativeNumber("Sugar (g): ");
        double sodiumMg = readNonNegativeNumber("Sodium (mg): ");
        double potassiumMg = readNonNegativeNumber("Potassium (mg): ");

        //FoodEntry validates name, so menu can report problem
        try {
            FoodEntry entry = new FoodEntry(LocalDate.now(), name, calories, protein, carbs, fat,
                    sugar, sodiumMg, potassiumMg);
            log.addEntry(entry);
            EntryStorage.saveEntry(entry);
            IO.println("Added " + name.trim() + ".");
        } catch (IllegalArgumentException e) {
            IO.println("Invalid input: " + e.getMessage() + " Please try again.");
        }
    }

    //Keeps asking until user types a valid number, one typo doesn't throw away the whole entry
    private static double readNonNegativeNumber(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(IO.readln(prompt).trim());
                if (value >= 0) {
                    return value;
                }
                IO.println("Value cannot be negative.");
            } catch (NumberFormatException e) {
                IO.println("Please enter a number.");
            }
        }
    }
}
