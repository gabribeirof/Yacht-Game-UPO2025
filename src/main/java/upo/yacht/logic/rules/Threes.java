package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

/* Categoria Threes: Soma todos os dados com valor 3.
Ex: [3, 3, 5, 1, 3] -> pontos = 9
 */

public class Threes implements ScoringRule {
    String categoryName = "Threes";

    @Override
    public String getName() {
        return categoryName;
    }

    @Override
    public int calculate(int[] diceValues) {
        /* getDiceFrequency gera um array de 7 posicoes, onde cada index representa
        um valor do dado, e o numero que estiver nesse index representa quantas vezes
         aquele valor apareceu nos dados.
        Ex: se eu acessar freq[1] o numero que esta ali eh a quantidade de vezes que eu tenho 1
            nos dados lancados. */
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        return freq[3] * 3;
    }

}