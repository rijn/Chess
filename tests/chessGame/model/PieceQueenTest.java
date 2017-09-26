package chessGame.model;

import chessGame.model.Board;
import chessGame.model.PieceColor;
import chessGame.model.PieceQueen;
import chessGame.util.Movement;
import junit.framework.TestCase;

public class PieceQueenTest extends TestCase {
    public void testGetAvailableMovements() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        board.insertPiece(new PieceQueen(PieceColor.BLACK), 0, 0);
        assertEquals(board.getPiece(0, 0).getAvailableMovements(new Movement(0, 0), board).size(), 21);
    }

}