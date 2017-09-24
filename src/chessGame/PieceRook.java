package chessGame;

/**
 * Piece rook
 * Can go vertical or horizontal
 */
class PieceRook extends Piece {
    PieceRook(PieceColor color) {
        this.color = color;
        name = "ROOK";
        shortName = color == PieceColor.BLACK ? '♖' : '♜';

        factorMovements = new Integer[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    }
}
