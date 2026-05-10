public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Player player = new Player();
        Pile[] piles = {
            new Pile(Direction.UP),
            new Pile(Direction.UP),
            new Pile(Direction.DOWN),
            new Pile(Direction.DOWN)
        };

        // Draw initial hand (6 cards)
        for (int i = 0; i < 6; i++) {
            player.addCard(deck.draw());
        }

        GameState state   = new GameState(player, deck, piles);
        GameEngine engine = new GameEngine(state, new MoveValidator());
        new TerminalUI(engine, state).start();
    }
}