package chessGame.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Window class for new game
 */
public class NewGameWindow {
    /**
     * Main frame
     */
    JFrame window;

    /**
     * Constructor
     */
    public NewGameWindow() {
        window = new JFrame("New Game");
        window.setSize(300, 150);
        JPanel panel = initializePanel();

        initializeButton(panel);
        initializeTextArea(panel);

        window.setContentPane(panel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Disposer
     */
    public void dispose() {
        window.dispose();
    }

    /**
     * Initialize panel
     * @return
     */
    private JPanel initializePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300,150));
        panel.setLayout(new BorderLayout());
        return panel;
    }

    /**
     * Button for starting a new game
     */
    JButton newGame;

    /**
     * Initialize button
     * @param panel
     */
    private void initializeButton(JPanel panel) {
        newGame = new JButton("New Game");
        panel.add(newGame, BorderLayout.SOUTH);
    }

    /**
     * Add event listener to the new game button
     * @param a
     */
    public void addNewGameListener(ActionListener a) {
        newGame.addActionListener(a);
    }

    /**
     * Text area for inputing username
     */
    JTextArea whitePlayerName, blackPlayerName;

    /**
     * initialize text area
     * @param panel
     */
    private void initializeTextArea(JPanel panel) {
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2,2));
        whitePlayerName = new JTextArea();
        whitePlayerName.setBorder(BorderFactory.createCompoundBorder(
                panel.getBorder(),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)));
        blackPlayerName = new JTextArea();
        blackPlayerName.setBorder(BorderFactory.createCompoundBorder(
                panel.getBorder(),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)));
        controls.add(new JLabel("WHITE"));
        controls.add(whitePlayerName);
        controls.add(new JLabel("BLACK"));
        controls.add(blackPlayerName);

        panel.add(controls, BorderLayout.NORTH);
    }

    /**
     * get username of white player
     * @return
     */
    public String getWhitePlayerName() {
        return whitePlayerName.getText();
    }

    /**
     * get username of black player
     * @return
     */
    public String getBlackPlayerName() {
        return blackPlayerName.getText();
    }
}
