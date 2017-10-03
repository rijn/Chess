package chessGame.model;

import junit.framework.TestCase;

public class TimerTest extends TestCase {
    public void testIsEnabled() throws Exception {
        Timer timer = new Timer(1000);
        assertFalse(timer.isEnabled());
    }

    public void testEnable() throws Exception {
        Timer timer = new Timer(1000);
        timer.enable();
        assertTrue(timer.isEnabled());
    }

    public void testDisable() throws Exception {
        Timer timer = new Timer(1000);
        timer.disable();
        assertFalse(timer.isEnabled());
    }

    public void testGetString() throws Exception {
        Timer timer = new Timer(1000);
        assertEquals(timer.getString(), "16:40");
    }

}