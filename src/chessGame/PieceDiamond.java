package chessGame;

import java.util.Arrays;

public class PieceDiamond extends Piece {
    PieceDiamond(PieceColor color) {
        this.color = color;
        name = "Diamond";
        shortName = color == PieceColor.BLACK ? 'D' : 'D';

        presetMovements = Arrays.asList(new Movement[]{
                new Movement(-2, -1),
                new Movement(-1, -2),
                new Movement(+1, -2),
                new Movement(+2, -1),
                new Movement(+2, +1),
                new Movement(+1, +2),
                new Movement(-1, +2),
                new Movement(-2, +1),
                new Movement(-3, 0),
                new Movement(0, 3),
                new Movement(3, 0),
                new Movement(0, -3)
        });
    }
}

