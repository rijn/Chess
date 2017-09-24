package chessGame;

/**
 * Queen class
 */
public class PieceQueen extends Piece {
    PieceQueen(PieceColor color) {
        this.color = color;
        name = "QUEEN";
        shortName = color == PieceColor.BLACK ? '♕' : '♛';

        factorMovements = new Integer[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    }
}