package chessGame.controller;

import chessGame.model.Piece;
import chessGame.model.Player;
import chessGame.model.Round;
import chessGame.model.storage.DB;
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

    GameController gameController;

    Long id;

    public MoveController(GameController gc, Board board, Movement from, Movement to, Round round) {
        this.board = board;
        this.from = from;
        this.to = to;
        this.round = round;
        this.player = round.current();
        this.gameController = gc;
    }

    public void execute() {
        source = board.getPiece(from);
        target = board.getPiece(to);
        board.movePiece(from, to);

        id = DB.insertMove(gameController, this);
    }

    public void undo() {
        board.movePiece(to, from);
        if (target != null) {
            board.insertPiece(target, to);
            board.callEvent(target, "BE_RELEASED");
        }
        round.prev();

        DB.removeMove(id);
        id = null;
    }

    public Player getPlayer() {
        return player;
    }

    public Piece getSource() {
        return source;
    }

    public Movement getFrom() {
        return from;
    }

    public Movement getTo() {
        return to;
    }
}
