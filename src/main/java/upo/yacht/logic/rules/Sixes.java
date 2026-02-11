package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Sixes scoring rule for Yacht dice game.
///
/// This rule calculates the score by summing only the dice
/// that show a value of 6. Other dice values are ignored.
///
/// # Example
/// For dice values `[1, 2, 5, 1, 6]`, the score would be 6 (one 6).
public class Sixes implements ScoringRule {
    String categoryName = "Sixes";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Sixes"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score by summing all dice showing 6.
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. The frequency array has 7 positions where each index
    /// represents a die value (1-6), and the value at that index indicates
    /// how many times that number appeared.
    ///
    /// @param diceValues array of dice values to score
    /// @return the sum of all 6's (frequency of 6's multiplied by 6)
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        return freq[6] * 6;
    }
}