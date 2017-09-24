package chessGame.view;

import chessGame.Movement;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Space extends JLabel {
    chessGame.Movement currentMovement;

    public Space(Movement movement) {
        URL resource = Piece.class.getResource("mark-1.gif");
        if (resource != null) {
            setIcon(new ImageIcon(resource));
        }
        setSize(getPreferredSize());

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            }
        });

        currentMovement = new Movement(getCoordX(movement), getCoordY(movement));
        move();

        display(false);
    }

    public void display(Boolean v) {
        setVisible(v);
    }

    void move() {
        setLocation(currentMovement.x, currentMovement.y);
        SwingUtilities.updateComponentTreeUI(this);
    }

    int getCoordX(Movement movement) {
        return (movement.x + movement.y) * 30 + 16;
    }

    int getCoordY(Movement movement) {
        return (movement.y - movement.x + 7) * 15 + 15;
    }
}
