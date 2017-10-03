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
