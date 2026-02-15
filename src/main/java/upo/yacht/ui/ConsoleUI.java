package upo.yacht.ui;

import upo.yacht.exceptions.YachtGameException;
import upo.yacht.logic.GameEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/// Console-based user interface for the Yacht dice game.
///
/// This class handles all user interaction through the console, including
/// displaying welcome messages, reading rules, collecting player count,
/// and starting the game engine. Supports both Classic and Extended game modes.
public class ConsoleUI {
    private final Scanner scanner;
    private final boolean isExtended;
    private Long seed;

    /// Constructs a new ConsoleUI with specified game settings.
    ///
    /// @param seed       optional seed for deterministic random number generation; null for random gameplay
    /// @param isExtended true for Extended mode, false for Classic mode
    public ConsoleUI(Long seed, boolean isExtended) {
        this.scanner = new Scanner(System.in);
        this.seed = seed;
        this.isExtended = isExtended;
    }

    /// Starts the console interface flow and initializes the game.
    ///
    /// Displays the welcome message, handles rule display, collects player count,
    /// and creates and starts the GameEngine with the configured settings.
    public void start() {
        printWelcome();
        handleRules();
        int playerCount = askPlayerCount();
        System.out.println("Mode: " + (isExtended ? "Extended" : "Classic"));
        GameEngine engine = new GameEngine(isExtended, playerCount, seed);
        engine.startGame();
    }

    /// Displays the welcome banner to the console.
    private void printWelcome() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║        Welcome to Yacht!       ║");
        System.out.println("╚════════════════════════════════╝");
    }

    /// Prompts the user to read the game rules and displays them if requested.
    ///
    /// Reads the rules from an embedded resource file (yacht_rules.txt) within
    /// the JAR. If the user responds with 'y', the rules are displayed to the console.
    private void handleRules() {
        System.out.print("Do you want to read the rules? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.startsWith("y")) {
            String path = "/upo/yacht/ui/yacht_rules.txt";
            try (InputStream inputStream = getClass().getResourceAsStream(path)) {
                if (inputStream == null) {
                    System.err.println("File not found! Check the path: " + path);
                    return;
                }
                String rules = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("\n--- RULES ---");
                System.out.println(rules);
                System.out.println("-------------\n");
            } catch (IOException e) {
                System.err.println("Error reading rules file: " + e.getMessage());
            }
        } else {
            System.out.println("---you already know the rules, let's go---");
        }
    }

    /// Prompts the user for the number of players and validates the input.
    ///
    /// Continuously prompts until a valid number (at least 1) is entered.
    /// Handles both non-numeric input and numbers below the minimum by
    /// throwing and catching YachtGameException with appropriate error messages.
    ///
    /// @return the validated number of players (minimum 1)
    private int askPlayerCount() {
        int numberOfPlayers = 0;
        while (numberOfPlayers < 1) {
            System.out.print("How many players (minimum 1)? ");
            try {
                String input = scanner.nextLine();
                try {
                    numberOfPlayers = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    throw new YachtGameException("'" + input + "' is not a valid number.");
                }
                if (numberOfPlayers < 1) {
                    throw new YachtGameException("The game requires at least 1 player to start.");
                }
            } catch (YachtGameException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return numberOfPlayers;
    }
}
