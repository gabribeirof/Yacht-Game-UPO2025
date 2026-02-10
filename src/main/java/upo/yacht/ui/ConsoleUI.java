package upo.yacht.ui;

import upo.yacht.exceptions.YachtGameException;
import upo.yacht.logic.GameEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final boolean isExtended;
    // Store seed if provided via constructor or setter
    private Long seed;

    // Constructor that accepts a seed for deterministic gameplay
    public ConsoleUI(Long seed, boolean isExtended) {
        this.scanner = new Scanner(System.in);
        this.seed = seed;
        this.isExtended = isExtended;
    }

    public void start() {
        printWelcome();
        handleRules();
        int playerCount = askPlayerCount();
        //Mode foi definido em linha de comando e esta contido na variável
        //isExtended. Se for true é EXTENDED se for false é CLASSIC
        System.out.println("Mode: " + (isExtended ? "Extended" : "Classic"));
        // CREATE AND START THE ACTUAL GAME ENGINE
        GameEngine engine = new GameEngine(isExtended, playerCount, seed);
        engine.startGame();
    }

    private void printWelcome() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║        Welcome to Yacht!       ║");
        System.out.println("╚════════════════════════════════╝");
    }

    //Funcao que pergunta se o jogador quer ler as regras, se sim,
    //é aberto um file com as regras e mostrado em tela.
    //alterei a funçao para funcionar com o build de .jar
    private void handleRules() {
        System.out.print("Do you want to read the rules? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.startsWith("y")) {
            String path = "src/yacht_rules.txt";
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

    private int askPlayerCount() {
        int numberOfPlayers = 0;
        while (numberOfPlayers < 2) {
            System.out.print("How many players (minimum 2)? ");
            try {
                String input = scanner.nextLine();
                // 1. O parseInt pode lançar NumberFormatException (exceção nativa)
                try {
                    numberOfPlayers = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    // Transformamos um erro genérico do Java em um YachtGameException
                    throw new YachtGameException("'" + input + "' is not a valid number.");
                }
                // 2. Verificação da regra de negócio: Minimo 2 jogadores
                if (numberOfPlayers < 2) {
                    throw new YachtGameException("The game requires at least 2 players to start.");
                }
            } catch (YachtGameException e) {
                //capturamos as excecoes personalizadas caso sejam lancadas.
                System.out.println("Error: " + e.getMessage());
            }
        }
        return numberOfPlayers;
    }
}