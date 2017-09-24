package chessGame;

/**
 * Player class
 */
public class Player {
    /**
     * Color of current player choose
     */
    PieceColor color = PieceColor.BLACK;

    /**
     * User name
     */
    String name = "UNKNOWN";

    Player(PieceColor color, String name) {
        this.color = color;
        this.name = name;
    }
}
