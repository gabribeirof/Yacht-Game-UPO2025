package upo.yacht.model;

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
     * @param points the points to register
     * @throws IllegalStateException if the category is already used
     * @throws IllegalArgumentException if the category index is invalid
     */
    public void registerScore(int categoryIndex, int points) {
        // Validate index
        if (categoryIndex < 0 || categoryIndex >= NUM_CATEGORIES) {
            throw new IllegalArgumentException(
                    "Invalid category index: " + categoryIndex +
                            ". Must be between 0 and " + (NUM_CATEGORIES - 1)
            );
        }

        // Check if already used
        if (categoryUsed[categoryIndex]) {
            throw new IllegalStateException(
                    "Category " + categoryIndex + " is already filled!"
            );
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
            return false;
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
            return 0;
        }
        return scores[categoryIndex];
    }

    /**
     * Calculates the total score across all filled categories.
     *
     * @return the sum of all scores
     */
    public int getTotalScore() {
        int total = 0;
        for (int score : scores) {
            total += score;
        }
        return total;
    }

    /**
     * Displays the current scoreboard state in the console.
     * Shows which categories are filled and which are available.
     */
    public void displayBoard() {
        System.out.println("\n|| ========== SCOREBOARD ==========");

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            String categoryName = getCategoryName(i);

            if (categoryUsed[i]) {
                // Category already filled - show the score with checkmark
                System.out.printf("|| [%2d] %-20s : %3d ✓\n",
                        i, categoryName, scores[i]);
            } else {
                // Category available - show as empty
                System.out.printf("|| [%2d] %-20s : ---\n",
                        i, categoryName);
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

    /**
     * Counts how many categories have been filled.
     *
     * @return the number of used categories
     */
    public int getUsedCategoriesCount() {
        int count = 0;
        for (boolean used : categoryUsed) {
            if (used) count++;
        }
        return count;
    }

    /**
     * Checks if the scoreboard is complete (all categories filled).
     *
     * @return true if all 12 categories are used
     */
    public boolean isComplete() {
        return getUsedCategoriesCount() == NUM_CATEGORIES;
    }
}