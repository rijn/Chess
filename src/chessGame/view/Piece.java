package chessGame.view;

import chessGame.util.Movement;
import chessGame.model.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Vector;
import java.util.function.Function;

/**
 * Piece view class
 */
public class Piece extends JLabel implements ActionListener {
    /**
     * Reference of the piece
     */
    chessGame.model.Piece piece;

    /**
     * Current coordinate on panel
     */
    Movement currentMovement;

    /**
     * Timer for animation
     */
    javax.swing.Timer timer;

    /**
     * Constructor
     *
     * @param piece
     */
    public Piece(chessGame.model.Piece piece) {
        this.piece = piece;

        setUpIcon();

        setUpClickEvent();

        currentMovement = new Movement(0, 0);
        currentMovement.x = getCoordX(piece.currentMovement());
        currentMovement.y = getCoordY(piece.currentMovement());
        move();

        setUpMoveAnimation();
    }

    /**
     * Set up the icon based on the type of the piece
     */
    public void setUpIcon() {
        URL resource = Piece.class.getResource("icon/" + piece.name + (piece.color == PieceColor.BLACK ? "_BLACK" : "_WHITE") + ".gif");
        if (resource != null) {
            setIcon(new ImageIcon(resource));
        }
        setSize(32, 32);
        // setSize(getPreferredSize());
    }

    /**
     * Register the click event
     */
    public void setUpClickEvent() {
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ((Board)getParent()).clearFocus();
                focus(true);
            }
        });
    }

    /**
     * Set up timer and movement animation
     */
    public void setUpMoveAnimation() {
        if (piece.events.get("AFTER_MOVE") == null) {
            piece.events.put("AFTER_MOVE", new Vector<Function<chessGame.model.Board, chessGame.model.Piece>>());
        }
        piece.events.get("AFTER_MOVE").add((chessGame.model.Board _board) -> {
            timer.start();
            return null;
        });

        if (piece.events.get("BE_CAPTURED") == null) {
            piece.events.put("BE_CAPTURED", new Vector<Function<chessGame.model.Board, chessGame.model.Piece>>());
        }
        piece.events.get("BE_CAPTURED").add((chessGame.model.Board _board) -> {
            Container board = getParent();
            board.remove(this);
            SwingUtilities.updateComponentTreeUI(board);
            return null;
        });

        setUpTimer();
    }

    /**
     * Set up the timer
     */
    public void setUpTimer() {
        timer = new javax.swing.Timer(25, this);
        timer.setRepeats(true);
    }

    /**
     * Event performer of the timer
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.currentMovement.move(getCoordX(piece.currentMovement()) > currentMovement.x ? 2 : -2, getCoordY(piece.currentMovement()) > currentMovement.y ? 1 : -1);
        if (Math.abs(getCoordX(piece.currentMovement()) - currentMovement.x) <= 2) {
            currentMovement.x = getCoordX(piece.currentMovement());
        }
        if (Math.abs(getCoordY(piece.currentMovement()) - currentMovement.y) <= 1) {
            currentMovement.y = getCoordY(piece.currentMovement());
        }

        move();

        if (Math.abs(getCoordX(piece.currentMovement()) - currentMovement.x) <= 2 &&
            Math.abs(getCoordY(piece.currentMovement()) - currentMovement.y) <= 2) {
            ((Timer)e.getSource()).stop();
        }
    }

    /**
     * Boolean to indicate the current piece's focusing status
     */
    Boolean focused = false;

    /**
     * Change focus status
     *
     * @param v
     */
    void focus(Boolean v) {
        this.focused = v;
        if (focused) {
            ((Board)getParent()).displayAvailableMovements(this);
        }
        setLocation(currentMovement.x, currentMovement.y - (v ? 3 : 0));
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Toggle of focus status
     *
     * **deprecated**
     */
    void toggle() {
        focus(!focused);
    }

    /**
     * Move to current coordinate
     */
    void move() {
        setLocation(currentMovement.x, currentMovement.y);
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Get coord x
     *
     * @param movement
     * @return x
     */
    int getCoordX(Movement movement) {
        return (movement.x + movement.y) * 30 + 25;
    }

    /**
     * Get coord y
     *
     * @param movement
     * @return y
     */
    int getCoordY(Movement movement) {
        return (movement.y - movement.x + 7) * 15;
    }
}
