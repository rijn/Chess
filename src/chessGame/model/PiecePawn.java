package chessGame.model;

import chessGame.util.Movement;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Pawn has specific design for promotion
 */
public class PiecePawn extends Piece {
    InputStream systemIn;
    OutputStream systemOut;

    public PiecePawn(PieceColor color) {
        this.color = color;
        name = "PAWN";
        shortName = color == PieceColor.BLACK ? '♙' : '♟';

        /**
         * Register an afterMove event to check if it is suitable for promotion.
         */
        events.put("AFTER_MOVE", new Vector<Function<Board, Piece>>());
        events.get("AFTER_MOVE").add(eventPromotion);

        this.systemIn = System.in;
        this.systemOut = System.out;
    }

    /**
     * Promotion supply function
     * will ask for user which type of piece they wish, then return that piece which will substitute current object
     * later in Board.
     */
    public Function<Board, Piece> eventPromotion = (Board board) -> {
        if ((color == PieceColor.BLACK && currentMovement().x == 7) || currentMovement().x == 0) {
            try {
                systemOut.write("Promotion (B for Bishop, K for Knight, Q for Queen, R for Rook)".getBytes());
            } catch (Exception e) { }
            if (systemIn == null) { return null; };
            Scanner scanner = new Scanner(systemIn);
            String command = scanner.next();
            switch (command) {
                case "B":
                    return new PieceBishop(color);
                case "K":
                    return new PieceKnight(color);
                case "R":
                    return new PieceRook(color);
                case "Q":
                default:
                    return new PieceQueen(color);
            }
        }
        return null;
    };

    /**
     * Pawn has different rule when moving. Override base function.
     *
     * @param base Base Location
     * @param board The board that wish to be inspect on
     * @return List<Movement> All possible movements
     */
    public List<Movement> getAvailableMovements(Movement base, Board board) {
        List<Movement> output = new Vector<Movement>();

        // If it's the first move, pawn can move two steps, otherwise one.
        // If any piece on current line, then pawn cannot occupy them
        Movement current = new Movement(this.color == PieceColor.WHITE ? 1 : -1, 0).move(base);
        if (board.getPiece(current) == null) {
            output.add((Movement) current.clone());
            if (this.moveLog.size() == 1) {
                current.move(new Movement(this.color == PieceColor.WHITE ? 1 : -1, 0));
                if (board.getPiece(current) == null) {
                    output.add((Movement) current.clone());
                }
            }
        }

        // If pawn can eat other pieces
        if (board.getPiece(new Movement(this.color == PieceColor.WHITE ? 1 : -1, -1).move(base)) != null) {
            output.add(new Movement(this.color == PieceColor.WHITE ? 1 : -1, -1).move(base));
        }
        if (board.getPiece(new Movement(this.color == PieceColor.WHITE ? 1 : -1, 1).move(base)) != null) {
            output.add(new Movement(this.color == PieceColor.WHITE ? 1 : -1, 1).move(base));
        }

        return output.stream()
                .filter(Movement::valid)
                .collect(Collectors.toList());
    }

    // TODO: override willOccupy function here. Pawn cannot capture piece on the line.
}