package chessGame.controller;

import chessGame.model.Piece;
import chessGame.model.Player;
import chessGame.model.Round;
import chessGame.util.Command;
import chessGame.util.Movement;
import chessGame.model.Board;

public class MoveController implements Command {

    Board board;

    Movement from;
    Movement to;

    Piece source;
    Piece target = null;

    Round round;

    Player player;

    public MoveController(Board board, Movement from, Movement to, Round round) {
        this.board = board;
        this.from = from;
        this.to = to;
        this.round = round;
        this.player = round.current();
    }

    public void execute() {
        source = board.getPiece(from);
        target = board.getPiece(to);
        board.movePiece(from, to);
    }

    public void undo() {
        board.movePiece(to, from);
        if (target != null) {
            board.insertPiece(target, to);
            board.callEvent(target, "BE_RELEASED");
        }
        round.prev();
    }
}
