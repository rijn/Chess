package chessGame.model;

import chessGame.util.Movement;

import java.util.Arrays;
import java.util.Vector;
import java.util.function.Function;

/**
 * Piece TNT
 */
public class PieceTnt extends Piece {

    /**
     * TNT constructor
     *
     * @param color
     */
    PieceTnt(PieceColor color) {
        this.color = color;
        name = "TNT";
        shortName = color == PieceColor.BLACK ? 'T' : 'T';

        presetMovements = Arrays.asList(new Movement[]{
                new Movement(-1, -1),
                new Movement(-1, 0),
                new Movement(-1, 1),
                new Movement(0, 1),
                new Movement(1, 1),
                new Movement(1, 0),
                new Movement(1, -1),
                new Movement(0, -1)
        });

        events.put("BE_CAPTURED", new Vector<Function<Board, Piece>>());
        events.get("BE_CAPTURED").add(explode);
    }

    /**
     * Explode
     */
    public Function<Board, Piece> explode = (Board board) -> {
        this.presetMovements.stream()
                .forEach(m -> {
                    board.removePiece(new Movement(this.currentMovement()).move(m));
                });
        return null;
    };
}