package chessGame.model;

/**
 * Player class
 */
public class Player {
    /**
     * Color of current player choose
     */
    public PieceColor color = PieceColor.BLACK;

    /**
     * User name
     */
    public String name = "UNKNOWN";

    /**
     * Player Constructor
     *
     * @param color
     * @param name
     */
    public Player(PieceColor color, String name) {
        this.color = color;
        this.name = name;
    }
}
