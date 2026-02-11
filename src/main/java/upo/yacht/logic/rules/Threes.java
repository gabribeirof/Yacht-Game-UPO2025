package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Threes scoring rule for Yacht dice game.
///
/// This rule calculates the score by summing only the dice
/// that show a value of 3. Other dice values are ignored.
///
/// # Example
/// For dice values `[3, 3, 5, 1, 3]`, the score would be 9 (three 3's).
public class Threes implements ScoringRule {
    String categoryName = "Threes";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Threes"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score by summing all dice showing 3.
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. The frequency array has 7 positions where each index
    /// represents a die value (1-6), and the value at that index indicates
    /// how many times that number appeared.
    ///
    /// @param diceValues array of dice values to score
    /// @return the sum of all 3's (frequency of 3's multiplied by 3)
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        return freq[3] * 3;
    }
}
