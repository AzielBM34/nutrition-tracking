import java.time.LocalDate;

public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testRejectsEmptyName();
        testRejectsNegativeNutrient();
        testCsvRoundTrip();
        testTotalsOnlyCountTheGivenDate();
        testFromCSVLineRejectsDamagedLine();

        IO.println("\n" + passed + " passed, " + failed + " failed");
    }

    public static void check(String testName, boolean condition) {
        if (condition) {
            passed++;
            IO.println("PASS: " + testName);
        } else {
            failed++;
            IO.println("FAIL: " + testName);
        }
    }

    private static void testRejectsEmptyName() {
        boolean threw = false;
        try {
            new FoodEntry(LocalDate.now(), "   ", 1, 1, 1, 1, 1, 1, 1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("Empty name is rejected", threw);
    }

    private static void testRejectsNegativeNutrient() {
        boolean threw = false;
        try {
            new FoodEntry(LocalDate.now(), "Apple", -5, 0, 0, 0, 0, 0, 0);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("Negative nutrient is rejected", threw);
    }

    //Saving then loading must give back same values, or entries would change between runs.
    private static void testCsvRoundTrip() {
        FoodEntry original = new FoodEntry(LocalDate.of(2026, 11, 24), "Banana, large",
                105, 1.3, 27, 0.4, 14, 1, 422);
        FoodEntry loaded = FoodEntry.fromCSVLine(original.toCSVLine());
        check("CSV round trip keeps sodium", loaded.getSodiumMg() == 1);
        check("CSV round trip keeps potassium", loaded.getPotassiumMg() == 422);
        check("CSV round trip replaces comma in name", loaded.getName().equals("Banana  large"));
    }

    private static void testTotalsOnlyCountTheGivenDate() {
        DailyLog log = new DailyLog();
        LocalDate today = LocalDate.of(2026, 11, 24);
        log.addEntry(new FoodEntry(today, "A", 100, 0, 0, 0, 0, 10, 0));
        log.addEntry(new FoodEntry(today, "B", 200, 0, 0, 0, 0, 20, 0));
        log.addEntry(new FoodEntry(today.minusDays(1), "Old", 999, 0, 0, 0, 0, 999, 0));
        check("Calories total only today", log.totalFor(today, "calories") == 300);
        check("Sodium total only today", log.totalFor(today, "sodium") == 30);
    }

    private static void testFromCSVLineRejectsDamagedLine() {
        boolean threw = false;
        try {
            FoodEntry.fromCSVLine("hello");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("Damaged CSV line is rejected", threw);
    }
}
