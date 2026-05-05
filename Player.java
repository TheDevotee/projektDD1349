import java.util.ArrayList;
import java.util.List;

/**
 * Meant to be the player (solo) by having all the cards
 */
public class Player {

    private final List<Card> hand = new ArrayList<>();

    /**
     * Adds a card to the player's hand.
     *
     * @param card the card to add
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Removes a card from the player's hand.
     *
     * @param card the card to remove
     */
    public void removeCard(Card card) {
        hand.remove(card);
    }

    /**
     * Returns the player's current hand.
     *
     * @return list of cards in hand
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Returns the number of cards in the player's hand.
     *
     * @return hand size
     */
    public int getHandSize() {
        return hand.size();
    }
}