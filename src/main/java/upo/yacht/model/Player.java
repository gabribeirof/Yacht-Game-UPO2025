package upo.yacht.model;

/**
 * Represents a player in the Yacht game.
 * Each player has a name and their own scoreboard to track category scores.
 */
public class Player {
    private final String name;
    private final Scoreboard scoreboard;

    /**
     * Creates a new player with the given name.
     * Automatically initializes an empty scoreboard for this player.
     *
     * @param name the player's name/alias
     */
    public Player(String name) {
        this.name = name;
        this.scoreboard = new Scoreboard();
    }

    /**
     * Gets the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's scoreboard.
     *
     * @return the scoreboard instance for this player
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Gets the player's total score across all categories.
     *
     * @return the sum of all category scores
     */
    public int getTotalScore() {
        return scoreboard.getTotalScore();
    }

    @Override
    public String toString() {
        return name + " (Total: " + getTotalScore() + ")";
    }
}