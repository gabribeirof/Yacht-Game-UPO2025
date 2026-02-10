package upo.yacht.logic;


import upo.yacht.exceptions.YachtGameException;
import upo.yacht.model.Player;
import upo.yacht.util.DiceManager;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


//private final nao impede modificar os valores dentro do array.

//Mas impede que eu substitua to do o array por um nnovo array.

//Ex: em algum momento eu poderia tentar this.players = new Player[2], o que

//substituiria o array antigo pelo novo.

//

//Classe que controla o jogo

public class GameEngine {

    private final Player[] players; //array de jogadores

    private final DiceManager diceManager; //objeto que representa um array de dados

    private final boolean isExtended; //flag que define a modalidade do jogo

    private final Scanner scanner; //variavel que pega dados do jogador: canal de comunicacao

    private int currentRound; //contador de 12 rodadas


    // Construtor: recebe como parametro True ou False, se True = modo variante, False = modo classico

    public GameEngine(boolean isExtended, int numPlayers, Long seed) {

        //players nao sera inicializada no construtor porque precisa do nome dos jogadores que eh

        //inserido pelo usuario, portanto sera inicializada no metodo starGame.

        this.players = new Player[numPlayers];

        this.isExtended = isExtended;

        this.scanner = new Scanner(System.in);

        this.currentRound = 0; //jogo comeca na rodada 0 vai ate a 11.

        this.diceManager = new DiceManager(seed); //chama o construtor de DiceManager que inicializa os 5 dados

    }


    public void startGame() {

        setupPlayer();

        for (; currentRound <= 11; currentRound++) {

            System.out.println("\n=== ROUND " + currentRound + " ===");

            //Loop que percorre os jogadores, define de quem eh a vez de jogar

            for (Player p : players) {

                System.out.println("\nIt is: " + p.getName() + " turn.");

                executeTurn(p);
            }

        }

        finishGame();

    }


    //Metodo auxiliar(private, usado apenas por GameEngine) para definir o nome dos jogadores

    private void setupPlayer() {

        for (int i = 0; i < players.length; i++) {

            System.out.print("Type player " + (i + 1) + "'s name: ");

            String name = scanner.nextLine();

            this.players[i] = new Player(name);

        }

    }


    //Metodo auxiliar que destrava somente os dados que o usuario quer lançar.

    private boolean processRerrolChoices(String[] choices) {

        if (choices[0].equals("X")) {

            return true;

        }

        for (String s : choices) {

            if (s.matches("[0-4]")) {

                int index = Integer.parseInt(s);

                diceManager.getDie(index).setLocked(false);

            }

        }

        return false;

    }


    //Metodo auxiliar que mostra qual fase do extended mode estamos

    private void printPhaseHeader() {

        if (currentRound <= 3) {

            System.out.println(">>> MODE: DOWNWARD (3 Rolls, Fixed Category)");

        } else if (currentRound <= 7) {

            System.out.println(">>> MODE: 1ST ROLL (1 Roll, Choice Category)");

        } else {

            System.out.println(">>> MODE: FREE (3 Rolls, Choice Category)");

        }

    }


    //metodo que gerencia a vez do jogador de acordo com modo e fase.

    public void executeTurn(Player p) {

        diceManager.unlockAll(); // Reset inicial do turno
        int maxRolls;

        // Define o limite de lances se for extended mode: 1 para a fase '1st Roll', 3 para as demais

        // rodadas 5, 6, 7 e 8 so posso lancar o dado 1 vez

        if (isExtended) {
            maxRolls = (currentRound >= 4 && currentRound <= 7) ? 1 : 3;
        } else {
            maxRolls = 3;
        }

        for (int j = 0; j < maxRolls; j++) {
            diceManager.rollAvailableDice();
            diceManager.displayDice(); //mostra os dados lancados

            int rollsLeft = (maxRolls - 1) - j;
            // Se nao houver mais lancamentos ou se for a fase 1 de lancamento unico, termina
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

            //loop de validacao que nao relanca os dados se o usuario digitar errado
            while (true) {
                input = scanner.nextLine().toUpperCase().trim();
                if (input.isEmpty()) {
                    choices = new String[]{"X"};
                    break;
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
                if (inputOK) {
                    break;
                }

                if (choices[0].equals("X")) {
                    break;
                }

                //trava tudo e destrava so o que foi escolhido pro proximo roll
                diceManager.lockAll();
                for (String s : choices) {
                    if (s.matches("[0-4]")) {
                        diceManager.getDie(Integer.parseInt(s)).setLocked(false);
                    }
                }
            }
            handleScoring(p);
        }
    }



    private void handleScoring(Player p) {

        int[] finalDice = diceManager.getDiceValues(); // Pega os números (ex: 1, 3, 3, 4, 6)

        int categoryIndex = -1;

        // 1. Definição da Categoria

        if (isExtended && currentRound <= 3) { //ou seja, DOWNWARD

            // Fase DOWNWARD: Categoria é automática (Round 1 -> Cat 0, Round 2 -> Cat 1...)

            categoryIndex = currentRound;

            System.out.println("Downward Phase: Scoring automatically in category " + categoryIndex);

        } else {

            // Fases Normais: Jogador escolhe

            p.getScoreboard().displayBoard(finalDice); // Mostra o que já está preenchido

            while (true) {

                System.out.print("Choose a category index (0-11): ");

                String input = scanner.nextLine(); // Read input as String first

                try {

                    categoryIndex = Integer.parseInt(input); // Attempt to parse

                    // Check if the number is within the valid array bounds (0 to 11)

                    if (categoryIndex >= 0 && categoryIndex <= 11) {


                        break; // Valid input found, exit the loop

                    } else {

                        System.out.println("Error: Number must be between 0 and 11.");

                    }

                } catch (NumberFormatException e) {

                    // Handle non-numeric input like "s"

                    System.out.println("Error: '" + input + "' is not a valid number.");


                }

            }

        }


        // 2. Cálculo (Aqui chamamos a classe Scorer que vamos criar)

        int points = Scorer.getScore(categoryIndex, finalDice);

        // 3. Registro no Placar


        try {

            p.getScoreboard().registerScore(categoryIndex, points);

            System.out.println("Points registered: " + points);

        } catch (YachtGameException e) {

            System.out.println("Error: Category already filled! Choose another one.");

            handleScoring(p); // Recursão simples para tentar de novo se estiver ocupada

        }

    }


    public void finishGame() {

        System.out.println("\n" + "=".repeat(50));

        System.out.println("GAME OVER - FINAL RESULTS");

        System.out.println("=".repeat(50));

        // Sort players by score (descending)

        Player[] sortedPlayers = Arrays.copyOf(players, players.length);

        Arrays.sort(sortedPlayers, Comparator.comparingInt(Player::getTotalScore).reversed());


        // Display final scores


        System.out.println("\nFINAL SCOREBOARD:");


        System.out.println("-".repeat(40));
        for (int i = 0; i < sortedPlayers.length; i++) {
            Player player = sortedPlayers[i];
            String trophy = "";
            if (i == 0) {
                trophy = "#1 ";
            } else if (i == 1 && sortedPlayers.length > 1) {
                trophy = "#2 ";
            } else if (i == 2 && sortedPlayers.length > 2) {
                trophy = "3 ";
            }


            System.out.printf("%s%-20s: %5d points%n", trophy, player.getName(), player.getTotalScore());
            // Display individual category scores
            System.out.println("  Categories:");
            for (int cat = 0; cat < 12; cat++) {
                if (player.getScoreboard().isCategoryUsed(cat)) {
                    System.out.printf("    %-18s: %3d%n", Scorer.getCategoryName(cat),
                            player.getScoreboard().getScore(cat));
                }
            }
            System.out.println();
        }
        // Announce winner
        System.out.println("\n" + "*".repeat(20));
        System.out.println("CONGRATULATIONS " + sortedPlayers[0].getName() + "! ");
        System.out.println("YOU ARE THE YACHT CHAMPION!");


        System.out.println("*".repeat(20));
        // Offer to save results
        handleSaveResults(sortedPlayers);
    }

    private void handleSaveResults(Player[] sortedPlayers) {
        System.out.print("\nDo you want to save the results to a file? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.startsWith("y")) {
            System.out.print("Enter file name (e.g., scores.txt): ");
            String fileName = scanner.nextLine().trim();


            // 1. Resolve the path relative to where the JAR is running
            Path currentDir = Paths.get(".");
            Path destination = currentDir.resolve(fileName);

            try {
                // Pass the RESOLVED path, not the raw string name
                saveScoreboardToFile(sortedPlayers, destination);
                System.out.println("Results saved successfully to: " + destination.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
                System.out.print("Try another path? (y/n): ");
                String retry = scanner.nextLine().trim().toLowerCase();

                // Retry logic
                if (retry.equals("y") || retry.equals("yes")) {
                    handleSaveResults(sortedPlayers);
                }
            }


        }
    }

    // Change the argument from String to Path
    private void saveScoreboardToFile(Player[] sortedPlayers, Path path) throws IOException {

        // Create parent directories if they don't exist (e.g., if user typed "results/scores.txt")
        Path parentDir = path.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }

        // 2. Use Files.newBufferedWriter with StandardCharsets.UTF_8
        // This ensures your special characters (borders) look the same on Windows, Mac, and Linux.
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            writer.write("=".repeat(50));
            writer.newLine();
            writer.write("YACHT GAME - FINAL SCOREBOARD");
            writer.newLine();
            writer.write("=".repeat(50));
            writer.newLine();
            writer.newLine();

            writer.write("Game Mode: " + (isExtended ? "Extended" : "Classic"));
            writer.newLine();
            writer.write("Players: " + sortedPlayers.length);
            writer.newLine();
            writer.write("-".repeat(50));
            writer.newLine();
            writer.newLine();

            for (int i = 0; i < sortedPlayers.length; i++) {
                Player player = sortedPlayers[i];
                writer.write(String.format("%d. %s", i + 1, player.getName()));
                writer.newLine();
                writer.write(String.format("   Total Score: %d points", player.getTotalScore()));
                writer.newLine();

                writer.write("   Category Breakdown:");
                writer.newLine();

                // Assuming categories are 0-11
                for (int cat = 0; cat < 12; cat++) {
                    if (player.getScoreboard().isCategoryUsed(cat)) {
                        // %n is the portable way to do a newline inside String.format
                        writer.write(String.format("     %-18s: %3d%n",
                                Scorer.getCategoryName(cat),
                                player.getScoreboard().getScore(cat)));
                    }
                }
                writer.newLine();
            }

            writer.write("=".repeat(50));
            writer.newLine();
            writer.write(String.format("WINNER: %s", sortedPlayers[0].getName()));
            writer.newLine();
            writer.write("=".repeat(50));
        }
    }
}
