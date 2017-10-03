package chessGame.model;

import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Round wrapping
 */
public class Round {
    /**
     * List of players
     */
    List<Player> players;

    /**
     * Current round
     */
    int round = 0;

    /**
     * Constructor
     * @param players
     */
    public Round(List<Player> players) {
        this.players = players;
    }

    /**
     * Event listener
     */
    List<Consumer<Round>> listener = new Vector<Consumer<Round>>();

    /**
     * Add event listener
     * @param c
     */
    public void addListener(Consumer<Round> c) {
        listener.add(c);
    }

    /**
     * Consume event listener
     */
    public void callListener() {
        listener.stream().forEach(c -> c.accept(this));
    }

    /**
     * Return current player
     * @return
     */
    public Player current() {
        return players.get(round);
    }

    /**
     * Change to next player
     * @return
     */
    public Player next() {
        round = (round + 1) % players.size();
        callListener();
        return players.get(round);
    }

    /**
     * Undo to previous player
     * @return
     */
    public Player prev() {
        round = (round - 1 + players.size()) % players.size();
        callListener();
        return players.get(round);
    }

    /**
     * Return remaining player
     * @return
     */
    public List<Player> getNotCurrentPlayers() {
        return players.stream().filter(p -> current() != p).collect(Collectors.toList());
    }
}
