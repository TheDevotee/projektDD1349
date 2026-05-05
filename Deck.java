import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Initially contains 98 cards with values 2-99 that have been shuffled.
 * Cards can be drawn from the deck.
 */
public class Deck {
    
    private ArrayList<Card> cards = new ArrayList<>(98);

    /**
     * Creates a new Deck, fills it with cards and shuffles them.
     */
    public Deck() {
        addCards();
        shuffle();
    }

    private void addCards() {
        for (int i = 2; i <= 99; i++) {
            Card card = new Card(i);
            cards.add(card);
        }
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Returns true if the deck has no cards.
     *
     * @return true if the deck is empty
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the number of cards in the deck
     *
     * @return number of cards
     */
    public int size() {
        return cards.size();
    }

    /**
     * Removes the last card from the deck and returns it.
     *
     * @return the last card
     */
    public Card draw() {
        Card card = cards.removeLast();
        return card;
    }

}
