package chessGame.controller;

import chessGame.model.Log;
import chessGame.model.PieceColor;
import chessGame.model.Player;
import chessGame.model.Round;
import chessGame.model.storage.DB;
import chessGame.util.Event;
import chessGame.view.NewGameWindow;
import chessGame.view.NewUserWindow;
import chessGame.view.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class ViewController {
    static Window window;

    public static void initialize() {
        window = new Window();

        initializeMainWindowListener();
    }

    static Log log;

    static GameController gameController;

    public static void initializeMainWindowListener() {
        window.addNewUserListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                initializeNewUserWindowListener(new NewUserWindow());
            }
        });

        window.addNewGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeNewGameWindowListener(new NewGameWindow());

            }
        });
        window.addPauseGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameController.isStarted()) {
                    gameController.pauseGame();
                } else {
                    gameController.startGame();
                }
            }
        });
        window.addForfeitGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.ariseEvent(Event.FORFEIT);
            }
        });

        window.addUndoistener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (log != null) {
                    log.undo();
                }
            }
        });
        window.addRndoistener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (log != null) {
                    log.redo();
                }
            }
        });

        window.addScordboardListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

    }

    public static void initializeNewUserWindowListener(NewUserWindow newUserWindow) {
        newUserWindow.addRegisterUserListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!DB.registerUser(newUserWindow.getUserName())) {
                    JOptionPane.showMessageDialog(null, "Username has already been used", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    newUserWindow.dispose();
                }
            }
        });
    }

    static List<Player> players;
    static chessGame.model.Board board;
    static chessGame.view.Board _board;
    static Round round;

    public static void initializeNewGameWindowListener(NewGameWindow newGameWindow) {
        newGameWindow.addNewGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer whitePlayerId = DB.getUserId(newGameWindow.getWhitePlayerName());
                Integer blackPlayerId = DB.getUserId(newGameWindow.getBlackPlayerName());
                if (whitePlayerId == 0) {
                    JOptionPane.showMessageDialog(null, "White player does not exist", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (blackPlayerId == 0) {
                    JOptionPane.showMessageDialog(null, "Black player does not exist", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                window.removeBoard();

                board = new chessGame.model.Board();

                players = new Vector<Player>(Arrays.asList(new Player[]{
                        new Player(PieceColor.WHITE, newGameWindow.getWhitePlayerName(), whitePlayerId, new chessGame.model.Timer(3600)),
                        new Player(PieceColor.BLACK, newGameWindow.getBlackPlayerName(), blackPlayerId, new chessGame.model.Timer(3600))
                }));

                log = new Log();

                log.addListener(window.undoChange);
                log.addListener(window.redoChange);

                round = new Round(players);

                _board = new chessGame.view.Board(board, round);
                window.initializeBoard(_board);

                gameController = new GameController(board, players, round, log);

                gameController.addTimerListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        window.setTitle(players.stream().map(p -> p.name + " " + p.timer.getString()).collect(Collectors.joining(" / ")));
                    }
                });

                _board.setMoveListener(gameController.moveTrigger);

                newGameWindow.dispose();
            }
        });
    }
}
