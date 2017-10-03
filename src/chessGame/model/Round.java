package chessGame.model;

import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Round {
    List<Player> players;

    int round = 0;

    public Round(List<Player> players) {
        this.players = players;
    }

    List<Consumer<Round>> listener = new Vector<Consumer<Round>>();

    public void addListener(Consumer<Round> c) {
        listener.add(c);
    }

    public void callListener() {
        listener.stream().forEach(c -> c.accept(this));
    }

    public Player current() {
        return players.get(round);
    }

    public Player next() {
        round = (round + 1) % players.size();
        callListener();
        return players.get(round);
    }

    public Player prev() {
        round = (round - 1 + players.size()) % players.size();
        callListener();
        return players.get(round);
    }

    public List<Player> getNotCurrentPlayers() {
        return players.stream().filter(p -> current() != p).collect(Collectors.toList());
    }
}
