import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Handles all terminal input/output for the game.
 * Displays the game state and reads player commands.
 */
public class TerminalUI {

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private final GameEngine engine;
    private final GameState state;
    private final Scanner scanner;

    /**
     * Creates a new TerminalUI with the given engine and state.
     *
     * @param engine the game engine
     * @param state  the current game state
     */
    public TerminalUI(GameEngine engine, GameState state) {
        this.engine = engine;
        this.state = state;
        this.scanner = new Scanner(System.in);
    }

    // Main game loop
    /**
     * Starts and runs the game loop until the game ends.
     */
    public void start() {
        printHelp();
        while (true) {
            printState();
            String input = prompt();
            MoveResult result = handleInput(input);

            if (result == MoveResult.GAME_WON) {
                printState();
                System.out.println(GREEN + BOLD + "🎉 You won! Congratulations!" + RESET);
                break;
            }
            if (result == MoveResult.GAME_OVER) {
                printState();
                System.out.println(RED + BOLD + "💀 Game over! No valid moves left." + RESET);
                break;
            }
        }
        scanner.close();
    }

    // Input handling
    private MoveResult handleInput(String input) {
        String[] parts = input.trim().toLowerCase().split("\\s+");
        if (parts.length == 0 || parts[0].isEmpty()) {
            return MoveResult.SUCCESS;
        }
        switch (parts[0]) {
            case "play" -> {
                return handlePlay(parts);
            }
            case "end" -> {
                if (!engine.canEndTurn()) {
                    System.out.println(YELLOW + "You must play at least 2 cards before ending your turn." + RESET);
                    return MoveResult.SUCCESS;
                }
                engine.endTurn();
                System.out.println(CYAN + "Turn ended. Cards drawn." + RESET);
                return MoveResult.SUCCESS;
            }
            case "help" -> {
                printHelp();
                return MoveResult.SUCCESS;
            }
            case "rules" -> {
                printRules();
                return MoveResult.SUCCESS;
            }
            case "exit" -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println(YELLOW + "Unknown command. Type 'help' for commands." + RESET);
        }
        return MoveResult.SUCCESS;
    }

    private MoveResult handlePlay(String[] parts) {
        if (parts.length != 3) {
            System.out.println(YELLOW + "Usage: play <card value> <pile number 1-4>" + RESET);
            return MoveResult.SUCCESS;
        }

        int cardValue, pileIndex;
        try {
            cardValue = Integer.parseInt(parts[1]);
            pileIndex = Integer.parseInt(parts[2]) - 1; // 1-based → 0-based
        } catch (NumberFormatException e) {
            System.out.println(YELLOW + "Invalid input. Usage: play <card value> <pile number 1-4>" + RESET);
            return MoveResult.SUCCESS;
        }

        if (pileIndex < 0 || pileIndex > 3) {
            System.out.println(YELLOW + "Pile number must be between 1 and 4." + RESET);
            return MoveResult.SUCCESS;
        }

        // Find card in hand
        Card cardToPlay = null;
        for (Card c : state.getPlayer().getHand()) {
            if (c.getValue() == cardValue) {
                cardToPlay = c;
                break;
            }
        }

        if (cardToPlay == null) {
            System.out.println(YELLOW + "You don't have a card with value " + cardValue + "." + RESET);
            return MoveResult.SUCCESS;
        }

        Pile pile = state.getPile(pileIndex);
        MoveResult result = engine.playCard(cardToPlay, pile);

        if (result == MoveResult.INVALID) {
            System.out.println(RED + "Invalid move! Can't place [" + cardValue + "] on pile " + (pileIndex + 1)
                    + " (top: " + pile.getTopCard().getValue() + ", direction: " + pile.getDirection() + ")." + RESET);
            return MoveResult.SUCCESS; // game continues
        }

        System.out.println(GREEN + "Played [" + cardValue + "] on pile " + (pileIndex + 1) + "." + RESET);
        return result;
    }

    // Display
    private void printState() {
        System.out.println();
        System.out.println("═".repeat(50));

        printPiles();
        System.out.println();
        printDeckInfo();
        System.out.println();
        printHand();

        System.out.println("═".repeat(50));
    }

    private void printPiles() {
        Pile[] piles = state.getPiles();
        System.out.println(BOLD + "  Piles:" + RESET);

        for (int i = 0; i < piles.length; i++) {
            Pile pile = piles[i];
            String dir = pile.getDirection() == Direction.UP ? GREEN + "↑ UP  " + RESET
                    : RED + "↓ DOWN" + RESET;
            String topCard = formatTopCard(pile);
            System.out.printf("  [%d] %s  top: %s%n", i + 1, dir, topCard);
        }
    }

    private String formatTopCard(Pile pile) {
        int val = pile.getTopCard().getValue();
        boolean isStart = (pile.getDirection() == Direction.UP && val == 1)
                || (pile.getDirection() == Direction.DOWN && val == 100);
        if (isStart) {
            return CYAN + BOLD + "[" + val + "]" + RESET;
        }
        return "[" + val + "]";
    }

    private void printDeckInfo() {
        int remaining = state.getDeck().size();
        System.out.println(BOLD + "  Deck: " + RESET + remaining + "/98 cards remaining");
    }

    private void printHand() {
        List<Card> hand = new ArrayList<>(state.getPlayer().getHand());
        hand.sort((a, b) -> a.getValue() - b.getValue());

        // Highlight valid cards
        System.out.println(BOLD + "  Your hand:" + RESET);
        System.out.print("  ");
        for (Card card : hand) {
            boolean valid = isValidOnAnyPile(card);
            if (valid) {
                System.out.print(GREEN + "[" + card.getValue() + "]" + RESET + " ");
            } else {
                System.out.print("[" + card.getValue() + "] ");
            }
        }
        System.out.println();
        System.out.println("  " + YELLOW + "(green = can be played)" + RESET);
    }

    private boolean isValidOnAnyPile(Card card) {
        for (Pile pile : state.getPiles()) {
            if (new MoveValidator().validate(card, pile)) {
                return true;
            }
        }
        return false;
    }

    private void printHelp() {
        System.out.println();
        System.out.println(BOLD + "  Commands:" + RESET);
        System.out.println("  play <value> <pile>  - play a card (e.g. play 38 2)");
        System.out.println("  end                  - end your turn and draw cards");
        System.out.println("  help                 - show available commands");
        System.out.println("  rules                - show game rules");
        System.out.println("  exit                 - quit the game");
        System.out.println();
    }

    private void printRules() {
        System.out.println();
        System.out.println(BOLD + "  THE GAME — Rules" + RESET);
        System.out.println("  " + "─".repeat(40));
        System.out.println(BOLD + "  Goal:" + RESET);
        System.out.println("    Play all 98 cards onto the 4 piles.");
        System.out.println();
        System.out.println(BOLD + "  Piles:" + RESET);
        System.out.println("    [1] and [2] go UP   — play higher cards");
        System.out.println("    [3] and [4] go DOWN — play lower cards");
        System.out.println();
        System.out.println(BOLD + "  Jump back rule:" + RESET);
        System.out.println("    Play a card exactly 10 steps backwards on any pile.");
        System.out.println("    e.g. play 34 on UP pile with top [44]");
        System.out.println();
        System.out.println(BOLD + "  Turns:" + RESET);
        System.out.println("    You must play at least 2 cards before ending your turn.");
        System.out.println();
        System.out.println(BOLD + "  Win/Lose:" + RESET);
        System.out.println("    Win — deck empty and no cards left in hand");
        System.out.println("    Lose — no valid moves remaining");
        System.out.println("  " + "─".repeat(40));
        System.out.println();
    }

    private String prompt() {
        System.out.print(BOLD + "> " + RESET);
        return scanner.nextLine();
    }
}