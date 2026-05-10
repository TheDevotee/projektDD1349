import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Test class for the Card, Deck and Pile.
 */
public class CardDeckPileTest {
    /**
     * Assert that getValue returns the correct value of a card
     */
    @Test
    public void cardGetValueReturnsCorrectValue() {
        // Arrange
        Card card = new Card(50);
        // Act, Assert
        assertThat(card.getValue(), equalTo(50));
    }

    /**
     * Assert that decks are shuffled, so that
     * draw returns different valued cards for different decks
     */
    @Test
    public void drawReturnsDifferentCardsFromDifferentDecks() {
        // Arrange
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        // Act
        boolean differentValue = false;
        for (int i = 0; i < 10; i++) {
            int value1 = deck1.draw().getValue();
            int value2 = deck2.draw().getValue();
            if (value1 != value2) {
                differentValue = true;
                break;
            }
        }
        // Assert
        assertTrue(differentValue);
    }

    /**
     * Assert that size of Deck returns 98
     */
    @Test
    public void deckSizeReturns98() {
        // Arrange
        Deck deck = new Deck();
        // Act, Assert
        assertThat(deck.size(), equalTo(98));
    }

    /**
     * Assert that draw reduces the size of deck
     */
    @Test
    public void drawReducesSizeOfDeck() {
        // Arrange
        Deck deck = new Deck();
        // Act
        for (int i = 0; i < 10; i++) {
            deck.draw();
        }
        // Assert
        assertThat(deck.size(), equalTo(98 - 10));
    }

    /**
     * Assert that isEmpty returns true for empty deck
     */
    @Test
    public void isEmptyReturnsTrueForEmptyDeck() {
        // Arrange
        Deck deck = new Deck();
        // Act
        for (int i = 0; i < 98; i++) {
            deck.draw();
        }
        // Assert
        assertTrue(deck.isEmpty());
    }

    /**
     * Assert that deck contains cards with values 2-99.
     */
    @Test
    public void DeckContainsValues2To98() {
        // Arrange
        Deck deck = new Deck();
        Boolean[] boolArray = new Boolean[98];
        Boolean[] expectedBoolArray = new Boolean[98];
        Arrays.fill(expectedBoolArray, true);
        // Act
        for (int i = 0; i < 98; i++) {
            Card card = deck.draw();
            boolArray[card.getValue() - 2] = true;
        }
        // Assert
        assertThat(boolArray, equalTo(expectedBoolArray));
    }

    /**
     * Assert that getDirection returns correct direction of Pile
     */
    @Test
    public void getDirectionReturnsCorrectDirectionOfPile() {
        // Arrange
        Pile upPile = new Pile(Direction.UP);
        Pile downPile = new Pile(Direction.DOWN);
        // Act, Assert
        assertThat(upPile.getDirection(), equalTo(Direction.UP));
        assertThat(downPile.getDirection(), equalTo(Direction.DOWN));
    }

    /**
     * Assert that place changes top card of Pile
     */
    @Test
    public void placeChangesTopCardOfPile() {
        // Arrange
        Pile pile = new Pile(Direction.UP);
        Card card = new Card(2);
        // Act
        pile.place(card);
        // Assert
        assertThat(pile.getTopCard(), equalTo(card));
    }
}