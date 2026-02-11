package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/// Represents the **Small Straight** scoring rule.
///
/// A Small Straight consists of 4 dice showing consecutive values (e.g., 1-3-4-5-6).
///
/// # Scoring
/// *   **Condition:** 5 consecutive dice values in any order.
/// *   **Points:** Fixed score of 40 points.
public class SmallStraight implements ScoringRule {
    String categoryName = "Small Straight";

    /// Retrieves the display name of this scoring category.
    ///
    /// @return The string "Small Straight".
    @Override
    public String getName() {
        return categoryName;
    }

    /// Calculates the score based on the dice values provided.
    ///
    /// It determines the frequency of each die face and checks if there are
    /// 4 distinct dice faces counting towards a straight sequence.
    ///
    /// @param diceValues An array of integers representing the values of the 5 dice.
    /// @return **40** if the dice form a Small Straight, otherwise **0**.
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        int sumFreq = 0;
        for (int i = 1; i < freq.length; i++) {
            if (freq[i] == 1) {
                sumFreq++;
            }
            if (sumFreq >= 4) {
                return 30;
            }
        }
        return 0;
    }
}
