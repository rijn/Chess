package chessGame;

/**
 * Movement or coordinate class
 */
public class Movement implements Cloneable {
    /**
     * Coordinate x
     */
    public int x = 0;

    /**
     * Coordinate y
     */
    public int y = 0;

    public Movement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Implements Cloneable so Movement can be cloned.
     */
    @Override
    public Object clone() {
        Movement cloneObject = null;
        try {
            cloneObject = (Movement) super.clone();
        } catch (CloneNotSupportedException e) { }
        return cloneObject;
    }

    /**
     * Implements equals so two different Movement objects can be compared.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Movement))return false;
        Movement otherMovement = (Movement)other;
        return (this.x == otherMovement.x && this.y == otherMovement.y);
    }

    /**
     * Add a step to this movement
     *
     * @param dx Differential in axis x
     * @param dy Differential in axis y
     * @return Movement Move current one and return
     */
    public Movement move(int dx, int dy) {
        x = x + dx;
        y = y + dy;
        return this;
    }

    /**
     * Add another movement to current movement
     *
     * @param d Differential movement
     * @return Movement Move current one and return
     */
    public Movement move(Movement d) {
        if (d == null) return this;
        x = x + d.x;
        y = y + d.y;
        return this;
    }

    /**
     * Checkout if current Movement is in board's valid size.
     *
     * @return boolean True if current movement is in the board
     */
    public boolean valid() {
        return x >= 0 && x < Constant.BOARD_SIZE_X && y >= 0 && y < Constant.BOARD_SIZE_Y;
    }
}
