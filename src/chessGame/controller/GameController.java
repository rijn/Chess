package chessGame.controller;

import chessGame.model.Board;
import chessGame.model.Log;
import chessGame.model.Player;
import chessGame.model.Round;
import chessGame.model.storage.DB;
import chessGame.util.Event;
import chessGame.util.Movement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * Game controller for game logic
 */
public class GameController {
    /**
     * In stream
     */
    static InputStream systemIn = System.in;

    /**
     * out stream
     */
    static OutputStream systemOut = System.out;

    /**
     * Board
     */
    Board board;

    /**
     * Players
     */
    List<Player> players;

    /**
     * Round reference
     */
    Round round;

    /**
     * Log reference
     */
    Log log;

    /**
     * Move trigger for check statements
     */
    public BiConsumer<Movement, Movement> moveTrigger = (from, to) -> move(from, to);

    /**
     * Timer for refreshing the remaining time
     */
    Timer timer;

    /**
     * Bind event listener to the timer
     * @param a
     */
    public void addTimerListener(ActionListener a) {
        timer.addActionListener(a);
    }

    /**
     * game_id
     */
    Long id;

    /**
     * get game_id
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Constructor
     * @param board
     * @param players
     * @param round
     * @param log
     */
    public GameController(Board board, List<Player> players, Round round, Log log) {
        this.board = board;
        this.players = players;
        this.round = round;
        this.log = log;

        this.timer = new Timer(1000, null);
        timer.setRepeats(true);

        round.addListener(r -> {
            r.current().timer.enable();
            r.getNotCurrentPlayers().stream().forEach(p -> p.timer.disable());
        });

        id = DB.newGame(players);

        startGame();
    }

    /**
     * check if timer has been started
     * @return
     */
    public Boolean isStarted() {
        return round.current().timer.isEnabled();
    }

    /**
     * start the timer
     */
    public void startGame() {
        round.current().timer.enable();
        timer.start();
    }

    /**
     * stop the timer
     */
    public void pauseGame() {
        round.current().timer.disable();
        timer.stop();
    }

    /**
     * Record important event
     * @param event
     */
    public void ariseEvent(Event event) {
        EventController e = new EventController(this, round.current(), event);
        log.record(e);
        e.execute();
    }

    /**
     * Main game loop and logic
     *
     * @param from
     * @param to
     */
    public void move(Movement from, Movement to) {
        try {
            // Test if current player is in check
            if (isInCheck(round.current(), board) && board.getPiece(from).name != "KING") {
                JOptionPane.showMessageDialog(null, "Illegal movement", "In check", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            MoveController move = new MoveController(this, board, from, to, round);
            move.execute();
            log.record(move);

            // Switch to next round
            round.next();

            // Test if game is over
            if (isInCheckmate(round.current(), board)) {
                ariseEvent(Event.CHECKMATE);
                return;
            }

            if (isInStalemate(board)) {
                ariseEvent(Event.STALEMATE);
                return;
            }
        } catch ( Exception e ) { }
    }

    /**
     * Return true if one side does not have King.
     *
     * @param players Current players
     * @param board Current board
     * @return boolean True if one side does not have King.
     */
    public boolean isGameOver(List<Player> players, chessGame.model.Board board) {
        return players.stream()
                .anyMatch(player -> board.getPiece("KING", player.color) == null);
    }

    /**
     * Return true if specific player is in check.
     *
     * @param currentPlayer Player that wish to be inspected
     * @param board Current board
     * @return boolean True if he is in checkmate
     */
    public boolean isInCheck(Player currentPlayer, chessGame.model.Board board) {
        return board.getPiece("KING", currentPlayer.color) != null &&
                board.getAvailableMovementsOfCondition(piece -> piece.color != currentPlayer.color)
                        .contains(board.getPiece("KING", currentPlayer.color).currentMovement());
    }

    /**
     * Return true if current player is in checkmate.
     *
     * @param currentPlayer
     * @param board
     * @return
     */
    public boolean isInCheckmate(Player currentPlayer, chessGame.model.Board board) {
        return isInCheck(currentPlayer, board) &&
                board.getPiece("KING", currentPlayer.color) != null &&
                board.getAvailableMovementsOfCondition(piece -> piece.color != currentPlayer.color)
                        .stream().allMatch(m -> board.getAvailableMovementsOfCondition(piece -> piece.color != currentPlayer.color).contains(m));
    }

    /**
     * Return true if there's the stalemate situation.
     *
     * @param board Current board
     * @return boolean True if is in stalemate
     */
    public boolean isInStalemate(Board board) {
        return board.getAvailableMovementsOfCondition(null).isEmpty();
    }

}
