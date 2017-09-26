package chessGame.util;

import chessGame.util.Constant;
import chessGame.util.Movement;
import junit.framework.TestCase;

public class MovementTest extends TestCase {
    public void testClone() throws Exception {
        Movement movement = new Movement(0, 0);
        assertEquals(movement.clone(), movement);
    }

    public void testEquals() throws Exception {
        assertEquals(new Movement(0, 0), new Movement(0, 0));
    }

    public void testMove() throws Exception {
        assertEquals(new Movement(0, 0).move(1, 2), new Movement(1, 2));
    }

    public void testMoveObj() throws Exception {
        assertEquals(new Movement(0, 0).move(new Movement(1, 2)), new Movement(1, 2));
        assertEquals(new Movement(0, 0).move(null), new Movement(0, 0));
    }

    public void testValid() throws Exception {
        assertTrue(new Movement(0, 0).valid());
        assertTrue(new Movement(0, Constant.BOARD_SIZE_Y - 1).valid());
        assertTrue(new Movement(Constant.BOARD_SIZE_X - 1, 0).valid());
        assertTrue(new Movement(Constant.BOARD_SIZE_X - 1, Constant.BOARD_SIZE_Y - 1).valid());
        assertFalse(new Movement(-1, 0).valid());
        assertFalse(new Movement(0, -1).valid());
        assertFalse(new Movement(Constant.BOARD_SIZE_X, 0).valid());
        assertFalse(new Movement(0, Constant.BOARD_SIZE_X).valid());
    }

    public void testCopyConstuctor() throws Exception {
        assertEquals(new Movement(0, 0), new Movement(new Movement(0, 0)));
    }
}