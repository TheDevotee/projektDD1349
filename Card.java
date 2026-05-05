/**
 * A card with an integer value
 */
public class Card {

    private int value;

    /**
     * Creates a new Card with the given value
     *
     * @param value the card's number
     */
    public Card(int value) {
        this.value = value;
    }

    /**
     * Returns the card's value
     *
     * @return the card's number
     */
    public int getValue() {
        return value;
    }

}