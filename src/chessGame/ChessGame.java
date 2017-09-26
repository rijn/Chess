package chessGame;

import java.io.*;
import java.util.*;

import chessGame.model.Board;
import chessGame.model.PieceColor;
import chessGame.model.Player;
import chessGame.util.Movement;
import chessGame.view.*;

/**
 * Main static loop
 */
public class ChessGame {
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
    Player[] players;

    /**
     * Start a new chess game
     *
     * @param board
     * @param players
     * @throws Exception
     */
    public ChessGame(Board board, Player[] players) throws Exception {
        this.board = board;
        this.players = players;
    }

    /**
     * Start a new game by initialize the board as well as the players.
     */
    public void newGame () throws Exception {
        int round = 0;
        Scanner scanner = new Scanner(systemIn);

        // Main game loop
        while (true) {
            systemOut.write((players[round].name + "'s round.\r\n").getBytes());

            Movement coord = new Movement(0, 0), newCoord = new Movement(0, 0);
            List<Movement> availableMovements;
            do {
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

                // Print board for user
                board.printBoard(systemOut);

                // Test if user is in checkmate. If true, player can only move the King
                boolean isInCheckmate = isInCheck(players[round], board);
                if (isInCheckmate) {
                    systemOut.write("You're in checkmake.\r\n".getBytes());
                    coord = board.getPiece("KING", players[round].color).currentMovement();
                } else {
                    // Prompt user to type in the location of the piece that he wish to move
                    do {
                        systemOut.write("Input the coord of the piece that you wish to move (e.g. a1) : ".getBytes());
                        String command = scanner.next();
                        coord = new Movement(command.charAt(1) - 49, command.charAt(0) - 97);
                    } while (board.getPiece(coord) == null || board.getPiece(coord).color != players[round].color);
                }
                systemOut.write(("The piece is " + board.getPiece(coord).name + "\r\n").getBytes());

                // Get all available movements of that piece
                // TODO: update printBoardWithAvailableMovements here, pass in movements other than location
                board.printBoardWithAvailableMovements(coord, systemOut);
                availableMovements = board.getAvailableMovementsOfPiece(coord);
                if (availableMovements.size() > 0) {
                    // If there are possible movements
                    do {
                        systemOut.write("Input the coord of the space that you wish to move to (e.g. a1) :  ".getBytes());
                        String command = scanner.next();
                        newCoord = new Movement(command.charAt(1) - 49, command.charAt(0) - 97);
                    } while (!availableMovements.contains(newCoord));
                } else {
                    // If user can do nothing with the piece
                    if (isInCheckmate) {
                        systemOut.write("Game over.\r\n".getBytes());
                        return;
                    } else {
                        systemOut.write("You cannot move this piece.\r\n".getBytes());
                    }
                }
            } while (!availableMovements.contains(newCoord));

            board.movePiece(coord, newCoord);

            // Switch to next round
            round = (round + 1) % players.length;
        }
    }

    /**
     * Return true if one side does not have King.
     *
     * @param players Current players
     * @param board Current board
     * @return boolean True if one side does not have King.
     */
    public boolean isGameOver(Player[] players, chessGame.model.Board board) {
        return Arrays.asList(players).stream()
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

    /**
     * Return true if there's the stalemate situation.
     *
     * @param board Current board
     * @return boolean True if is in stalemate
     */
    public boolean isInStalemate(Board board) {
        return board.getAvailableMovementsOfCondition(null).isEmpty();
    }

    /**
     * Main loop
     * @param args
     * @throws Exception
     */
    public static void main (String[] args) throws Exception {
        Window window = new Window();

        Board board = new chessGame.model.Board();
        board.initialize();

        window.initializeBoard(board);

        Player[] players = new Player[]{
                new Player(PieceColor.WHITE, "Player 1"),
                new Player(PieceColor.BLACK, "Player 2")
        };

        new ChessGame(board, players).newGame();

        return;
    }
}
