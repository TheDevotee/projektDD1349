import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * A graphical representation of a Pile.
 * Contains a reference to a Pile, 2 images and text.
 */
public class GUIPile extends JPanel {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 200;
    private static final int FONT_SIZE = 30;

    private Pile pile;
    private JLabel topCardImageLabel;
    private JLabel topCardValueLabel;

    /**
     * Creates a new GUIPile with the given coordinates and pile object
     *
     * @param x    the x coordinate
     * @param y    the y coordinate
     * @param pile the Pile object
     */
    public GUIPile(int x, int y, Pile pile) {
        setBounds(x, y, WIDTH, HEIGHT * 2);
        setBackground(null);
        setLayout(null);
        this.pile = pile;

        Font font = new Font("Arial", 0, FONT_SIZE);
        topCardValueLabel = new JLabel();
        topCardValueLabel.setBounds(0, (int) (HEIGHT * 0.75), WIDTH, HEIGHT);
        topCardValueLabel.setText("");
        topCardValueLabel.setFont(font);
        topCardValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topCardValueLabel.setVisible(false);
        add(topCardValueLabel);

        ImageIcon cardIcon = new ImageIcon(getClass().getClassLoader().getResource("card.png"));
        topCardImageLabel = new JLabel();
        topCardImageLabel.setBounds(0, (int) (HEIGHT * 0.75), WIDTH, HEIGHT);
        topCardImageLabel.setIcon(cardIcon);
        topCardImageLabel.setVisible(false);
        add(topCardImageLabel);

        ImageIcon bottomCardIcon;
        if (pile.getDirection() == Direction.UP) {
            bottomCardIcon = new ImageIcon(getClass().getClassLoader().getResource("card1.png"));
        } else {
            bottomCardIcon = new ImageIcon(getClass().getClassLoader().getResource("card100.png"));
        }
        JLabel bottomImage = new JLabel();
        bottomImage.setBounds(0, 0, WIDTH, HEIGHT);
        bottomImage.setIcon(bottomCardIcon);
        add(bottomImage);
    }

    /**
     * Returns the associated pile object
     *
     * @return the pile
     */
    public Pile getPile() {
        return pile;
    }

    /**
     * Updates the top card text to match the value of the pile's top card.
     */
    public void update() {
        Card topCard = pile.getTopCard();
        if (topCard != null) {
            topCardValueLabel.setVisible(true);
            topCardImageLabel.setVisible(true);
            topCardValueLabel.setText(String.valueOf(pile.getTopCard().getValue()));
        }
    }

}