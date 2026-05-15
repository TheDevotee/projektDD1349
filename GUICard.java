import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

/**
 * A graphical representation of a Card.
 * Contains an image, text and a reference to a Card object.
 */
public class GUICard extends JPanel {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 200;
    private static final int FONT_SIZE = 30;

    private Card card;

    /**
     * Creates a new GUICard with the given coordinates and card object
     *
     * @param x    the x coordinate
     * @param y    the y coordinate
     * @param card the Card object
     */
    public GUICard(int x, int y, Card card) {
        setBounds(x, y, WIDTH, HEIGHT);
        setBackground(null);
        setLayout(null);
        this.card = card;

        JLabel valueLabel = new JLabel();
        valueLabel.setBounds(0, 0, WIDTH, HEIGHT);
        valueLabel.setText(String.valueOf(card.getValue()));
        Font font = new Font("Times New Roman", 0, FONT_SIZE);
        valueLabel.setFont(font);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(valueLabel);
        
        ImageIcon cardIcon = new ImageIcon(getClass().getClassLoader().getResource("card.png"));
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, WIDTH, HEIGHT);
        imageLabel.setIcon(cardIcon);
        add(imageLabel);
    }

    /**
     * Returns the associated card object
     *
     * @return the card
     */
    public Card getCard() {
        return card;
    }
}