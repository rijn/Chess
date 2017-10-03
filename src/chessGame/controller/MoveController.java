package chessGame.controller;

import chessGame.model.Piece;
import chessGame.model.Player;
import chessGame.model.Round;
import chessGame.model.storage.DB;
import chessGame.util.Command;
import chessGame.util.Movement;
import chessGame.model.Board;

/**
 * Movement controller. Derived from command
 */
public class MoveController implements Command {

    /**
     * board reference
     */
    Board board;

    /**
     * current move
     */
    Movement from;
    Movement to;

    /**
     * holding the pieces for undo
     */
    Piece source;
    Piece target = null;

    /**
     * restore the round
     */
    Round round;

    /**
     * holding player for record
     */
    Player player;

    /**
     * hold gc for game_id
     */
    GameController gameController;

    /**
     * movement id
     */
    Long id;

    /**
     * Constructor. Save everything
     * @param gc
     * @param board
     * @param from
     * @param to
     * @param round
     */
    public MoveController(GameController gc, Board board, Movement from, Movement to, Round round) {
        this.board = board;
        this.from = from;
        this.to = to;
        this.round = round;
        this.player = round.current();
        this.gameController = gc;
    }

    /**
     * Execute current move, i.e. move pieces and insert record into the db
     */
    public void execute() {
        source = board.getPiece(from);
        target = board.getPiece(to);
        board.movePiece(from, to);

        id = DB.insertMove(gameController, this);
    }

    /**
     * Undo current command, move pieces back to its position and delete record from the db
     */
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
