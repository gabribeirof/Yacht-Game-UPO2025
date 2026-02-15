package upo.yacht.model;

import upo.yacht.exceptions.YachtGameException;
import upo.yacht.logic.Scorer;
import upo.yacht.util.DiceUtils;

/// Manages the scoring for a single player in Yacht.
///
/// This class tracks which categories have been used and their scores
/// across all 12 scoring categories. Each category can only be filled once,
/// and the class provides methods to register scores, check availability,
/// and display the current state.
public class Scoreboard {
    private static final int NUM_CATEGORIES = 12;
    private final int[] scores;
    private final boolean[] categoryUsed;

    /// Creates an empty scoreboard with all categories available.
    ///
    /// Initializes arrays to store scores and track usage for all 12 categories.
    /// All categories start as unused with a score of 0.
    public Scoreboard() {
        this.scores = new int[NUM_CATEGORIES];
        this.categoryUsed = new boolean[NUM_CATEGORIES];
    }

    /// Registers a score for a specific category.
    ///
    /// Validates that the category index is valid and that the category
    /// has not been used previously. Once registered, the category is
    /// marked as used and cannot be changed.
    ///
    /// @param categoryIndex the index of the category (0-11)
    /// @param points        the points to register for this category
    /// @throws IllegalArgumentException if the category index is invalid
    /// @throws YachtGameException       if the category is already filled
    public void registerScore(int categoryIndex, int points) throws YachtGameException {
        if (categoryIndex < 0 || categoryIndex >= NUM_CATEGORIES) {
            throw new IllegalArgumentException("Invalid category index: " + categoryIndex + ". Must " +
                    "be between 0 and " + (NUM_CATEGORIES - 1));
        }
        if (categoryUsed[categoryIndex]) {
            throw new YachtGameException("Category " + getCategoryName(categoryIndex) + " is already filled!");
        }
        scores[categoryIndex] = points;
        categoryUsed[categoryIndex] = true;
    }

    /// Checks if a category has been used.
    ///
    /// @param categoryIndex the index to check (0-11)
    /// @return true if the category has been filled, false otherwise
    /// @throws IllegalArgumentException if the category index is invalid
    public boolean isCategoryUsed(int categoryIndex) {
        if (categoryIndex < 0 || categoryIndex >= NUM_CATEGORIES) {
            throw new IllegalArgumentException("Invalid category index: " + categoryIndex + ". Must " +
                    "be between 0 and " + (NUM_CATEGORIES - 1));
        }
        return categoryUsed[categoryIndex];
    }

    /// Gets the score for a specific category.
    ///
    /// @param categoryIndex the category index (0-11)
    /// @return the score for that category, or 0 if not yet filled
    /// @throws IllegalArgumentException if the category index is invalid
    public int getScore(int categoryIndex) {
        if (categoryIndex < 0 || categoryIndex >= NUM_CATEGORIES) {
            throw new IllegalArgumentException("Invalid category index: " + categoryIndex + ". Must " +
                    "be between 0 and " + (NUM_CATEGORIES - 1));
        }
        return scores[categoryIndex];
    }

    /// Calculates the total score across all filled categories.
    ///
    /// @return the sum of all scores in the scoreboard
    public int getTotalScore() {
        return DiceUtils.sumDice(scores);
    }

    /// Displays the current scoreboard state to the console.
    ///
    /// Shows which categories are filled (with checkmarks) and which are available.
    /// For available categories, displays potential points if current dice values
    /// are provided. Includes a running total at the bottom.
    ///
    /// @param CurrentDice optional array of current dice values to show potential scores; null to hide predictions
    public void displayBoard(int[] CurrentDice) {
        System.out.println("\n|| ========== SCOREBOARD ==========");

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            String categoryName = getCategoryName(i);

            if (categoryUsed[i]) {
                System.out.printf("[%2d] %-20s (Points: %3d) : %3d ✓\n",
                        i, categoryName, 0, scores[i]);
            } else {
                if (CurrentDice != null) {
                    System.out.printf("[%2d] %-20s (Points: %3d) : ---\n",
                            i, categoryName, Scorer.getScore(i, CurrentDice));
                } else {
                    System.out.printf("|| [%2d] %-20s : ---\n", i, categoryName);
                }
            }
        }
        System.out.println("|| --------------------------------");
        System.out.printf("|| TOTAL                    : %3d\n", getTotalScore());
        System.out.println("|| ================================\n");
    }

    /// Helper method to get category names by index.
    ///
    /// Uses the Scorer class to maintain consistency across the application.
    ///
    /// @param index the category index (0-11)
    /// @return the category name as a string
    private String getCategoryName(int index) {
        return upo.yacht.logic.Scorer.getCategoryName(index);
    }
}
