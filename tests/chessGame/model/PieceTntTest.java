package chessGame.model;

import junit.framework.TestCase;

public class PieceTntTest extends TestCase {
    public void testExplode() throws Exception {
        Board board = new Board();
        board.initializeSpace();

        PieceTnt tnt = new PieceTnt(PieceColor.BLACK);

        board.insertPiece(new Piece(), 1, 1);
        board.insertPiece(tnt, 2, 2);

        tnt.explode.apply(board);

        assertEquals(board.getPieces().size(), 1);
        assertNull(board.getPiece(1, 1));
    }

}