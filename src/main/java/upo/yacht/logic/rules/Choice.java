package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Choice scoring rule for Yacht dice game.
///
/// This rule calculates the score as the sum of all dice values,
/// regardless of their order or specific values.
///
/// # Example
/// For dice values `[1, 6, 5, 3, 2]`, the score would be 17.
public class Choice implements ScoringRule {
    String categoryName = "Choice";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Choice"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score by summing all dice values.
    ///
    /// This rule accepts any combination of dice values and simply
    /// adds them together to produce the final score.
    ///
    /// @param diceValues array of dice values to score
    /// @return the sum of all dice values
    @Override
    public int calculate(int[] diceValues) {
        return DiceUtils.sumDice(diceValues);
    }
}
