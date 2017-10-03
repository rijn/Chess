package chessGame.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window for new user
 */
public class NewUserWindow {
    /**
     * Main frame
     */
    JFrame window;

    /**
     * New window constructor
     */
    public NewUserWindow() {
        window = new JFrame("New User");
        window.setSize(300, 100);
        JPanel panel = initializePanel();

        initializeButton(panel);
        initializeTextArea(panel);

        window.setContentPane(panel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Window disposer
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
        panel.setPreferredSize(new Dimension(300,100));
        panel.setLayout(new BorderLayout());
        return panel;
    }

    /**
     * Button of register user
     */
    JButton registerUser;

    /**
     * Initialize button
     * @param panel
     */
    private void initializeButton(JPanel panel) {
        registerUser = new JButton("Register");
        panel.add(registerUser, BorderLayout.SOUTH);
    }

    /**
     * Add event listener to the button register user
     * @param a
     */
    public void addRegisterUserListener(ActionListener a) {
        registerUser.addActionListener(a);
    }

    /**
     * Text area for username
     */
    JTextArea textArea;

    /**
     * Initialize text area
     * @param panel
     */
    private void initializeTextArea(JPanel panel) {
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createCompoundBorder(
                panel.getBorder(),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)));
        panel.add(textArea, BorderLayout.NORTH);
    }

    /**
     * Get username that user typed in
     * @return
     */
    public String getUserName() {
        return textArea.getText();
    }
}
