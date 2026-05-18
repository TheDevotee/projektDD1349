package src;
import javax.swing.BorderFactory;
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
    private JButton rulesButton;
    private JLabel deckLabel;
    private JLabel gameEndLabel;
    private JPanel rulesPanel;

    /**
     * Creates a new GameGUI with the given engine and state.
     *
     * @param engine the game engine
     * @param state  the game state
     */
    public GameGUI(GameEngine engine, GameState state) {
        this.engine = engine;
        this.state = state;

        window = new JFrame("The Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200, 800);
        window.setResizable(false);
        window.getContentPane().setBackground(backgroundColor);
        window.setLayout(null);

        createGameEndLabel();
        createRulesPanel();

        cardIcon = new ImageIcon(getClass().getClassLoader().getResource("src/card.png"));
        selectedCardIcon = new ImageIcon(getClass().getClassLoader().getResource("src/selected_card.png"));

        createGuiPile(200, 50, state.getPile(0));
        createGuiPile(300, 50, state.getPile(1));
        createGuiPile(400, 50, state.getPile(2));
        createGuiPile(500, 50, state.getPile(3));

        guiHand = new GUICard[8];
        updateHand();

        createEndTurnButton();
        updateEndTurnButton();
        createRulesButton();
        createDeckLabel();

        window.setVisible(true);
    }

    private void createEndTurnButton() {
        endTurnButton = new JButton("End Turn");
        endTurnButton.setBounds(800, 310, 260, 100);
        endTurnButton.setFont(font);
        endTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (engine.canEndTurn()) {
                    engine.endTurn();
                    checkWinAndLoss();
                    updateHand();
                    updateEndTurnButton();
                    updateDeckLabel();
                }
            }
        });
        window.add(endTurnButton);
    }

    private void createRulesButton() {
        rulesButton = new JButton("Read Rules");
        rulesButton.setBounds(800, 200, 260, 100);
        rulesButton.setFont(font);
        rulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rulesPanel.setVisible(!rulesPanel.isVisible());
            }
        });
        window.add(rulesButton);
    }

    private void createDeckLabel() {
        deckLabel = new JLabel("Cards in deck: " + String.valueOf(state.getDeck().size()));
        deckLabel.setBounds(800, 100, 400, 100);
        deckLabel.setFont(font);
        deckLabel.setForeground(Color.WHITE);
        window.add(deckLabel);
    }

    private void createGameEndLabel() {
        gameEndLabel = new JLabel("");
        gameEndLabel.setBounds(0, 0, 1140, 720);
        gameEndLabel.setFont(new Font("Arial", 0, 100));
        gameEndLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameEndLabel.setVisible(false);
        window.add(gameEndLabel);
    }

    private void createRulesPanel() {
        rulesPanel = new JPanel();
        rulesPanel.setBounds(100, 100, 600, 500);
        rulesPanel.setBackground(Color.WHITE);
        rulesPanel.setLayout(null);
        rulesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        StringBuilder rulesString = new StringBuilder();
        rulesString.append("<html><B>Goal:</B><br>Play all 98 cards onto the 4 piles.");
        rulesString.append("<br><B>Piles:</B><br>Play higher cards on the left piles and lower cards on the right piles.");
        rulesString.append("<br><B>Jump back rule</B>:<br>You can play a card exactly 10 steps backwards on any pile.");
        rulesString.append("<br><B>Turns:</B>:<br>You must play at least 2 cards every turn.");
        rulesString.append("<br><B>Win/lose:</B>:<br>You win when you have no more cards in the deck or your hand. You lose if you cannot play any of your cards.");
        rulesString.append("</html>");
        JLabel rulesLabel = new JLabel(rulesString.toString());
        rulesLabel.setBounds(10, 40, 590, 590);
        rulesLabel.setFont(new Font("Arial", 0, 25));
        rulesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rulesLabel.setVerticalAlignment(SwingConstants.TOP);
        rulesPanel.add(rulesLabel);
        rulesPanel.setVisible(false);
        window.add(rulesPanel);
    }

    private void updateDeckLabel() {
        deckLabel.setText("Cards in deck: " + String.valueOf(state.getDeck().size()));
    }

    private void checkWinAndLoss() {
        if (engine.isGameWon()) {
            gameEndLabel.setText("You Won!");
            gameEndLabel.setForeground(Color.GREEN);
            gameEndLabel.setVisible(true);
        } else if (engine.isGameOver() && !engine.canEndTurn()) {
            gameEndLabel.setText("You Lost!");
            gameEndLabel.setForeground(Color.RED);
            gameEndLabel.setVisible(true);
            endTurnButton.setVisible(false);
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

    private void updateEndTurnButton() {
        endTurnButton.setEnabled(engine.canEndTurn());
    }

    private GUIPile createGuiPile(int x, int y, Pile pile) {
        GUIPile guiPile = new GUIPile(x, y, pile);
        guiPile.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (selectedGuiCard != null) {
                    engine.playCard(selectedGuiCard.getCard(), pile);
                    updateHand();
                    updateEndTurnButton();
                    guiPile.update();
                    checkWinAndLoss();
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
}