package upo.yacht.logic;

import upo.yacht.exceptions.YachtGameException;
import upo.yacht.model.Player;
import upo.yacht.util.DiceManager;

import java.util.Collections;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/// Main game engine that controls the flow of the Yacht dice game.
///
/// This class manages all game logic including player turns, dice rolling,
/// scoring, and game phases. Supports both Classic and Extended game modes,
/// with Extended mode featuring three distinct phases: Downward (rounds 1-4),
/// 1st Roll (rounds 5-8), and Free (rounds 9-12).
public class GameEngine {
    private final Player[] players;
    private final DiceManager diceManager;
    private final boolean isExtended;
    private final Scanner scanner;
    private int currentRound;

    /// Constructs a new game engine with specified settings.
    ///
    /// @param isExtended true for Extended mode, false for Classic mode
    /// @param numPlayers number of players in the game (minimum 1)
    /// @param seed       optional seed for deterministic random number generation; null for random gameplay
    public GameEngine(boolean isExtended, int numPlayers, Long seed) {
        this.players = new Player[numPlayers];
        this.isExtended = isExtended;
        this.scanner = new Scanner(System.in);
        this.currentRound = 0;
        this.diceManager = new DiceManager(seed);
    }

    /// Starts and runs the complete game loop.
    ///
    /// Initializes players, shuffles turn order, and executes all 12 rounds
    /// of gameplay. Each round consists of turns for all players, followed
    /// by scoring. After all rounds complete, displays final results.
    public void startGame() {
        setupPlayer();
        ArrayList<Player> playerList = new ArrayList<>(Arrays.asList(players));
        Collections.shuffle(playerList);
        System.out.println("\n--- The players were shuffled !! ---");

        for (; currentRound <= 11; currentRound++) {
            System.out.println("\n=== ROUND " + (currentRound + 1) + " ===");
            for (Player p : playerList) {
                System.out.println("\nIt is: " + p.getName() + " turn.");
                executeTurn(p);
            }
        }
        finishGame();
    }

    /// Prompts for and sets up player names.
    ///
    /// Collects names from console input and initializes Player objects
    /// for each participant in the game.
    private void setupPlayer() {
        for (int i = 0; i < players.length; i++) {
            System.out.print("Type player " + (i + 1) + "'s name: ");
            String name = scanner.nextLine();
            this.players[i] = new Player(name);
        }
    }

    /// Displays the current phase header for Extended mode.
    ///
    /// Shows which phase of Extended mode is active based on the current round:
    /// - Rounds 1-4: Downward (3 rolls, fixed category)
    /// - Rounds 5-8: 1st Roll (1 roll, choice category)
    /// - Rounds 9-12: Free (3 rolls, choice category)
    private void printPhaseHeader() {
        if (currentRound <= 3) {
            System.out.println(">>> MODE: DOWNWARD (3 Rolls, Fixed Category)");
        } else if (currentRound <= 7) {
            System.out.println(">>> MODE: 1ST ROLL (1 Roll, Choice Category)");
        } else {
            System.out.println(">>> MODE: FREE (3 Rolls, Choice Category)");
        }
    }

    /// Executes a complete turn for the specified player.
    ///
    /// Manages the rolling phase with appropriate number of rolls based on
    /// game mode and phase. Allows players to lock/unlock dice between rolls.
    /// Concludes with scoring the final dice configuration.
    ///
    /// @param p the player taking their turn
    public void executeTurn(Player p) {
        diceManager.unlockAll();
        int maxRolls;

        if (isExtended) {
            maxRolls = (currentRound >= 4 && currentRound <= 7) ? 1 : 3;
        } else {
            maxRolls = 3;
        }

        for (int j = 0; j < maxRolls; j++) {
            diceManager.rollAvailableDice();
            diceManager.displayDice();
            int rollsLeft = (maxRolls - 1) - j;

            if (rollsLeft == 0) {
                break;
            }

            if (isExtended) {
                printPhaseHeader();
            }

            System.out.println("Rolls left: " + rollsLeft);
            System.out.print("Which dice do you want to REROLL?\n" +
                    "type the dice numbers from 0 to 4 or x to keep the values:   ");

            String input;
            String[] choices;

            while (true) {
                input = scanner.nextLine().toUpperCase().trim();
                if (input.isEmpty()) {
                    continue;
                }

                choices = input.split("[\\s,]+");
                boolean inputOK = true;

                for (String s : choices) {
                    if (!s.matches("[0-4]") && !s.equals("X")) {
                        System.out.print("Invalid! Use 0-4 or X: ");
                        inputOK = false;
                        break;
                    }
                }

                if (inputOK) break;
            }

            if (choices[0].equals("X")) {
                break;
            }

            diceManager.lockAll();
            for (String s : choices) {
                if (s.matches("[0-4]")) {
                    int index = Integer.parseInt(s);
                    diceManager.getDie(index).setLocked(false);
                }
            }
        }
        handleScoring(p);
    }

    /// Prompts the player to select a scoring category.
    ///
    /// Validates input to ensure a valid category index (0-11) is selected.
    ///
    /// @return the selected category index
    private int askForCategory() {
        while (true) {
            System.out.print("Choose a category index (0-11): ");
            String input = scanner.nextLine();
            try {
                int idx = Integer.parseInt(input);
                if (idx >= 0 && idx <= 11) return idx;
                System.out.println("Error: Number must be between 0 and 11.");
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + input + "' is not a valid number.");
            }
        }
    }

    /// Handles scoring for the player's current turn.
    ///
    /// In Extended Downward phase, automatically assigns the category based on
    /// the current round. Otherwise, prompts the player to choose a category.
    /// Calculates and registers the score, handling already-filled categories
    /// appropriately.
    ///
    /// @param p the player whose turn is being scored
    private void handleScoring(Player p) {
        int[] finalDice = diceManager.getDiceValues();
        int categoryIndex;

        if (isExtended && currentRound <= 3) {
            categoryIndex = currentRound;
            System.out.println("Downward Phase: Scoring automatically in category " + categoryIndex);
        } else {
            p.getScoreboard().displayBoard(finalDice);
            categoryIndex = askForCategory();
        }

        int points = Scorer.getScore(categoryIndex, finalDice);

        if (points == 0) {
            System.out.println("ATTENTION: These dice scored 0 in " + Scorer.getCategoryName(categoryIndex));
        }

        try {
            p.getScoreboard().registerScore(categoryIndex, points);
            System.out.println("Points registered: " + points);
        } catch (YachtGameException e) {
            if (isExtended && currentRound <= 3) {
                System.out.println("ERROR: Downward category already filled.");
            } else {
                System.out.println("Error: Category already filled! Choose another one.");
                handleScoring(p);
            }
        }
    }

    /// Concludes the game and displays final results.
    ///
    /// Sorts players by total score, generates the final scoreboard,
    /// displays it to console, and offers to save results to a file.
    public void finishGame() {
        Player[] sortedPlayers = Arrays.copyOf(players, players.length);
        Arrays.sort(sortedPlayers, Comparator.comparingInt(Player::getTotalScore).reversed());

        String output = generateScoreboardOutput(sortedPlayers);
        System.out.print(output);
        handleSaveResults(output);
    }

    /// Generates a formatted string containing final game results.
    ///
    /// Creates a detailed scoreboard showing all players sorted by score,
    /// their individual category scores, and announces the winner(s).
    ///
    /// @param sortedPlayers array of players sorted by total score (descending)
    /// @return formatted scoreboard string
    private String generateScoreboardOutput(Player[] sortedPlayers) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append("=".repeat(50)).append("\n");
        sb.append("GAME OVER - FINAL RESULTS\n");
        sb.append("=".repeat(50)).append("\n");

        sb.append("\nFINAL SCOREBOARD:\n");
        sb.append("-".repeat(40)).append("\n");

        for (int i = 0; i < sortedPlayers.length; i++) {
            Player player = sortedPlayers[i];
            String rankLabel = player.getTotalScore() == sortedPlayers[0].getTotalScore() ? "#1 " : "   ";

            sb.append(String.format("%s%-20s: %5d points%n", rankLabel, player.getName(), player.getTotalScore()));
            sb.append("  Categories:\n");

            for (int cat = 0; cat < 12; cat++) {
                if (player.getScoreboard().isCategoryUsed(cat)) {
                    sb.append(String.format("    %-18s: %3d%n", Scorer.getCategoryName(cat), player.getScoreboard().getScore(cat)));
                }
            }
            sb.append("\n");
        }

        sb.append("\n").append("*".repeat(20)).append("\n");

        int topScore = sortedPlayers[0].getTotalScore();
        boolean multiWinners = sortedPlayers.length > 1 && sortedPlayers[1].getTotalScore() == topScore;

        if (multiWinners) {
            sb.append("CONGRATULATIONS: ");
            for (Player p : sortedPlayers) {
                if (p.getTotalScore() == topScore) {
                    sb.append(p.getName()).append(" ");
                }
            }
            sb.append("\nYOU ARE ALL YACHT CHAMPIONS!\n");
        } else {
            sb.append("CONGRATULATIONS ").append(sortedPlayers[0].getName()).append("! \n");
            sb.append("YOU ARE THE YACHT CHAMPION!\n");
        }
        sb.append("*".repeat(20)).append("\n");

        return sb.toString();
    }

    /// Prompts the user to save game results to a file.
    ///
    /// Offers to save the final scoreboard output to a user-specified file.
    /// Handles file I/O errors and provides retry options.
    ///
    /// @param output        the formatted scoreboard string to save
    private void handleSaveResults (String output) {
        System.out.print("\nDo you want to save the results to a file? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.startsWith("y")) {
            System.out.print("Enter file name (e.g., scores.txt): ");
            String fileName = scanner.nextLine().trim();

            Path currentDir = Paths.get(".");
            Path destination = currentDir.resolve(fileName);

            try {
                saveScoreboardToFile(output, destination);
                System.out.println("Results saved successfully to: " + destination.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
                System.out.print("Try another path? (y/n): ");
                String retry = scanner.nextLine().trim().toLowerCase();

                if (retry.equals("y") || retry.equals("yes")) {
                    handleSaveResults(output);
                }
            }
        }
    }

    /// Writes the scoreboard content to the specified file path.
    ///
    /// Creates parent directories if they don't exist and writes the
    /// content using UTF-8 encoding.
    ///
    /// @param content the scoreboard string to write
    /// @param path    the destination file path
    /// @throws IOException if file writing fails
    private void saveScoreboardToFile(String content, Path path) throws IOException {
        Path parentDir = path.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }

        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}
