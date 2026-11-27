import java.time.LocalDate;
import java.util.ArrayList;
public class Main {

    private static void printMenu() {
        IO.println("\n=== Nutrition Tracker ===");
        IO.println("1. Add food");
        IO.println("2. View Today");
        IO.println("3. Set Goals");
        IO.println("0. Exit");
    }

    private static void viewToday(DailyLog log, Goals goals) {
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

        //Goals are null until user sets them, every goal lookup is guarded by flag
        if (goals == null) {
            // No goals yet, so show plain totals instead of reading from a goals object that doesn't exist.
            IO.println("\n--- Totals ---");
            printTotal("Calories", log.totalFor(today, "calories"), "cal");
            printTotal("Protein", log.totalFor(today, "protein"), "g");
            printTotal("Carbs", log.totalFor(today, "carbs"), "g");
            printTotal("Fat", log.totalFor(today, "fat"), "g");
            printTotal("Sugar", log.totalFor(today, "sugar"), "g");
            printTotal("Sodium", log.totalFor(today, "sodium"), "mg");
            printTotal("Potassium", log.totalFor(today, "potassium"), "mg");
            IO.println("\nNo goals set yet. Choose 3 from the menu to set them.");
        } else {
            IO.println("\n--- Totals vs Goals ---");
            printNutrient("Calories", log.totalFor(today, "calories"), goals.getCalories(), "cal");
            printNutrient("Protein", log.totalFor(today, "protein"), goals.getProtein(), "g");
            printNutrient("Carbs", log.totalFor(today, "carbs"), goals.getCarbs(), "g");
            printNutrient("Fat", log.totalFor(today, "fat"), goals.getFat(), "g");
            printNutrient("Sugar", log.totalFor(today, "sugar"), goals.getSugar(), "g");
            printNutrient("Sodium", log.totalFor(today, "sodium"), goals.getSodiumMg(), "mg");
            printNutrient("Potassium", log.totalFor(today, "potassium"), goals.getPotassiumMg(), "mg");
        }
    }

    private static Goals setGoals() {
        double calories = readNonNegativeNumber("Calorie goal: ");
        double protein = readNonNegativeNumber("Protein goal (g): ");
        double carbs = readNonNegativeNumber("Carb goal (g): ");
        double fat = readNonNegativeNumber("Fat goal (g): ");
        double sugar = readNonNegativeNumber("Sugar goal (g): ");
        double sodiumMg = readNonNegativeNumber("Sodium goal (mg): ");
        double potassiumMg = readNonNegativeNumber("Potassium goal (mg): ");

        Goals goals = new Goals(calories, protein, carbs, fat, sugar, sodiumMg, potassiumMg);
        EntryStorage.saveGoals(goals);
        IO.println("Goals saved.");
        return goals;
    }

    private static void printTotal(String label, double total, String unit) {
        IO.println(label + ": " + String.format("%.1f", total) + " " + unit);
    }

    //One place formats every nutrient line, all seven stay consistent
    private static void printNutrient(String label, double total, double goal, String unit) {
        double remaining = goal - total;
        String status;
        if (remaining >= 0) {
            status = String.format("%.1f", remaining) + " left";
        } else {
            status = String.format("%.1f", -remaining) + " over";
        }
        IO.println(label + ": " + String.format("%.1f", total) + " / "
                + String.format("%.1f", goal) + " " + unit + " (" + status + ")");
    }

    public static void main(String[] args) {
        DailyLog log = new DailyLog();
        //Null means the user has never set goals, "remaining" display must handle
        Goals goals = EntryStorage.loadGoals();
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
                viewToday(log, goals);
            } else if (input.equals("3")) {
                goals = setGoals();
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
