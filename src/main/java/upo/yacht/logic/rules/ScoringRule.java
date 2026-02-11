package upo.yacht.logic.rules;

/// Scoring rule interface for Yacht dice game categories.
///
/// Each scoring rule represents a specific category in the game
/// (e.g., "Full House", "Ones", "Choice") and defines how to
/// calculate the score for a given set of dice values.
///
/// Implementing classes must provide:
/// - A calculation method that processes dice values
/// - A name identifier for the category
public interface ScoringRule {
    /// Calculates the score for the given dice values.
    ///
    /// This method processes the dice according to the specific
    /// rule's logic and returns the resulting score.
    ///
    /// @param dice array of dice values to score (typically 5 dice with values 1-6)
    /// @return the calculated score for this rule
    int calculate(int[] dice);

    /// Returns the name of this scoring category.
    ///
    /// @return the category name (e.g., "Full House", "Ones", "Choice")
    String getName();
}
