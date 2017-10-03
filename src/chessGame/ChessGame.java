package chessGame;

import chessGame.controller.ViewController;
import chessGame.model.storage.DB;

/**
 * Main static loop
 */
public class ChessGame {

    /**
     * Main loop
     * @param args
     * @throws Exception
     */
    public static void main (String[] args) throws Exception {
//        Window window = new Window();
//
//        Board board = new chessGame.model.Board();
//        board.initialize();
//
//        window.initializeBoard(board);
//
//        Player[] players = new Player[]{
//                new Player(PieceColor.WHITE, "Player 1"),
//                new Player(PieceColor.BLACK, "Player 2")
//        };
//
//        new ChessGame(board, players).newGame();
        ViewController.initialize();

        DB.connect();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                DB.disconnect();
            }
        }, "Shutdown-thread"));

        return;
    }
}
