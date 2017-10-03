package chessGame.view;

import chessGame.util.Movement;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * Space on the board
 */
public class Space extends JLabel {
    /**
     * Current coordinate
     */
    Movement currentMovement;

    Movement _movement;

    /**
     * Constructor with coordinate
     *
     * @param movement
     */
    public Space(Movement movement) {
        URL resource = Piece.class.getResource("mark-1.gif");
        if (resource != null) {
            setIcon(new ImageIcon(resource));
        }
        setSize(getPreferredSize());

        this._movement = movement;

        currentMovement = new Movement(getCoordX(movement), getCoordY(movement));
        move();

        display(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ((Board)getParent()).moveTo(_movement);
            }
        });
    }

    /**
     * Display a notation or not
     *
     * @param v
     */
    public void display(Boolean v) {
        setVisible(v);
    }

    /**
     * MoveController to corresponding location on panel
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
        return (movement.x + movement.y) * 30 + 16;
    }

    /**
     * Get coord y
     *
     * @param movement
     * @return y
     */
    int getCoordY(Movement movement) {
        return (movement.y - movement.x + 7) * 15 + 15;
    }
}
