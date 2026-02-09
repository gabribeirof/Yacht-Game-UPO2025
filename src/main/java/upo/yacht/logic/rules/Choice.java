package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/*Qualquer combinacao de dados.
Ex: [1, 6, 5, 3, 2] --> pontos = 17*/
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