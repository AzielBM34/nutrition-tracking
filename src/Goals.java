public class Goals {

    private final double calories;
    private final double protein;
    private final double carbs;
    private final double fat;
    private final double sugar;
    private final double sodiumMg;
    private final double potassiumMg;

    public Goals(double calories, double protein, double carbs, double fat, double sugar,
                 double sodiumMg, double potassiumMg) {
        //Negative target makes no sense and would break "remaining" math later
        if (calories < 0 || protein < 0 || carbs < 0 || fat < 0 || sugar < 0 || sodiumMg < 0
                || potassiumMg < 0) {
            throw new IllegalArgumentException("Goals cannot be negative.");
        }
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.sugar = sugar;
        this.sodiumMg = sodiumMg;
        this.potassiumMg = potassiumMg;
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

    //One line, same field order as constructor, so goals.csv is easy to read
    public String toCSVLine() {
        return calories + "," + protein + "," + carbs + "," + fat + ","
                + sugar + "," + sodiumMg + "," + potassiumMg;
    }

    //Throws IllegalArgumentException on damaged line so storage code can fall back to no goals.
    public static Goals fromCSVLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Expected 7 fields but found " + parts.length);
        }
        return new Goals(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4]), Double.parseDouble(parts[5]),
                Double.parseDouble(parts[6]));
    }
}
