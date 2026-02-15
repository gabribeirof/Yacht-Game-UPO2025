package upo.yacht.logic;

import upo.yacht.logic.rules.*;

/// Central scoring utility for calculating points in the Yacht game.
///
/// This class maintains a fixed array of all 12 scoring rules (categories)
/// and provides static methods to calculate scores and retrieve category names.
/// Each index (0-11) corresponds to a specific scoring rule implementation.
///
/// The rules are organized as follows:
/// - Indices 0-5: Ones, Twos, Threes, Fours, Fives, Sixes
/// - Indices 6-11: Full House, Four of a Kind, Small Straight, Big Straight, Choice, Yacht
public class Scorer {
    private static final ScoringRule[] RULES = {
            new Ones(),
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
            new YachtRule()
    };

    /// Calculates the score for a given category and dice values.
    ///
    /// Retrieves the scoring rule at the specified index and applies
    /// its calculation logic to the provided dice values. This method
    /// uses polymorphism to call the appropriate calculate() implementation
    /// without knowing the specific rule class.
    ///
    /// @param categoryIndex the category index (0-11)
    /// @param diceValues    array of dice values to score
    /// @return the calculated score for the specified category
    public static int getScore(int categoryIndex, int[] diceValues) {
        return RULES[categoryIndex].calculate(diceValues);
    }

    /// Returns the name of the category at the specified index.
    ///
    /// @param index the category index (0-11)
    /// @return the category name as a string
    public static String getCategoryName(int index) {
        return RULES[index].getName();
    }
}
