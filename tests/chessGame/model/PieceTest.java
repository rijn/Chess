package chessGame.model;

import chessGame.model.Board;
import chessGame.model.Piece;
import chessGame.model.PieceColor;
import chessGame.model.PieceKnight;
import chessGame.util.Movement;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Vector;

public class PieceTest extends TestCase {
    public void testMoveTo() throws Exception {
        Piece testPiece = new Piece();
        assertEquals(testPiece.moveLog.size(), 0);
        testPiece.moveTo(new Movement(0, 0));
        assertEquals(testPiece.moveLog.size(), 1);
    }

    public void testCurrentMovement() throws Exception {
        Piece testPiece = new Piece();
        testPiece.moveTo(new Movement(0, 0));
        assertEquals(testPiece.currentMovement(), new Movement(0, 0));
    }

    Board board;

    void initializeBoard() {
        board = new Board();
        board.initializeSpace();
    }

    void insertPresetsMovements(Piece piece) {
        piece.presetMovements = new Vector<>(Arrays.asList(new Movement[]{ new Movement(0, 0) }));
    }

    void insertFactorMovements(Piece piece) {
        piece.factorMovements = new Integer[][]{ {1, 1} };
    }

    public void testGetAvailableMovements() throws Exception {
        Piece testPiece = new Piece();
        initializeBoard();

        assertEquals(testPiece.getAvailableMovements(new Movement(0, 0), board).size(), 0);

        insertPresetsMovements(testPiece);
        assertEquals(testPiece.getAvailableMovements(new Movement(0, 0), board).size(), 1);

        insertFactorMovements(testPiece);
        assertEquals(testPiece.getAvailableMovements(new Movement(0, 0), board).size(), 8);
    }

    public void testGetAvailableMovementsFromPresets() throws Exception {
        Piece testPiece = new Piece();
        initializeBoard();

        insertPresetsMovements(testPiece);
        insertFactorMovements(testPiece);

        assertEquals(testPiece.getAvailableMovementsFromPresets(new Movement(0, 0)).size(), 1);
    }

    public void testGetAvailableMovementsFromFactors() throws Exception {
        Piece testPiece = new Piece();
        initializeBoard();

        insertPresetsMovements(testPiece);
        insertFactorMovements(testPiece);

        assertEquals(testPiece.getAvailableMovementsFromFactors(new Movement(0, 0), board).size(), 7);
    }

    public void testWillOccupyWhenConflict() throws Exception {
        assertFalse(new Piece().willOccupyWhenConflict(new Piece()));
        assertTrue(new Piece().willOccupyWhenConflict(new PieceKnight(PieceColor.WHITE)));
    }

}