package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;


/**
 * 5 dice in sequency at any order
 * 40 points
 */

public class BigStraight implements ScoringRule {
    String categoryName = "Big Straight";

    @Override
    public String getName() {
        return categoryName;
    }

    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        int sumFreq = 0;

        for (int i = 1; i < freq.length; i++) {
            if (freq[i] == 1) {
                sumFreq++;
            }
            if (sumFreq >= 5) {
                return 40;
            }
        }
        return 0;
    }
}