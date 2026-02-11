package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Yacht scoring rule for Yacht dice game.
///
/// This rule scores when all five dice show the same value.
/// When this condition is met, the score is a fixed 50 points
/// regardless of which value appears. If not all dice match,
/// the score is 0.
///
/// # Example
/// For dice values `[1, 1, 1, 1, 1]`, the score would be 50.
public class YachtRule implements ScoringRule {
    String categoryName = "Yacht";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Yacht"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score for Yacht (five of a kind).
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. Checks if any value appears exactly 5 times. If found,
    /// returns the fixed score of 50 points.
    ///
    /// @param diceValues array of dice values to score
    /// @return 50 if all five dice match, otherwise 0
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        for (int i = 1; i < freq.length; i++) {
            if (freq[i] == 5) {
                return 50;
            }
        }
        return 0;
    }
}
