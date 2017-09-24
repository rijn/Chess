package chessGame;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ChessGameTest extends TestCase {
    public void testMain() throws Exception {
        InputStream mockInStream = new ByteArrayInputStream(new String("d2\r\nd4\r\ne7\r\ne5\r\nd1\r\nd2\r\ne5\r\nd4\r\nd2\r\nc3\r\nd4\r\nc3\r\n").getBytes());
        OutputStream mockOutStream = new ByteArrayOutputStream();
        ChessGame game = new ChessGame();
        game.systemIn = mockInStream;
        game.systemOut = mockOutStream;

        game.main(new String[]{});
    }

    public void testIsInCheckmate() throws Exception {
        ChessGame game = new ChessGame();
        Player player = new Player(PieceColor.BLACK, "TEST");
        Board board = new Board();
        board.initializeSpace();

        board.insertPiece(new PieceKing(PieceColor.BLACK), 0, 0);
        board.insertPiece(new PiecePawn(PieceColor.WHITE), 1, 1);

        assertTrue(game.isInCheckmate(player, board));
    }

    public void testIsInStalemate() throws Exception {
        ChessGame game = new ChessGame();
        Board board = new Board();
        board.initializeSpace();

        board.insertPiece(new PiecePawn(PieceColor.BLACK), 7, 0);
        board.insertPiece(new PiecePawn(PieceColor.WHITE), 0, 0);

        assertTrue(game.isInStalemate(board));
    }

}