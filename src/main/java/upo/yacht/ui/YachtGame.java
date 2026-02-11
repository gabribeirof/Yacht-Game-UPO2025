package upo.yacht.ui;

/// Entry point for the **Yacht Dice Game**.
///
/// This class parses command-line arguments to configure the game mode and random seed,
/// then launches the console-based user interface.
///
/// ## Command-Line Arguments
///
/// | Argument   | Value      | Description                                    |
/// |------------|------------|------------------------------------------------|
/// | `--seed`   | `<long>`   | Sets the random number generator seed          |
/// | `--mode`   | `classic`  | Plays standard Yacht rules                     |
/// | `--mode`   | `extended` | Plays with extended scoring categories         |
///
/// ## Usage Examples
///
/// Standard random mode:
/// ```bash
/// java upo.yacht.ui.YachtGame
/// ```
///
/// With fixed seed for reproducible gameplay:
/// ```bash
/// java upo.yacht.ui.YachtGame --seed 42
/// ```
///
/// Extended mode with seed:
/// ```bash
/// java upo.yacht.ui.YachtGame --mode extended --seed 12345
/// ```

public class YachtGame {

    /// Parses command-line arguments and starts the Yacht game.
    ///
    /// Recognized arguments:
    /// - `--seed <value>`: Sets the random seed (must be a valid `long`)
    /// - `--mode <classic|extended>`: Selects game variant
    ///
    /// The program exits with status `1` if invalid arguments are provided.
    ///
    /// @param args Command-line arguments
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
