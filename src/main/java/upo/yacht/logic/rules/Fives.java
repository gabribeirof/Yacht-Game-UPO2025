package upo.yacht.logic.rules;

import upo.yacht.util.DiceUtils;

//Soma todos os dados com valor 5.
//Ex: [5, 5, 5, 1, 6] -> pontos = 15
public class Fives implements ScoringRule {
    String categoryName = "Fives";

    @Override
    public String getName() {
        return categoryName;
    }

    @Override
    public int calculate(int[] diceValues) {
        //getDiceFrequency gera um array de 7 posiçoes, onde cada index representa
        //um valor do dado, e o numero que estiver nesse index representa quantas vezes
        // aquele valor apareceu nos dados.
        //Ex: se eu acessar freq[1] o numero que esta ali eh a quantidade de vezes que eu tenho 1
        //    nos dados lançados.
        int[] freq = DiceUtils.getDiceFrequency(diceValues);
        return freq[5] * 5;
    }
}