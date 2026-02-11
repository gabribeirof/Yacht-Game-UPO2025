package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Ones scoring rule for Yacht dice game.
///
/// This rule calculates the score by summing only the dice
/// that show a value of 1. Other dice values are ignored.
///
/// # Example
/// For dice values `[1, 2, 5, 1, 6]`, the score would be 2 (two 1's).
public class Ones implements ScoringRule {
    String categoryName = "Ones";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Ones"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score by summing all dice showing 1.
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. The frequency array has 7 positions where each index
    /// represents a die value (1-6), and the value at that index indicates
    /// how many times that number appeared.
    ///
    /// @param diceValues array of dice values to score
    /// @return the count of all 1's (frequency of 1's, no multiplication needed)
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        return freq[1]; // Multiplication by 1 is implicit
    }
}
