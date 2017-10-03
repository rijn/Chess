package chessGame.controller;

import chessGame.model.PieceColor;
import chessGame.model.Player;
import chessGame.model.Round;
import chessGame.model.storage.DB;
import chessGame.view.NewGameWindow;
import chessGame.view.NewUserWindow;
import chessGame.view.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewController {
    static Window window;

    public static void initialize() {
        window = new Window();

        initializeMainWindowListener();
    }

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
                // TODO
            }
        });
        window.addForfeitGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

        window.addUndoistener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        window.addRndoistener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
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

                chessGame.model.Board board = new chessGame.model.Board();

                Player[] players = new Player[]{
                    new Player(PieceColor.WHITE, newGameWindow.getWhitePlayerName(), whitePlayerId),
                    new Player(PieceColor.BLACK, newGameWindow.getBlackPlayerName(), blackPlayerId)
                };

                Round round = new Round(players);

                chessGame.view.Board _board = new chessGame.view.Board(board, round);
                window.initializeBoard(_board);

                GameController gameController = new GameController(board, players, round);

                _board.setMoveListener(gameController.moveTrigger);

                newGameWindow.dispose();
            }
        });
    }
}
