package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

// Choice  rule
// the sum of any order/value of dice
// Ex: [1, 6, 5, 3, 2] --> pontos = 17
public class Choice implements ScoringRule {
    String categoryName = "Choice";

    @Override
    public String getName() {
        return categoryName;
    }

    @Override
    public int calculate(int[] diceValues) {
        return DiceUtils.sumDice(diceValues);
    }
}