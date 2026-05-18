import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Creates a GUI window displaying the game state
 * Handles interaction through the mouse
 */
public class GameGUI {
    private final GameEngine engine;
    private final GameState state;

    private final Color backgroundColor = new Color(35, 100, 60);
    private final Font font = new Font("Arial", 0, 30);

    private final ImageIcon cardIcon;
    private final ImageIcon selectedCardIcon;

    private final JFrame window;
    private GUICard selectedGuiCard;
    private GUICard[] guiHand;
    private JButton endTurnButton;
    private JLabel deckLabel;
    private JLabel gameEndLabel;

    /**
     * Creates a new GameGUI with the given engine and state.
     *
     * @param engine the game engine
     * @param state  the game state
     */
    public GameGUI(GameEngine engine, GameState state) {
        this.engine = engine;
        this.state = state;
        engine.endTurn();

        window = new JFrame("The Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200, 800);
        window.setResizable(false);
        window.getContentPane().setBackground(backgroundColor);
        window.setLayout(null);

        gameEndLabel = new JLabel("");
        gameEndLabel.setBounds(0, 0, 1140, 720);
        gameEndLabel.setFont(new Font("Arial", 0, 100));
        gameEndLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameEndLabel.setVisible(false);
        window.add(gameEndLabel);

        cardIcon = new ImageIcon(getClass().getClassLoader().getResource("card.png"));
        selectedCardIcon = new ImageIcon(getClass().getClassLoader().getResource("selected_card.png"));

        createGuiPile(200, 50, state.getPile(0));
        createGuiPile(300, 50, state.getPile(1));
        createGuiPile(400, 50, state.getPile(2));
        createGuiPile(500, 50, state.getPile(3));

        guiHand = new GUICard[8];
        updateHand();

        endTurnButton = new JButton("End Turn");
        endTurnButton.setBounds(800, 200, 260, 100);
        endTurnButton.setFont(font);
        endTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (engine.canEndTurn()) {
                    engine.endTurn();
                    checkWinAndLoss();
                    updateHand();
                    updateDeckLabel();
                }
            }
        });
        window.add(endTurnButton);

        deckLabel = new JLabel("Cards in deck: " + String.valueOf(state.getDeck().size()));
        deckLabel.setBounds(800, 100, 400, 100);
        deckLabel.setFont(font);
        deckLabel.setForeground(Color.WHITE);
        window.add(deckLabel);

        window.setVisible(true);
    }

    private void updateDeckLabel() {
        deckLabel.setText("Cards in deck: " + String.valueOf(state.getDeck().size()));
    }

    private void checkWinAndLoss() {
        if (engine.isGameWon()) {
            gameEndLabel.setText("You Won!");
            gameEndLabel.setForeground(Color.GREEN);
            gameEndLabel.setVisible(true);
        } else if (engine.isGameOver()) {
            gameEndLabel.setText("You Lost!");
            gameEndLabel.setForeground(Color.RED);
            gameEndLabel.setVisible(true);
        }
    }

    private void selectGuiCard(GUICard card) {
        if (selectedGuiCard != null) {
            JLabel selectedCardImage = (JLabel) selectedGuiCard.getComponent(1);
            selectedCardImage.setIcon(cardIcon);
        }
        JLabel cardImage = (JLabel) card.getComponent(1);
        cardImage.setIcon(selectedCardIcon);
        selectedGuiCard = card;
    }

    private GUICard createGuiCard(int x, int y, Card card) {
        GUICard guiCard = new GUICard(x, y, card);
        guiCard.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                selectGuiCard(guiCard);
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });
        window.add(guiCard);
        return guiCard;
    }

    private void updateHand() {
        List<Card> hand = new ArrayList<>(state.getPlayer().getHand());
        hand.sort((a, b) -> a.getValue() - b.getValue());
        selectedGuiCard = null;
        for (int i = 0; i < 8; i++) {
            if (guiHand[i] != null) {
                window.remove(guiHand[i]);
                guiHand[i] = null;
            }
        }
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i) != null) {
                GUICard guiCard = createGuiCard(170 + i * 100, 450, hand.get(i));
                guiHand[i] = guiCard;
            }
        }
        window.repaint();
    }

    private GUIPile createGuiPile(int x, int y, Pile pile) {
        GUIPile guiPile = new GUIPile(x, y, pile);
        guiPile.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (selectedGuiCard != null) {
                    engine.playCard(selectedGuiCard.getCard(), pile);
                    updateHand();
                    guiPile.update();
                }
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });
        window.add(guiPile);
        return guiPile;
    }

    public static void main(String[] args) {
        Pile[] piles = {
            new Pile(Direction.UP),
            new Pile(Direction.UP),
            new Pile(Direction.DOWN),
            new Pile(Direction.DOWN)
        };
        GameState state = new GameState(new Player(), new Deck(), piles);
        GameEngine engine = new GameEngine(state, new MoveValidator());
        new GameGUI(engine, state);
    }
}