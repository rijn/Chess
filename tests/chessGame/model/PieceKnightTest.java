package chessGame.model;

import chessGame.model.Board;
import chessGame.model.PieceColor;
import chessGame.model.PieceKnight;
import chessGame.util.Movement;
import junit.framework.TestCase;

public class PieceKnightTest extends TestCase {
    public void testGetAvailableMovements() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        board.insertPiece(new PieceKnight(PieceColor.BLACK), 3, 3);
        assertEquals(board.getPiece(3, 3).getAvailableMovements(new Movement(3, 3), board).size(), 8);
    }
}