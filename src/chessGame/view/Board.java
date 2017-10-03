package chessGame.view;

import chessGame.util.Constant;
import chessGame.util.Movement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * Board view class
 */
public class Board extends ImagePanel {
    /**
     * Reference to the board model
     */
    chessGame.model.Board board;

    /**
     * Round bounding
     */
    public chessGame.model.Round round = null;

    /**
     * Space matrix
     */
    Space[][] spaces = new Space[Constant.BOARD_SIZE_X][Constant.BOARD_SIZE_Y];

    /**
     * From movement
     */
    Movement from;
    /**
     * To movement
     */
    Movement to;

    /**
     * Move event listener
     */
    BiConsumer<Movement, Movement> moveListener;

    /**
     * Constructor
     *
     * @param board
     */
    public Board(chessGame.model.Board board, chessGame.model.Round round) {
        super(new ImageIcon(Piece.class.getResource("board.gif")).getImage());

        this.board = board;
        this.round = round;

        setBackground(Color.white);
        setPreferredSize(new Dimension(500,300));
        setLayout(null);

        board.getPieces().stream()
                .forEach(piece -> {
                    Piece _piece = new Piece(piece);
                    add(_piece);
                });

        for (int x = 0; x < Constant.BOARD_SIZE_X; x++) {
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y++) {
                Space _space = new Space(new Movement(x, y));
                spaces[x][y] = _space;
                add(_space);
            }
        }

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                clearFocus();
            }
        });
    }

    /**
     * Hide all available movements indicators
     */
    public void hideAvailableMovements() {
        Arrays.stream(this.getComponents()).forEach(c -> {
            if (c instanceof Space) {
                ((Space)c).display(false);
            }
        });
    }

    /**
     * Show all available movements of the piece
     *
     * @param piece
     */
    public void displayAvailableMovements(Piece piece) {
        if (round.current().color != piece.piece.color) return;
        board.getAvailableMovementsOfPiece(piece.piece.currentMovement()).stream()
                .forEach(m -> {
                    spaces[m.x][m.y].display(true);
                });
    }

    /**
     * Clean all focus status
     */
    public void clearFocus() {
        hideAvailableMovements();
        Arrays.stream(this.getComponents()).forEach(c -> {
            if (c instanceof Piece) {
                ((Piece)c).focus(false);
            }
        });
        from = null;
    }

    /**
     * Set chess move listener
     * @param fn
     */
    public void setMoveListener(BiConsumer<Movement, Movement> fn) {
        this.moveListener = fn;
    }

    /**
     * Move to helper, listener caller
     * @param to
     */
    public void moveTo(Movement to) {
        this.to = to;
        if (from != null) {
            moveListener.accept(from, to);
        }
        clearFocus();
    }
}
