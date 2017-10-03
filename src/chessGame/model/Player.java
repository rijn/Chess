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
     * User ID
     */
    public Integer id;

    /**
     * User's timer
     */
    public Timer timer;

    /**
     * Player Constructor
     *
     * @param color
     * @param name
     */
    public Player(PieceColor color, String name, Integer id, Timer timer) {
        this.color = color;
        this.name = name;
        this.id = id;
        this.timer = timer;
    }
}
