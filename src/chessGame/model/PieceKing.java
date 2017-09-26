package chessGame.model;

import chessGame.util.Movement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * King class
 */
public class PieceKing extends Piece {
    /**
     * Constructor
     *
     * @param color
     */
    public PieceKing(PieceColor color) {
        this.color = color;
        name = "KING";
        shortName = color == PieceColor.BLACK ? '♔' : '♚';

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
    }

    /**
     * King has different rules for movement, that it cannot move to a "threatened" location. This function will filter
     * them out.
     *
     * @param base Base Location
     * @param board The board that wish to be inspect on
     * @return List<Movement> All possible movements
     */
    public List<Movement> getAvailableMovements(Movement base, Board board) {
        List<Movement> availableMovementsOfOtherPlayers = board.getAvailableMovementsOfCondition(piece -> piece.color != color && piece.name != "KING");
        return getAvailableMovementsFromPresets(base).stream()
                .filter(movement -> !availableMovementsOfOtherPlayers.contains(movement))
                .collect(Collectors.toList());
    }
}