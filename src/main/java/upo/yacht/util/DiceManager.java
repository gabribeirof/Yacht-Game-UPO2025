package upo.yacht.util;

import upo.yacht.model.Die;

import java.util.Random;

/// Manager class for handling a collection of dice in the Yacht game.
///
/// This class manages an array of 5 dice, providing methods to roll,
/// lock, unlock, and access individual dice. Each die can be independently
/// locked to prevent it from being rolled.
public class DiceManager {
    private final Die[] dice;

    /// Constructs a new DiceManager with 5 dice.
    ///
    /// Each die is initialized with a Random instance. If a seed is provided,
    /// all dice use a seeded random generator for reproducible results
    /// (useful for testing). Otherwise, uses non-seeded randomness.
    ///
    /// @param seed optional seed for random number generation; null for non-seeded randomness
    public DiceManager(Long seed) {
        Random random = (seed != null) ? new Random(seed) : new Random();
        this.dice = new Die[5];
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new Die(random);
        }
    }

    /// Rolls all dice that are not currently locked.
    ///
    /// Iterates through all dice and rolls only those with their lock flag set to false.
    /// Locked dice retain their current value.
    public void rollAvailableDice() {
        for (Die d : dice) {
            if (!d.isLocked()) {
                d.roll();
            }
        }
    }

    /// Locks all dice to prevent them from being rolled.
    public void lockAll() {
        for (Die d : dice) {
            d.setLocked(true);
        }
    }

    /// Unlocks all dice, allowing them to be rolled again.
    public void unlockAll() {
        for (Die d : dice) {
            d.setLocked(false);
        }
    }

    /// Retrieves a specific die by its index.
    ///
    /// @param index the position of the die in the array (0-4)
    /// @return the Die object at the specified index
    public Die getDie(int index) {
        return dice[index];
    }

    /// Returns an array containing the current values of all dice.
    ///
    /// @return integer array with the face values of all 5 dice
    public int[] getDiceValues() {
        int[] values = new int[dice.length];
        for (int i = 0; i < dice.length; i++) {
            values[i] = dice[i].getValue();
        }
        return values;
    }

    /// Displays the current dice values to the console.
    ///
    /// Prints a formatted table showing all dice values in a visually
    /// clear format for the player.
    public void displayDice() {
        System.out.print(
                "\n" +
                        "------------------CURRENT DICE TABLE-------------------" +
                        "\n" +
                        "            ((   "
        );
        for (int i = 0; i < dice.length; i++) {
            System.out.print(dice[i].getValue() + "    ");
        }
        System.out.println(
                "))" +
                        "\n" +
                        "-------------------------------------------------------"
        );
    }
}
