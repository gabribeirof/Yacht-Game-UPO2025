package upo.yacht.util;
/**
 * Utility class for dice operations in the Yacht game.
 * This class provides static helper methods to calculate sums and
 * determine the frequency of dice values. {final classes cannot be inherited}
 */
public final class DiceUtils {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DiceUtils() {
    }
    /**
     * Calculates the sum of all dice values in the provided array.
     *
     * @param diceValues an array containing the face values of the dice (e.g., 1-6)
     * @return the total sum of the dice values
     */
    public static int sumDice(int[] diceValues) {
        int sum = 0;
        for (int value : diceValues) {
            sum += value;
        }
        return sum;
    }
    /**
     * Generates a frequency array representing how many times each dice value appears.
     *
     * @param diceValues an array containing the face values of the dice.
     * @return an integer array where index i holds the count of dice showing face i.
     */
    public static int[] getDiceFrequency(int[] diceValues) {
        // We need a slot for the number 6.
        // In Java, to access index [6], the size must be 7.
        // If we used diceValues.length (5) + 1 = 6, index [6] would not exist.
        int[] frequency = new int[7];

        for (int value : diceValues) {
            if (value >= 1 && value <= 6) {
                frequency[value]++;
            }
        }
        return frequency;
    }
}