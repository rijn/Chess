package chessGame.model;

import junit.framework.TestCase;

public class PieceZebraTest extends TestCase {

    public void testContructor() throws Exception {
        Piece zebra = new PieceZebra(PieceColor.BLACK);
        assertEquals(zebra.name, "ZEBRA");
    }
}