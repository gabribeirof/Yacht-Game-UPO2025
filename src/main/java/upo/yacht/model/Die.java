package upo.yacht.model;

import java.util.Random;

public class Die {
    private static final int FACES = 6;
    private final Random random;
    private int value;
    // true = locked, false = can roll
    private boolean isLocked;

    // Default constructor
    public Die(Random random) {
        this.random = random; // Todos os dados bebem da mesma fonte
        this.value = 1;
        this.isLocked = false;
    }

    //Rolls the die only if it's not locked.
    public int roll() {
        if (!isLocked) {
            this.value = random.nextInt(FACES) + 1;
        }
        return this.value;
    }

    //Returns the current value of the die.
    public int getValue() {
        return value;
    }

    //Returns whether the die is locked.
    public boolean isLocked() {
        return isLocked;
    }

    //Toggles or sets the lock state of the die.
    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    @Override
    public String toString() {
        return "Die(" + FACES + "): value=" + value + ", locked=" + isLocked;
    }
}