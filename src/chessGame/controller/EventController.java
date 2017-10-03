package chessGame.controller;

import chessGame.model.Player;
import chessGame.model.Round;
import chessGame.model.storage.DB;
import chessGame.util.Command;
import chessGame.util.Event;

import javax.swing.*;

public class EventController implements Command {

    GameController gameController;

    Player player;

    Event event;

    public EventController(GameController gc, Player player, Event e) {
        this.gameController = gc;
        this.player = player;
        this.event = e;
    }

    @Override
    public void execute() {
        switch (this.event) {
            case FORFEIT:
                JOptionPane.showMessageDialog(null, "Player " + player.name + " forfeit the game", "Game over", JOptionPane.INFORMATION_MESSAGE);
                gameController.pauseGame();
                break;
            case CHECKMATE:
                JOptionPane.showMessageDialog(null, "Checkmate! Player " + player.name + " lost", "Game over", JOptionPane.INFORMATION_MESSAGE);
                gameController.pauseGame();
                break;
            case STALEMATE:
                JOptionPane.showMessageDialog(null, "Stalemate", "Game over", JOptionPane.INFORMATION_MESSAGE);
                gameController.pauseGame();
                break;
        }

        DB.insertEvent(gameController, this);
    }

    @Override
    public void undo() {
        JOptionPane.showMessageDialog(null, "You cannot undo an event", "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    public Player getPlayer() {
        return player;
    }

    public Event getEvent() {
        return event;
    }
}
