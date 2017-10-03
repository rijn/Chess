package chessGame.model;

public class Round {
    Player[] players;

    int round = 0;

    public Round(Player[] players) {
        this.players = players;
    }

    public Player current() {
        return players[round];
    }

    public Player next() {
        round = (round + 1) % players.length;
        return players[round];
    }
}
