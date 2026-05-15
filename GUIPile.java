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

        ImageIcon cardIcon = new ImageIcon(getClass().getClassLoader().getResource("card.png"));
        JLabel bottomImage = new JLabel();
        bottomImage.setBounds(0, 0, WIDTH, HEIGHT);
        bottomImage.setIcon(cardIcon);
        add(bottomImage);

        topCardValueLabel = new JLabel();
        topCardValueLabel.setBounds(0, (int) (HEIGHT * 0.75), WIDTH, HEIGHT);
        topCardValueLabel.setText("");
        Font font = new Font("Times New Roman", 0, FONT_SIZE);
        topCardValueLabel.setFont(font);
        topCardValueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topCardImageLabel = new JLabel();
        topCardImageLabel.setBounds(0, (int) (HEIGHT * 0.75), WIDTH, HEIGHT);
        topCardImageLabel.setIcon(cardIcon);
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
            add(topCardValueLabel);
            add(topCardImageLabel);
            topCardValueLabel.setText(String.valueOf(pile.getTopCard().getValue()));
        }
    }

}