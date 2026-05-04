/**
 * The game engine that ties all components together.
 * Handles turn logic, card drawing and win/loss conditions.
 * All interaction with the game goes through this class.
 */
public class GameEngine {

    private final GameState state;
    private final MoveValidator validator;

    /**
     * Creates a new GameEngine with the given state and validator.
     *
     * @param state     the current game state
     * @param validator the validator used to check moves
     */
    public GameEngine(GameState state, MoveValidator validator) {
        this.state = state;
        this.validator = validator;
    }

    /**
     * Attempts to play a card onto a pile.
     * The move is validated before being executed.
     * Checks for win and loss conditions after a successful move.
     *
     * @param card the card to play
     * @param pile the pile to play the card onto
     * @return {@code MoveResult.INVALID}  if the move breaks the rules,
     *         {@code MoveResult.GAME_WON} if the player has won,
     *         {@code MoveResult.GAME_OVER} if no valid moves remain,
     *         {@code MoveResult.SUCCESS}  if the move was successful
     */
    public MoveResult playCard(Card card, Pile pile) {
        if (!validator.validate(card, pile)) {
            return MoveResult.INVALID;
        }
        pile.place(card);
        state.getPlayer().removeCard(card);

        if (isGameWon()) return MoveResult.GAME_WON;
        if (isGameOver()) return MoveResult.GAME_OVER;

        return MoveResult.SUCCESS;
    }

    /**
     * Ends the current turn by drawing cards from the deck
     * until the player's hand is full or the deck is empty.
     * The hand size is capped at 6 cards.
     */
    public void endTurn() {
        Player player = state.getPlayer();
        Deck deck = state.getDeck();

        while (player.getHandSize() < 6 && !deck.isEmpty()) {
            player.addCard(deck.draw());
        }
    }

    // Checks if game is lost/won is checked after every card played
    
    /**
     * Checks whether the game is over.
     * The game is over when no card in the player's hand can be legally placed on any pile.
     *
     * @return {@code true} if no valid moves remain, {@code false} otherwise
     */
    public boolean isGameOver() {
        Player player = state.getPlayer();
        for (Card card : player.getHand()) {
            for (Pile pile : state.getPiles()) {
                if (validator.validate(card, pile)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the game has been won.
     * The game is won when the deck is empty and the player has no cards left in hand.
     *
     * @return {@code true} if the player has won, {@code false} otherwise
     */
    public boolean isGameWon() {
        return state.getDeck().isEmpty()
            && state.getPlayer().getHandSize() == 0;
    }
}