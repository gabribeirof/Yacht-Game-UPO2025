package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

// Four of a kind rule
// if I have 4 dice with the same value i add then together
// Ex: [2, 2, 2, 2, 5] --> score = 8
public class FourOfAKind implements ScoringRule {
    String categoryName = "Four of a Kind";

    @Override
    public String getName() {
        return categoryName;
    }

    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        for (int i = 1; i < freq.length; i++) {
            // freq[i] é a quantidade de vezes que a face 'i' apareceu
            if (freq[i] >= 4) {
                return i * 4; // Retorna o valor da face vezes 4
            }
        }
        return 0; // Se ninguém apareceu 4 ou mais vezes, zero pontos
    }
}