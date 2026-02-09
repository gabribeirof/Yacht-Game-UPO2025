package upo.yacht.model;

import upo.yacht.exceptions.YachtGameException;
import upo.yacht.logic.Scorer;
import upo.yacht.util.DiceUtils;

/**
 * Manages the scoring for a single player in Yacht.
 * Tracks which categories have been used and their scores.
 */
public class Scoreboard {
    private static final int NUM_CATEGORIES = 12;

    // Stores the score for each category (index 0-11)
    private final int[] scores;

    // Tracks whether each category has been filled
    private final boolean[] categoryUsed;

    /**
     * Creates an empty scoreboard with all categories available.
     */
    public Scoreboard() {
        this.scores = new int[NUM_CATEGORIES];
        this.categoryUsed = new boolean[NUM_CATEGORIES]; // defaults to false
    }

    /**
     * Registers a score for a specific category.
     *
     * @param categoryIndex the index of the category (0-11)
     * @param points        the points to register
     * @throws IllegalStateException    if the category is already used
     * @throws IllegalArgumentException if the category index is invalid
     */
    public void registerScore(int categoryIndex, int points) throws YachtGameException {
        // Java exige que você declare explicitamente que esse metodo pode ser que lance esse erro.
        // Validate index
        if (categoryIndex < 0 || categoryIndex >= NUM_CATEGORIES) {
            throw new IllegalArgumentException("Invalid category index: " + categoryIndex + ". Must " +
                    "be between 0 and " + (NUM_CATEGORIES - 1));
        }

        // Check if already used
        if (categoryUsed[categoryIndex]) {
            throw new YachtGameException("Category " + getCategoryName(categoryIndex) + " is already filled!");
        }

        // Register the score
        scores[categoryIndex] = points;
        categoryUsed[categoryIndex] = true;
    }

    /**
     * Checks if a category has been used.
     *
     * @param categoryIndex the index to check
     * @return true if the category has been filled
     */
    public boolean isCategoryUsed(int categoryIndex) {
        if (categoryIndex < 0 || categoryIndex >= NUM_CATEGORIES) {
            throw new IllegalArgumentException("Invalid category index: " + categoryIndex + ". Must " +
                    "be between 0 and " + (NUM_CATEGORIES - 1));
        }
        return categoryUsed[categoryIndex];
    }

    /**
     * Gets the score for a specific category.
     *
     * @param categoryIndex the category index
     * @return the score, or 0 if not yet filled
     */
    public int getScore(int categoryIndex) {
        if (categoryIndex < 0 || categoryIndex >= NUM_CATEGORIES) {
            throw new IllegalArgumentException("Invalid category index: " + categoryIndex + ". Must " +
                    "be between 0 and " + (NUM_CATEGORIES - 1));
        }
        return scores[categoryIndex];
    }

    /**
     * Calculates the total score across all filled categories.
     *
     * @return the sum of all scores
     */
    public int getTotalScore() {
        return DiceUtils.sumDice(scores);
    }

    /**
     * Displays the current scoreboard state in the console.
     * Shows which categories are filled and which are available.
     */
    public void displayBoard(int[] CurrentDice) {
        System.out.println("\n|| ========== SCOREBOARD ==========");

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            String categoryName = getCategoryName(i);

            if (categoryUsed[i]) {
                // Category already filled - show the score with checkmark
                System.out.printf("[%2d] %-20s (Points: %3d) : %3d ✓\n",
                        i, categoryName, 0, scores[i]);
            } else {
                if (CurrentDice != null) {
                    // Category available - show as empty
                    System.out.printf("[%2d] %-20s (Points: %3d) : ---\n",
                            i, categoryName, Scorer.getScore(i, CurrentDice));
                } else {
                    // Caso não queira mostrar previsões
                    System.out.printf("|| [%2d] %-20s : ---\n", i, categoryName);
                }
            }
        }

        System.out.println("|| --------------------------------");
        System.out.printf("|| TOTAL                    : %3d\n", getTotalScore());
        System.out.println("|| ================================\n");

    }

    /**
     * Helper method to get category names by index.
     * Uses the Scorer class to maintain consistency.
     *
     * @param index the category index
     * @return the category name
     */
    private String getCategoryName(int index) {
        // We'll use Scorer to get the name, since it already has the rule array
        return upo.yacht.logic.Scorer.getCategoryName(index);
    }

    /*
     * Counts how many categories have been filled.
     *
     * @return the number of used categories
     */
    //public int getUsedCategoriesCount() {
    //    int count = 0;
    //    for (boolean used : categoryUsed) {
    //        if (used) count++;
    //    }
    //    return count;
    //}

    //
     //* Checks if the scoreboard is complete (all categories filled).
     //*
     //* @return true if all 12 categories are used
     //*/
    // public boolean isComplete() {
    //    return getUsedCategoriesCount() == NUM_CATEGORIES;
    //}

    /**
     * Gets a formatted string representation of the scoreboard.
     * Useful for file saving.
     *
     * @return formatted scoreboard string
     */
    public String getFormattedScoreboard() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scoreboard:\n");
        sb.append("-".repeat(30)).append("\n");

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            String categoryName = getCategoryName(i);
            String scoreStr = categoryUsed[i] ? String.valueOf(scores[i]) : "---";
            String checkmark = categoryUsed[i] ? "✓" : " ";

            sb.append(String.format("[%s] %-18s : %3s %s%n",
                    checkmark, categoryName, scoreStr, checkmark));
        }

        sb.append("-".repeat(30)).append("\n");
        sb.append(String.format("TOTAL                : %3d%n", getTotalScore()));
        sb.append("=".repeat(30)).append("\n");

        return sb.toString();
    }
}