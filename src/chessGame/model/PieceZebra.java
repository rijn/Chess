package chessGame.model;

import chessGame.util.Movement;

import java.util.Arrays;

/**
 * Piece Zebra
 */
public class PieceZebra extends Piece {
    /**
     * Constructor
     *
     * @param color
     */
    PieceZebra(PieceColor color) {
        this.color = color;
        name = "ZEBRA";
        shortName = color == PieceColor.BLACK ? 'Z' : 'Z';

        presetMovements = Arrays.asList(new Movement[]{
                new Movement(-4, 3),
                new Movement(-3, 4),
                new Movement(3, 4),
                new Movement(4, 3),
                new Movement(4, -3),
                new Movement(3, -4),
                new Movement(-3, -4),
                new Movement(-4, -3)
        });
    }
}
