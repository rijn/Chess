package chessGame.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class NewGameWindow {
    JFrame window;
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

    public void dispose() {
        window.dispose();
    }

    private JPanel initializePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300,150));
        panel.setLayout(new BorderLayout());
        return panel;
    }

    JButton newGame;
    private void initializeButton(JPanel panel) {
        newGame = new JButton("New Game");
        panel.add(newGame, BorderLayout.SOUTH);
    }

    public void addNewGameListener(ActionListener a) {
        newGame.addActionListener(a);
    }

    JTextArea whitePlayerName, blackPlayerName;
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

    public String getWhitePlayerName() {
        return whitePlayerName.getText();
    }

    public String getBlackPlayerName() {
        return blackPlayerName.getText();
    }
}
