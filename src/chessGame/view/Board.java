package chessGame.view;

import chessGame.Constant;
import chessGame.Movement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Board extends ImagePanel {
    chessGame.Board board;

    Space[][] spaces = new Space[Constant.BOARD_SIZE_X][Constant.BOARD_SIZE_Y];

    public Board(chessGame.Board board) {
        super(new ImageIcon(Piece.class.getResource("board.gif")).getImage());

        this.board = board;

        setBackground(Color.white);
        setPreferredSize(new Dimension(500,300));
        setLayout(null);

        board.getPieces().stream()
                .forEach(piece -> add(new Piece(piece)));

        for (int x = 0; x < Constant.BOARD_SIZE_X; x++) {
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y++) {
                spaces[x][y] = new Space(new Movement(x, y));
                add(spaces[x][y]);
            }
        }

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                clearFocus();
            }
        });
    }

    public void hideAvailableMovements() {
        Arrays.stream(this.getComponents()).forEach(c -> {
            if (c instanceof Space) {
                ((Space)c).display(false);
            }
        });
    }

    public void displayAvailableMovements(Piece piece) {
        board.getAvailableMovementsOfPiece(piece.piece.currentMovement()).stream()
                .forEach(m -> {
                    spaces[m.x][m.y].display(true);
                });
    }

    public void clearFocus() {
        hideAvailableMovements();
        Arrays.stream(this.getComponents()).forEach(c -> {
            if (c instanceof Piece) {
                ((Piece)c).toggle(false);
            }
        });
    }
}
