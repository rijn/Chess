package chessGame.model;

import chessGame.util.Command;

import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

/**
 * Log wrapping
 */
public class Log {
    /**
     * List of commands need to be saved
     */
    List<Command> commands = new Vector<Command>();

    /**
     * Current idx of command
     */
    Integer current = 0;

    /**
     * Event listener
     */
    List<Consumer<Log>> listener = new Vector<Consumer<Log>>();

    /**
     * Add listener
     * @param c
     */
    public void addListener(Consumer<Log> c) {
        listener.add(c);
    }

    /**
     * Consume listener
     */
    public void callListener() {
        listener.stream().forEach(c -> c.accept(this));
    }

    /**
     * Push a new command into the list
     * @param c
     */
    public void record(Command c) {
        while (commands.size() > current) commands.remove(commands.size() - 1);
        commands.add(c);
        current++;
        callListener();
    }

    /**
     * Return true if we can undo in this stack
     * @return
     */
    public Boolean canUndo() {
        return current > 0;
    }

    /**
     * Undo it
     */
    public void undo() {
        if (!canUndo()) return;
        current--;
        commands.get(current).undo();
        callListener();
    }

    /**
     * Return true if we can redo in this stack
     * @return
     */
    public Boolean canRedo() {
        return current < commands.size();
    }

    /**
     * Redo it
     */
    public void redo() {
        if (!canRedo()) return;
        commands.get(current).execute();
        current++;
        callListener();
    }
}
