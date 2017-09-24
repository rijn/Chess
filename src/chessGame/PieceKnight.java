package chessGame;

import java.util.Arrays;

/**
 * Knight class
 */
public class PieceKnight extends Piece {
    PieceKnight(PieceColor color) {
        this.color = color;
        name = "KNIGHT";
        shortName = color == PieceColor.BLACK ? '♘' : '♞';

        presetMovements = Arrays.asList(new Movement[]{
                new Movement(-2, -1),
                new Movement(-1, -2),
                new Movement(+1, -2),
                new Movement(+2, -1),
                new Movement(+2, +1),
                new Movement(+1, +2),
                new Movement(-1, +2),
                new Movement(-2, +1)
        });
    }
}