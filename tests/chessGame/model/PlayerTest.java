package chessGame.model;

import chessGame.model.PieceColor;
import chessGame.model.Player;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {
    public void testConstructor() throws Exception {
        Player player = new Player(PieceColor.BLACK, "TEST", 0, new Timer(0));
        assertEquals(player.color, PieceColor.BLACK);
        assertEquals(player.name, "TEST");
    }
}