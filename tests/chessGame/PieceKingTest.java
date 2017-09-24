package chessGame;

import junit.framework.TestCase;

public class PieceKingTest extends TestCase {
    public void testGetAvailableMovements() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        board.insertPiece(new PieceKing(PieceColor.BLACK), 3, 3);
        assertEquals(board.getPiece(3, 3).getAvailableMovements(new Movement(3, 3), board).size(), 8);
    }
}