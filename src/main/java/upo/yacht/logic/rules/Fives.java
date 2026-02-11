package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Fives scoring rule for Yacht dice game.
///
/// This rule calculates the score by summing only the dice
/// that show a value of 5. Other dice values are ignored.
///
/// # Example
/// For dice values `[5, 5, 5, 1, 6]`, the score would be 15 (three 5's).
public class Fives implements ScoringRule {
    String categoryName = "Fives";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Fives"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score by summing all dice showing 5.
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. The frequency array has 7 positions where each index
    /// represents a die value (1-6), and the value at that index indicates
    /// how many times that number appeared.
    ///
    /// @param diceValues array of dice values to score
    /// @return the sum of all 5's (frequency of 5's multiplied by 5)
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        return freq[5] * 5;
    }
}
