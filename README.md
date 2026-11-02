# Nutrition Tracker

A Java command-line tool for logging food and tracking macros, sugar, sodium, and potassium against daily goals.

## Project Goal

Most nutrition apps focus on calories and bury or paywall micronutrients like sodium, potassium, and sugar. I want a lightweight tool that lets me log foods quickly, set daily targets, and see how much I have left each day.

## MVP Scope

Version 1.0 includes:
- Add a food entry (name, calories, protein, carbs, fat, sugar, sodium, potassium)
- View today's totals for every nutrient
- Set and save daily goals
- Show remaining amounts against each goal (limits for sugar and sodium, minimum targets for potassium and protein)
- Save and load data from CSV files between runs

## Future Plans (V2.0+)

- Saved list of frequently eaten foods for quick logging
- Edit or delete past entries
- Weekly summaries and averages
- Warnings when a limit is exceeded
- Export history to CSV

## Tech Stack

- **Language:** Java 17
- **Libraries:** none external, only the Java standard library ( `java.time.LocalDate`, `ArrayList`)
- **Storage:** CSV files (`entries.csv`, `goals.csv`)
- **Version control:** Git and GitHub

## Documentation

- [Eisenhower Matrix](docs/eisenhower-matrix.pdf)

## Build Plan

1. Repo setup and Main skeleton
2. FoodEntry class
3. Menu loop and add-food with validation
4. DailyLog and daily totals view
5. CSV persistence for entries
6. Goals class and set-goals menu
7. Remaining amounts against goals
8. Formatting and error handling
9. Tests, README, and demo prep

## How to Run

```bash
git clone https://github.com/AzielBM34/nutrition-tracking.git
cd nutrition-tracking
javac -d out src/*.java
java -cp out Main
```

## Status

In development. Currently on step 1 of the build plan.
