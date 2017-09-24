package chessGame;

/**
 * Bishop class
 */
public class PieceBishop extends Piece {
    PieceBishop(PieceColor color) {
        this.color = color;
        name = "BISHOP";
        shortName = color == PieceColor.BLACK ? '♗' : '♝';

        factorMovements = new Integer[][]{{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    }
}
