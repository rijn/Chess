package chessGame;

import java.util.Arrays;

public class PieceKingKnight extends Piece {
    PieceKingKnight(PieceColor color) {
        this.color = color;
        name = "KingKnight";
        shortName = color == PieceColor.BLACK ? 'K' : 'K';

        presetMovements = Arrays.asList(new Movement[]{
                new Movement(-2, -1),
                new Movement(-1, -2),
                new Movement(+1, -2),
                new Movement(+2, -1),
                new Movement(+2, +1),
                new Movement(+1, +2),
                new Movement(-1, +2),
                new Movement(-2, +1),
                new Movement(-1, -1),
                new Movement(-1, 0),
                new Movement(-1, 1),
                new Movement(0, 1),
                new Movement(1, 1),
                new Movement(1, 0),
                new Movement(1, -1),
                new Movement(0, -1)
        });
    }
}
