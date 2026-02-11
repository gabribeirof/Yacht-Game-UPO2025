package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Four of a Kind scoring rule for Yacht dice game.
///
/// This rule scores when at least four dice show the same value.
/// The score is calculated by summing only those four matching dice
/// (the value multiplied by 4). If no value appears four or more times,
/// the score is 0.
///
/// # Example
/// For dice values `[2, 2, 2, 2, 5]`, the score would be 8 (four 2's).
public class FourOfAKind implements ScoringRule {
    String categoryName = "Four of a Kind";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Four of a Kind"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score for Four of a Kind.
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. Iterates through the frequency array to find if any value
    /// appears 4 or more times. When found, returns that value multiplied by 4.
    ///
    /// @param diceValues array of dice values to score
    /// @return the sum of four matching dice, or 0 if no value appears at least 4 times
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        for (int i = 1; i < freq.length; i++) {
            if (freq[i] >= 4) {
                return i * 4;
            }
        }
        return 0;
    }
}
