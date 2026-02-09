package upo.yacht.logic.rules;
import upo.yacht.util.DiceUtils;
//small straight requer 4 dados em sequencia em qualquer ordem.
//Ex: [1, 3, 4, 2, 8] -> pontos = 30
public class SmallStraight implements ScoringRule {
    String categoryName = "Small Straight";
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
            if (sumFreq >= 4) {
                return 30;
            }
        }
        return 0;
    }
}