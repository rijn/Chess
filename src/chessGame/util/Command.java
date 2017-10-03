package chessGame.util;

/**
 * Command abstract interface
 */
public interface Command {
    /**
     * Execute method
     */
    public void execute();

    /**
     * Undo method
     */
    public void undo();
}
