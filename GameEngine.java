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

}