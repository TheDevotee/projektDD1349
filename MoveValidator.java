/**
 * Validates whether a card can be placed on a pile.
 * A move is valid if:
 * - The card is strictly higher than the top card (UP pile), or
 * - The card is strictly lower than the top card (DOWN pile), or
 * - The card is exactly 10 less than the top card (UP pile "jump back"), or
 * - The card is exactly 10 more than the top card (DOWN pile "jump back").
 */
public class MoveValidator {

    /**
     * Returns true if the card can legally be placed on the pile.
     *
     * @param card the card to play
     * @param pile the pile to play on
     * @return true if the move is valid
     */
    public boolean validate(Card card, Pile pile) {
        int cardValue = card.getValue();
        int topValue  = pile.getTopCard().getValue();

        if (pile.getDirection() == Direction.UP) {
            return cardValue > topValue || topValue - cardValue == 10;
        } else {
            return cardValue < topValue || cardValue - topValue == 10;
        }
    }
}
