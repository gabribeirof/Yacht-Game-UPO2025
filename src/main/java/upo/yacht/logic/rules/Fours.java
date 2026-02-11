package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Fours scoring rule for Yacht dice game.
///
/// This rule calculates the score by summing only the dice
/// that show a value of 4. Other dice values are ignored.
///
/// # Example
/// For dice values `[1, 4, 4, 1, 6]`, the score would be 8 (two 4's).
public class Fours implements ScoringRule {
    String categoryName = "Fours";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Fours"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score by summing all dice showing 4.
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. The frequency array has 7 positions where each index
    /// represents a die value (1-6), and the value at that index indicates
    /// how many times that number appeared.
    ///
    /// @param diceValues array of dice values to score
    /// @return the sum of all 4's (frequency of 4's multiplied by 4)
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        return freq[4] * 4;
    }
}
