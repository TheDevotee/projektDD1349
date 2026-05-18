package src;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Choose mode:");
        System.out.println("1 - Terminal");
        System.out.println("2 - GUI");
        System.out.print("> ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        Pile[] piles = {
                new Pile(Direction.UP),
                new Pile(Direction.UP),
                new Pile(Direction.DOWN),
                new Pile(Direction.DOWN)
        };

        GameState state = new GameState(new Player(), new Deck(), piles);
        GameEngine engine = new GameEngine(state, new MoveValidator());

        if (choice.equals("1")) {
            new TerminalUI(engine, state).start();
        } else if (choice.equals("2")) {
            engine.endTurn(); 
            new GameGUI(engine, state);
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }

}