package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Full House scoring rule for Yacht dice game.
///
/// This rule scores when the dice show exactly 3 of one value
/// and exactly 2 of another value. The score is the sum of all
/// five dice. If this pattern is not present, the score is 0.
///
/// # Example
/// For dice values `[2, 2, 5, 5, 5]`, the score would be 19 (sum of all dice).
public class FullHouse implements ScoringRule {
    String categoryName = "Full House";

    /// Returns the name of this scoring category.
    ///
    /// @return the category name "Full House"
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score for Full House.
    ///
    /// Uses `DiceUtils.getDiceFrequency()` to count occurrences of each
    /// die value. Checks if exactly one value appears 3 times and another
    /// value appears 2 times. If both conditions are met, returns the sum
    /// of all dice; otherwise returns 0.
    ///
    /// @param diceValues array of dice values to score
    /// @return the sum of all dice if Full House pattern is present, otherwise 0
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        boolean haveThree = false;
        boolean haveTwo = false;

        for (int i = 1; i < freq.length; i++) {
            if (freq[i] == 3) {
                haveThree = true;
            } else if (freq[i] == 2) {
                haveTwo = true;
            }
        }

        if (haveThree && haveTwo) {
            return DiceUtils.sumDice(diceValues);
        }
        return 0;
    }
}
