package upo.yacht.logic.rules;
import upo.yacht.util.DiceUtils;
//Quando tenho 5 dados iguais
//Ex: [1, 1, 1, 1, 1] --> pontos = 50
public class YachtRule implements ScoringRule {
    String categoryName = "Yacht";
    @Override
    public String getName() {
        return categoryName;
    }
    @Override
    public int calculate(int[] diceValues) {
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        for (int i = 1; i < freq.length; i++) {
            if (freq[i] == 5) {
                return 50;
            }
        }
        return 0;
    }
}