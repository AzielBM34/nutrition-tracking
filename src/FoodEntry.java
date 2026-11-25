import java.time.LocalDate;

public class FoodEntry {

    private final LocalDate date;
    private final String name;
    private final double calories, protein, carbs, fat;
    private final double sugar, sodiumMg, potassiumMg;

    public FoodEntry (LocalDate date, String name, double calories, double protein, double carbs,
                      double fat, double sugar, double sodiumMg, double potassiumMg) {
        //rejects bad input. Invalid Entry wont be able to exist in a DailyLog. Or written to CSV
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name of Food can not be Empty.");
        }
        if (calories < 0 || protein < 0 || carbs < 0 || fat < 0 || sugar < 0 || sodiumMg < 0
                || potassiumMg < 0) {
            throw new IllegalArgumentException("Value of Nutrients can not be Negative.");
        }

        this.date = date;
        this.name = name.trim();
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.sugar = sugar;
        this.sodiumMg = sodiumMg;
        this.potassiumMg = potassiumMg;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public double getCalories() {
        return calories;
    }
    public double getProtein() {
        return protein;
    }
    public double getCarbs() {
        return carbs;
    }
    public double getFat() {
        return fat;
    }
    public double getSugar() {
        return sugar;
    }
    public double getSodiumMg() {
        return sodiumMg;
    }
    public double getPotassiumMg() {
        return potassiumMg;
    }

    //Comma seperate CSV fields, comma insdie food name get split into 2 fields
    //Replacing with a space keeps every line at 9 fields
    public String toCSVLine() {
        return date + "," + name.replace(",", " ") + "," + calories + "," + protein + ","
                + carbs + "," + fat + "," + sugar + "," + sodiumMg + "," + potassiumMg;
    }

    //Rebuilds an entry from saved line, ThrowsIllegalArgExcept if line is damaged
    //Storage class skip bad lines, doesnt crash on start
    public static FoodEntry fromCSVLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 9) {
            throw new IllegalArgumentException("Expected 9 fields but found " + parts.length);
        }
        try {
            return new FoodEntry(LocalDate.parse(parts[0]), parts[1],
                    Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
                    Double.parseDouble(parts[4]), Double.parseDouble(parts[5]),
                    Double.parseDouble(parts[6]), Double.parseDouble(parts[7]),
                    Double.parseDouble(parts[8]));
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Bad date: " + parts[0]);
        }
    }
}
