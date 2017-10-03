package chessGame.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewUserWindow {
    JFrame window;
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

    public void dispose() {
        window.dispose();
    }

    private JPanel initializePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300,100));
        panel.setLayout(new BorderLayout());
        return panel;
    }

    JButton registerUser;
    private void initializeButton(JPanel panel) {
        registerUser = new JButton("Register");
        panel.add(registerUser, BorderLayout.SOUTH);
    }

    public void addRegisterUserListener(ActionListener a) {
        registerUser.addActionListener(a);
    }

    JTextArea textArea;
    private void initializeTextArea(JPanel panel) {
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createCompoundBorder(
                panel.getBorder(),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)));
        panel.add(textArea, BorderLayout.NORTH);
    }

    public String getUserName() {
        return textArea.getText();
    }
}
