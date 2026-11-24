import java.time.LocalDate;
import java.util.ArrayList;
public class Main {

    private static void printMenu() {
        IO.println("\n=== Nutrition Tracker ===");
        IO.println("1. Add food");
        IO.println("0. Exit");
    }
    public static void main(String[] args) {
        //Temporary list. so addFood has place to put entries. DailyLog will replave soon
        ArrayList<FoodEntry> entries = new ArrayList<>();
        boolean namra = true;

        while (namra) {
            printMenu();

            //read whole line. Not parsing a number so when a letter is typed program doesnt crash
            String input = IO.readln().trim();

            if (input.equals("1")) {
                addFood(entries);
            } else if (input.equals("0")) {
                namra = false;
            } else {
                IO.println("Invalid choice. Please enter a number from the menu.");
            }

        }
        IO.println("Goodbye!");
    }

    private static void addFood(ArrayList<FoodEntry> entries) {
        String name = IO.readln("Food name: ");
        double calories = readNonNegativeNumber("Calories: ");
        double protein = readNonNegativeNumber("Protein (g): ");
        double carbs = readNonNegativeNumber("Carbs (g): ");
        double fat = readNonNegativeNumber("Fat (g): ");
        double sugar = readNonNegativeNumber("Sugar (g): ");
        double sodiumMg = readNonNegativeNumber("Sodium (mg): ");
        double potassiumMg = readNonNegativeNumber("Potassium (mg): ");

        // FoodEntry validates name, so menu can report problem
        try {
            entries.add(new FoodEntry(LocalDate.now(), name, calories, protein,
                    carbs, fat, sugar, sodiumMg, potassiumMg));
            IO.println("Added " + name.trim() + ".");
        } catch (IllegalArgumentException e) {
            IO.println("Invalid input: " + e.getMessage() + " Please try again.");
        }
    }

    // Keeps asking until user types a valid number, one typo doesn't throw away the whole entry.
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
