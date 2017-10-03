package chessGame.model;

import chessGame.util.Command;
import com.sun.org.apache.xpath.internal.operations.Bool;
import junit.framework.TestCase;

public class LogTest extends TestCase {
    public void testAddListener() throws Exception {
        Log log = new Log();
        log.addListener(c -> {});
        assertEquals(log.listener.size(), 1);
    }

    public void testCallListener() throws Exception {
        Log log = new Log();
        log.addListener(c -> { c.current = 1; });
        log.callListener();
        assertTrue(log.canUndo());
    }

    public void testRecord() throws Exception {
        Log log = new Log();
        log.record(new Command() {
            @Override
            public void execute() {
            }
            @Override
            public void undo() {
            }
        });

        assertEquals(log.commands.size(), 1);
    }

    public void testUndo() throws Exception {
        Log log = new Log();
        log.record(new Command() {
            @Override
            public void execute() {
            }
            @Override
            public void undo() {
            }
        });
        assertTrue(log.canUndo());

        log.undo();

        assertFalse(log.canUndo());
    }

    public void testRedo() throws Exception {
        Log log = new Log();
        log.record(new Command() {
            @Override
            public void execute() {
            }
            @Override
            public void undo() {
            }
        });
        assertFalse(log.canRedo());

        log.undo();

        assertTrue(log.canRedo());

        log.redo();

        assertFalse(log.canRedo());
    }

}