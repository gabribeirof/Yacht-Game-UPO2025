package upo.yacht.model;

import java.util.Random;

/// Represents a single die in the Yacht game.
///
/// Each die can show values from 1 to 6 and can be locked to prevent
/// rolling. The die uses a shared Random instance to ensure consistent
/// randomness across all dice in the game.
public class Die {
    private static final int FACES = 6;
    private final Random random;
    private int value;
    private boolean isLocked;

    /// Creates a new die with the given random number generator.
    ///
    /// The die is initialized with a value of 1 and unlocked state.
    /// All dice in the game should share the same Random instance
    /// to maintain consistent randomness behavior.
    ///
    /// @param random the Random instance to use for generating die rolls
    public Die(Random random) {
        this.random = random;
        this.value = 1;
        this.isLocked = false;
    }

    /// Rolls the die to generate a new random value.
    ///
    /// If the die is locked, it retains its current value and is not rolled.
    /// Otherwise, generates a random value between 1 and 6 (inclusive).
    ///
    /// @return the current value of the die after rolling (or unchanged if locked)
    public int roll() {
        if (!isLocked) {
            this.value = random.nextInt(FACES) + 1;
        }
        return this.value;
    }

    /// Returns the current face value of the die.
    ///
    /// @return the current value (1-6)
    public int getValue() {
        return value;
    }

    /// Returns whether the die is currently locked.
    ///
    /// @return true if the die is locked and cannot be rolled, false otherwise
    public boolean isLocked() {
        return isLocked;
    }

    /// Sets the lock state of the die.
    ///
    /// When locked, the die will not change value when roll() is called.
    /// This allows players to keep specific dice between rolls.
    ///
    /// @param locked true to lock the die, false to unlock it
    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    @Override
    public String toString() {
        return "Die(" + FACES + "): value=" + value + ", locked=" + isLocked;
    }
}
