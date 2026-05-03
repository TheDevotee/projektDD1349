/**
 * Represents the current state of the game.
 * Holds the player, the deck and the four piles.
 */
public class GameState {

    private final Player player;
    private final Deck deck;
    private final Pile[] piles;

    /**
     * Creates a new GameState with the given player, deck and piles.
     *
     * @param player the solo player
     * @param deck   the draw pile
     * @param piles  the four piles (2 ascending, 2 descending)
     */
    public GameState(Player player, Deck deck, Pile[] piles) {
        this.player = player;
        this.deck   = deck;
        this.piles  = piles;
    }

    /**
     * Returns the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the deck.
     *
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Returns the four piles.
     *
     * @return array of piles
     */
    public Pile[] getPiles() {
        return piles;
    }

    /**
     * Returns the pile at the given index.
     *
     * @param index pile index (0-3)
     * @return the pile at that index
     */
    public Pile getPile(int index) {
        return piles[index];
    }
}
