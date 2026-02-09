package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/* Full House: Quando tenho 2 dados de um tipo e 3 de outros. Os pontos eh a soma de todos os dados.
Ex: [2, 2, 5, 5, 5] -> pontos = 19 */
public class FullHouse implements ScoringRule {
    String categoryName = "Full House";

    @Override
    public String getName() {
        return categoryName;
    }

    @Override
    public int calculate(int[] diceValues) {
        // 1. Pegamos a frequência de cada face (1 a 6)
        int[] freq = DiceUtils.getDiceFrequency(diceValues);

        boolean haveThree = false;
        boolean haveTwo = false;

        // 2. Percorremos o array de frequências (índices 1 a 6)
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
        // Caso contrário, zero pontos
        return 0;
    }
}