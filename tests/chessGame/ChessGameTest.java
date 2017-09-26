package chessGame;

import chessGame.model.*;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.mockito.Mockito.*;

public class ChessGameTest extends TestCase {
    public void testMainLoop() throws Exception {
        InputStream mockInStream = new ByteArrayInputStream(new String("d2\r\nd4\r\ne7\r\ne5\r\nd1\r\nd2\r\ne5\r\nd4\r\nd2\r\nc3\r\nd4\r\nc3\r\n").getBytes());
        OutputStream mockOutStream = new ByteArrayOutputStream();

        Player[] players = new Player[]{
                new Player(PieceColor.WHITE, "Player 1"),
                new Player(PieceColor.BLACK, "Player 2")
        };

        Board board = new Board();
        board.initializeSpace();

        ChessGame game = new ChessGame(board, players);

        game.systemIn = mockInStream;
        game.systemOut = mockOutStream;

        game.newGame();
    }

    public void testIsInCheck() throws Exception {
        Board board = new Board();
        board.initializeSpace();

        Player[] players = new Player[]{ new Player(PieceColor.BLACK, "TEST") };

        ChessGame game = new ChessGame(board, players);

        board.insertPiece(new PieceKing(PieceColor.BLACK), 0, 0);
        board.insertPiece(new PiecePawn(PieceColor.WHITE), 1, 1);

        assertTrue(game.isInCheck(players[0], board));
    }

    public void testIsInStalemate() throws Exception {
        Board board = new Board();
        board.initializeSpace();

        ChessGame game = new ChessGame(board, new Player[]{});

        board.insertPiece(new PiecePawn(PieceColor.BLACK), 7, 0);
        board.insertPiece(new PiecePawn(PieceColor.WHITE), 0, 0);

        assertTrue(game.isInStalemate(board));
    }
}