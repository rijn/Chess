package chessGame.controller;

import chessGame.model.Board;
import chessGame.model.Log;
import chessGame.model.Player;
import chessGame.model.Round;
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

    Round round;

    Log log;

    public BiConsumer<Movement, Movement> moveTrigger = (from, to) -> move(from, to);

    Timer timer;

    public void addTimerListener(ActionListener a) {
        timer.addActionListener(a);
    }


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

        startGame();
    }

    public Boolean isStarted() {
        return round.current().timer.isEnabled();
    }

    public void startGame() {
        round.current().timer.enable();
        timer.start();
    }

    public void pauseGame() {
        round.current().timer.disable();
        timer.stop();
    }

    public void forfeitGame() {
        EventController e = new EventController(this, round.current(), Event.FORFEIT);
        log.record(e);
        e.execute();
    }

    public void move(Movement from, Movement to) {
        try {
            // Test if game is over
            if (isGameOver(players, board)) {
                systemOut.write("Game over.\r\n".getBytes());
                return;
            }

            // Test if game is in stalemate
            if (isInStalemate(board)) {
                systemOut.write("Stalemate! Game over.\r\n".getBytes());
                return;
            }


            // Test if user is in checkmate. If true, player can only move the King
            boolean isInCheckmate = isInCheck(round.current(), board);
//            if (isInCheckmate) {
//                systemOut.write("You're in checkmake.\r\n".getBytes());
//                coord = board.getPiece("KING", round.current().color).currentMovement();
//            } else {
//                // Prompt user to type in the location of the piece that he wish to move
//                do {
//                    systemOut.write("Input the coord of the piece that you wish to move (e.g. a1) : ".getBytes());
//                    String command = scanner.next();
//                    coord = new Movement(command.charAt(1) - 49, command.charAt(0) - 97);
//                } while (board.getPiece(coord) == null || board.getPiece(coord).color != round.current().color);
//            }
            systemOut.write(("The piece is " + board.getPiece(from).name + "\r\n").getBytes());

            // Get all available movements of that piece
            // TODO: update printBoardWithAvailableMovements here, pass in movements other than location
            List<Movement> availableMovements;
            availableMovements = board.getAvailableMovementsOfPiece(from);
            if (availableMovements.size() > 0) {
                // If there are possible movements
            } else {
                // If user can do nothing with the piece
                if (isInCheckmate) {
                    // systemOut.write("Game over.\r\n".getBytes());
                    return;
                } else {
                    // systemOut.write("You cannot move this piece.\r\n".getBytes());
                }
            }

            MoveController move = new MoveController(board, from, to, round);
            move.execute();
            log.record(move);

            // Switch to next round
            round.next();
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
     * Return true if specific player is in checkmake.
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
