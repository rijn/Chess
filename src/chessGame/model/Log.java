package chessGame.model;

import chessGame.util.Command;

import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

public class Log {
    List<Command> commands = new Vector<Command>();

    Integer current = 0;

    List<Consumer<Log>> listener = new Vector<Consumer<Log>>();

    public void addListener(Consumer<Log> c) {
        listener.add(c);
    }

    public void callListener() {
        listener.stream().forEach(c -> c.accept(this));
    }

    public void record(Command c) {
        while (commands.size() > current) commands.remove(commands.size() - 1);
        commands.add(c);
        current++;
        callListener();
    }

    public Boolean canUndo() {
        return current > 0;
    }

    public void undo() {
        if (!canUndo()) return;
        current--;
        commands.get(current).undo();
        callListener();
    }

    public Boolean canRedo() {
        return current < commands.size();
    }

    public void redo() {
        if (!canRedo()) return;
        commands.get(current).execute();
        current++;
        callListener();
    }


}
