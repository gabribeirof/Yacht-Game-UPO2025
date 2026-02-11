package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Twos scoring rule for Yacht dice game.
///
/// This rule calculates the score by summing only the dice
/// that show a value of 2. Other dice values are ignored.
///
/// # Example
/// For dice values `[1, 2, 2, 1, 6]`, the score would be 4 (two 2's).
public class Twos implements ScoringRule {
    String categoryName = "Twos";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Twos"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score by summing all dice showing 2.
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. The frequency array has 7 positions where each index
    /// represents a die value (1-6), and the value at that index indicates
    /// how many times that number appeared.
    ///
    /// @param diceValues array of dice values to score
    /// @return the sum of all 2's (frequency of 2's multiplied by 2)
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        return freq[2] * 2;
    }
}
