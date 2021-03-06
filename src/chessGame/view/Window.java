package chessGame.view;

import chessGame.model.Log;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

/**
 * Window class
 */
public class Window {
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

        SwingUtilities.updateComponentTreeUI(window);
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
     * Set title of the main window
     *
     * @param s
     */
    public void setTitle(String s) {
        window.setTitle(s);
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
     * Board view holder, for destruction
     */
    chessGame.view.Board board;

    /**
     * Initialize board
     *
     * @param board
     */
    public void initializeBoard(chessGame.view.Board board) {
        this.board = board;
        panel.add(BorderLayout.CENTER, board);
        SwingUtilities.updateComponentTreeUI(window);
    }

    /**
     * Remove board from the frame
     */
    public void removeBoard() {
        if (board == null) return;
        panel.remove(board);
    }

    /**
     * Brunch of menu item
     */
    JMenuItem newUser;
    JMenuItem newGame;
    JMenuItem pauseGame;
    JMenuItem forfeitGame;
    JMenuItem scoreboard;
    JMenuItem undo;
    JMenuItem redo;

    /**
     * Set up pop down menu
     *
     * @param window
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();

        JMenu user = new JMenu("User");
        menubar.add(user);
        newUser = new JMenuItem("New User");
        user.add(newUser);

        JMenu game = new JMenu("Game");
        menubar.add(game);
        newGame = new JMenuItem("New Game");
        game.add(newGame);
        pauseGame = new JMenuItem("Pause Game");
        game.add(pauseGame);
        forfeitGame = new JMenuItem("Forfeit Game");
        game.add(forfeitGame);

        JMenu move = new JMenu("Move");
        menubar.add(move);
        undo = new JMenuItem("Undo");
        undo.setEnabled(false);
        move.add(undo);
        redo = new JMenuItem("Redo");
        redo.setEnabled(false);
        move.add(redo);

        JMenu history = new JMenu("History");
        menubar.add(history);
        scoreboard = new JMenuItem("Scoreboard");
        history.add(scoreboard);

        window.setJMenuBar(menubar);
    }

    /**
     * Event consumer
     */
    public Consumer<Log> undoChange = log -> {
        undo.setEnabled(log.canUndo());
    };

    /**
     * Event consumer
     */
    public Consumer<Log> redoChange = log -> {
        redo.setEnabled(log.canRedo());
    };

    /**
     * Add event listener to button new user
     * @param a
     */
    public void addNewUserListener(ActionListener a) {
        newUser.addActionListener(a);
    }

    /**
     * Add event listener to button new game
     * @param a
     */
    public void addNewGameListener(ActionListener a) {
        newGame.addActionListener(a);
    }

    /**
     * Add event listener to button pause game
     * @param a
     */
    public void addPauseGameListener(ActionListener a) {
        pauseGame.addActionListener(a);
    }

    /**
     * Add event listener to button forfeit
     * @param a
     */
    public void addForfeitGameListener(ActionListener a) {
        forfeitGame.addActionListener(a);
    }

    /**
     * Add event listener to scoreboard
     * @param a
     */
    public void addScordboardListener(ActionListener a) {
        scoreboard.addActionListener(a);
    }

    /**
     * Add event listener to undo
     * @param a
     */
    public void addUndoistener(ActionListener a) {
        undo.addActionListener(a);
    }

    /**
     * Add event listener to redo
     * @param a
     */
    public void addRedoistener(ActionListener a) {
        redo.addActionListener(a);
    }

}
