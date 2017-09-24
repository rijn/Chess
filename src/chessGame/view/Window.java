package chessGame.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {
    JPanel panel;
    JFrame window;

    public Window() {
        initializeFrame();
        setUpMenu(window);
        initializePanel();
    }

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

    private void initializeButton(JPanel myPanel) {
        JButton button = new JButton("Click me");
        button.addActionListener(this);
        myPanel.add(button, BorderLayout.SOUTH);
    }

    private void initializePanel() {
        this.panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setPreferredSize(new Dimension(600,400));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        window.getContentPane().add(panel);
    }

    public void initializeBoard(chessGame.Board board) {
        panel.add(BorderLayout.CENTER, new Board(board));
        SwingUtilities.updateComponentTreeUI(window);
    }

    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("Game");
        menubar.add(file);
        window.setJMenuBar(menubar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                "I was clicked by "+e.getActionCommand(),
                "Title here", JOptionPane.INFORMATION_MESSAGE);
    }
}
