package upo.yacht.logic;

import upo.yacht.logic.rules.*;

public class Scorer {
    /* Aqui eu nao estou criando objeto de ScoringRule, ate porque ScoringRule eh uma interface
    e interfaces nao sao instanciadas. O que estou fazendo eh criar uma COLECAO de objetos do
    tipo que implementa a Interface.

    Dentro do array ScoringRule terei objetos de classes concretas, onde, no caso, cada elemento
    eh um objeto de uma categoria diferente.

    Cada categoria representa uma classe separada que implementa ScoringRule.
    --> Quando uma classe implementa uma Interface temos uma relacao is-a, ou seja,
    podemos dizer que a classe x EH a interface.
    Ex: a classe YatchRule eh uma ScoringRule
    Com isso, eu posso inserir essa classe em um array de ScoringRules.

    A vantagem de ter um array do tipo da Interface é que, quando você percorre o array,
    você não precisa saber qual classe está lá dentro. O Java olha para o objeto no índice e diz:
    "Eu não sei se você é Yacht ou Full House, mas eu sei que você assinou o contrato ScoringRule (voce
    implementa ScoringRule), então você OBRIGATORIAMENTE tem o méto.do calculate e getCategory!"

    --> Proibido: ScoringRule regra = new ScoringRule();
    (Interfaces não têm corpo, o Java não saberia o que fazer).

    --> Permitido: ScoringRule regra = new YachtRule();
    (Uma variável chamada regra do tipo "Interface" apontando para um objeto "Real" (YachtRule)). */

    // Um array fixo que organiza as regras na ordem da tabela (0-11)
    private static final ScoringRule[] RULES = {
            new Ones(),     // Índice 0
            new Twos(),
            new Threes(),
            new Fours(),
            new Fives(),
            new Sixes(),
            new FullHouse(),
            new FourOfAKind(),
            new SmallStraight(),
            new BigStraight(),
            new Choice(),
            new YachtRule()     // Índice 11
    };


    /* Quando GameEngine chamar Scorer.getScore(11, valoresDosDados), JAVA vai ate o index 11
     * de RULES, acessando YachtRule, vaai usar o metodo calculate de YachtRule aplicado
     * aos valores dos dados. Esse metodo calcula os pontos para aquele valor e categoria
     * e retorna os pontos, que sao por sua vez retornados por getScore. */
    public static int getScore(int categoryIndex, int[] diceValues) {
        // Basta chamar a regra correspondente ao índice
        return RULES[categoryIndex].calculate(diceValues);
    }

    public static String getCategoryName(int index) {
        return RULES[index].getName();
    }
}
