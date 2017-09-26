package chessGame.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window class
 */
public class Window implements ActionListener {
    /**
     * Main container
     */
    JPanel panel;

    /**
     * Main frame
     */
    JFrame window;

    /**
     * Window Constructor
     *
     * Initialize window and view
     */
    public Window() {
        initializeFrame();
        setUpMenu(window);
        initializePanel();
    }

    /**
     * Initialize the frame
     */
    private void initializeFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }

        window = new JFrame("Chess");
        window.setSize(650, 400);
        window.getContentPane().setBackground(Color.white);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initialize Buttons
     *
     * **deprecated**
     *
     * @param myPanel
     */
    private void initializeButton(JPanel myPanel) {
        JButton button = new JButton("Click me");
        button.addActionListener(this);
        myPanel.add(button, BorderLayout.SOUTH);
    }

    /**
     * Initialize main panel
     */
    private void initializePanel() {
        this.panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setPreferredSize(new Dimension(600,400));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        window.getContentPane().add(panel);
    }

    /**
     * Initialize board
     *
     * @param board
     */
    public void initializeBoard(chessGame.model.Board board) {
        panel.add(BorderLayout.CENTER, new Board(board));
        SwingUtilities.updateComponentTreeUI(window);
    }

    /**
     * Set up pop down menu
     *
     * @param window
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("Game");
        menubar.add(file);
        window.setJMenuBar(menubar);
    }

    /**
     * Override action performer
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                "I was clicked by "+e.getActionCommand(),
                "Title here", JOptionPane.INFORMATION_MESSAGE);
    }
}
