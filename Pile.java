enum Direction {
    UP, DOWN;
}

/**
 * A pile of cards with an up or down direction.
 * Cards can be placed on the pile but aren't validated.
 */
public class Pile {
    
    private Direction direction;
    private Card topCard;

    /**
     * Creates a new Pile with the specified direction,
     * and sets its top card accordingly.
     */
    public Pile(Direction direction) {
        this.direction = direction;
        if (direction == Direction.UP) {
            topCard = new Card(1);
        } else {
            topCard = new Card(100);
        }
    }

    /**
     * Places a new card on the top of the pile
     *
     * @param card the card to place
     */
    public void place(Card card) {
        topCard = card;
    }

    /**
     * Returns the top card of the pile.
     *
     * @return top card
     */
    public Card getTopCard() {
        return topCard;
    }

    /**
     * Returns the direction of the pile,
     * either Direction.UP or Direction.DOWN.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }
}
