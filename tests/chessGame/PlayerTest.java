package chessGame;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {
    public void testConstructor() throws Exception {
        Player player = new Player(PieceColor.BLACK, "TEST");
        assertEquals(player.color, PieceColor.BLACK);
        assertEquals(player.name, "TEST");
    }
}