package upo.yacht.util;

/// Utility class for dice operations in the Yacht game.
///
/// Provides static helper methods to calculate sums and determine
/// the frequency of dice values. This class is final and cannot be
/// extended, and its constructor is private to prevent instantiation.
public final class DiceUtils {
    /// Private constructor to prevent instantiation of this utility class.
    private DiceUtils() {
    }

    /// Calculates the sum of all dice values in the provided array.
    ///
    /// @param diceValues array containing the face values of the dice (typically 1-6)
    /// @return the total sum of all dice values
    public static int sumDice(int[] diceValues) {
        int sum = 0;
        for (int value : diceValues) {
            sum += value;
        }
        return sum;
    }

    /// Generates a frequency array counting occurrences of each dice value.
    ///
    /// Creates an array of size 7 where each index represents a die face value (1-6),
    /// and the value at that index indicates how many times that face appeared.
    /// Index 0 is unused. Values outside the 1-6 range are ignored.
    ///
    /// # Example
    /// For dice `[2, 2, 5, 5, 5]`, the returned array would have:
    /// - `frequency[2] = 2` (two 2's)
    /// - `frequency[5] = 3` (three 5's)
    ///
    /// @param diceValues array containing the face values of the dice
    /// @return integer array where index i contains the count of dice showing face value i
    public static int[] getDiceFrequency(int[] diceValues) {
        int[] frequency = new int[7];

        for (int value : diceValues) {
            if (value >= 1 && value <= 6) {
                frequency[value]++;
            }
        }
        return frequency;
    }
}
