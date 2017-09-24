package chessGame.view;

import chessGame.Movement;
import chessGame.PieceColor;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Vector;
import java.util.function.Supplier;

public class Piece extends JLabel implements ActionListener {
    chessGame.Piece piece;

    chessGame.Movement currentMovement;

    javax.swing.Timer timer;

    public Piece(chessGame.Piece piece) {
        this.piece = piece;

        URL resource = Piece.class.getResource("icon/" + piece.name + (piece.color == PieceColor.BLACK ? "_BLACK" : "_WHITE") + ".gif");
        if (resource != null) {
            setIcon(new ImageIcon(resource));
        }
        setSize(getPreferredSize());

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ((Board)getParent()).clearFocus();
                toggle(true);
            }
        });

        timer = new javax.swing.Timer(25, this);
        timer.setRepeats(true);

        currentMovement = new chessGame.Movement(0, 0);
        currentMovement.x = getCoordX(piece.currentMovement());
        currentMovement.y = getCoordY(piece.currentMovement());
        move();

        if (piece.events.get("AFTER_MOVE") == null) {
            piece.events.put("AFTER_MOVE", new Vector<Supplier<chessGame.Piece>>());
        }
        piece.events.get("AFTER_MOVE").add(() -> {
            timer.start();
            return null;
        });

        if (piece.events.get("BE_CAPTURED") == null) {
            piece.events.put("BE_CAPTURED", new Vector<Supplier<chessGame.Piece>>());
        }
        piece.events.get("BE_CAPTURED").add(() -> {
            Container board = getParent();
            board.remove(this);
            SwingUtilities.updateComponentTreeUI(board);
            return null;
        });
    }

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

    Boolean focused = false;

    void toggle(Boolean v) {
        this.focused = v;
        if (focused) {
            ((Board)getParent()).displayAvailableMovements(this);
        }
        setLocation(currentMovement.x, currentMovement.y - (v ? 3 : 0));
        SwingUtilities.updateComponentTreeUI(this);
    }

    void toggle() {
        toggle(!focused);
    }

    void move() {
        setLocation(currentMovement.x, currentMovement.y);
        SwingUtilities.updateComponentTreeUI(this);
    }

    int getCoordX(Movement movement) {
        return (movement.x + movement.y) * 30 + 25;
    }

    int getCoordY(Movement movement) {
        return (movement.y - movement.x + 7) * 15;
    }
}
