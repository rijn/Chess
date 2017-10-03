package chessGame.model;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class RoundTest extends TestCase {
    public void testAddListener() throws Exception {
        Round round = new Round(new Vector<Player>());
        round.addListener(c -> {});
        assertEquals(round.listener.size(), 1);
    }

    public void testCallListener() throws Exception {
        Round round = new Round(new Vector<Player>());
        round.addListener(c -> {});
        round.callListener();
    }

    List<Player> players = new Vector<Player>(Arrays.asList(new Player[]{
            new Player(PieceColor.WHITE, "TEST1", 1, new chessGame.model.Timer(3600)),
            new Player(PieceColor.BLACK, "TEST2", 2, new chessGame.model.Timer(3600))
    }));

    public void testCurrentNextAndPrev() throws Exception {
        Round round = new Round(players);
        assertTrue(round.current().id == 1);
        round.next();
        assertTrue(round.current().id == 2);
        round.next();
        assertTrue(round.current().id == 1);
        round.prev();
        assertTrue(round.current().id == 2);
    }

    public void testGetNotCurrentPlayers() throws Exception {
        Round round = new Round(players);
        assertTrue(round.getNotCurrentPlayers().size() == 1);
    }

}