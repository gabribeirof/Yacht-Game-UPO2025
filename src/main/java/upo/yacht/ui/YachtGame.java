package upo.yacht.ui;
/**
 * The entry point for the Yacht game application.
 * <p>
 * This class handles command-line argument parsing to configure the game state,
 * specifically setting the random seed for deterministic gameplay and selecting
 * the game mode (normal or variant).
 * </p>
 */
public class YachtGame {
    /**
     * The main method acts as the application driver
     * <p>
     * Supported command-line arguments:
     * <ul>
     *   <li>{@code --seed <number>}: Sets a seed for the Random number generator (for testing/reproducibility).</li>
     *   <li>{@code --mode <string>}: Sets the game mode. Accepted values are 'normal' or 'variante'.</li>
     * </ul>
     *
     * @param args The command line arguments passed to the application.
     */
    static void main(String[] args) {
        Long seed = null;           // Default to null (standard random)
        boolean isExtended = false;  // Default to normal mode (false)
        // Parse command line arguments
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--seed" -> {
                    if (i + 1 < args.length) {
                        try {
                            seed = Long.parseLong(args[i + 1]);
                            i++; // Advance index to skip the value
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid seed value: " + args[i + 1]);
                            System.exit(1);
                        }
                    }
                }
                case "--mode" -> {
                    if (i + 1 < args.length) {
                        String modeTag = args[i + 1];
                        if (modeTag.equalsIgnoreCase("extended")) {
                            isExtended = true;
                        } else if (modeTag.equalsIgnoreCase("classic")) {
                            isExtended = false;
                        } else {
                            System.err.println("Invalid mode: " + modeTag + ". Must be 'classic' or 'extended'.");
                            System.exit(1);
                        }
                        i++; // Advance index to skip the value
                    }
                }
            }
        }
        // Create UI with seed and start the game
        ConsoleUI ui = new ConsoleUI(seed, isExtended);
        ui.start();
    }
}