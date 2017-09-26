package chessGame.model;

import chessGame.model.Board;
import chessGame.model.Piece;
import chessGame.model.PieceColor;
import chessGame.model.PiecePawn;
import chessGame.util.Movement;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;
import java.util.function.Function;

public class BoardTest extends TestCase {
    public void testInitialize() throws Exception {
        Board board = new Board();
        board.initialize();
        assertNotNull(board.board[0]);
        assertTrue(board.getPiece(0, 0) instanceof Piece);
    }

    public void testInitializeSpace() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        assertNotNull(board.board[0]);
    }

    public void testGetPiece() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        Piece piece = new Piece();
        board.insertPiece(piece, 0, 0);
        assertEquals(piece, board.getPiece(0, 0));
    }

    public void testGetPiece1() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        Piece piece = new Piece();
        board.insertPiece(piece, 0, 0);
        assertEquals(piece, board.getPiece(new Movement(0, 0)));
    }

    public void testGetPiece2() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        Piece piece = new Piece();
        board.insertPiece(piece, 0, 0);
        assertEquals(piece, board.getPiece("UNKNOWN", PieceColor.BLACK));
    }

    public void testGetPieceNull() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        Piece piece = new Piece();
        board.insertPiece(piece, 0, 0);
        assertNull(board.getPiece("UNKNOWN1", PieceColor.BLACK));
    }

    public void testInsertPiece() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        Piece piece = new Piece();
        board.insertPiece(piece, 0, 0);
        assertEquals(piece, board.getPiece(0, 0));
    }

    public void testinitializePieces() throws Exception {
        Board board = new Board();
        board.initialize();
        board.initializePieces();
        for (int x = 0; x < 2; x ++) {
            for (int y = 0; y < 8; y ++) {
                assertNotNull(board.getPiece(x, y));
            }
        }
        for (int x = 6; x < 8; x ++) {
            for (int y = 0; y < 8; y ++) {
                assertNotNull(board.getPiece(x, y));
            }
        }
    }

    public void testGetAvailableMovementsOfCondition() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        board.insertPiece(new PiecePawn(PieceColor.BLACK), 0, 0);
        board.insertPiece(new PiecePawn(PieceColor.WHITE), 7, 0);

        assertEquals(board.getAvailableMovementsOfCondition(null).size(), 4);
        assertEquals(board.getAvailableMovementsOfCondition(piece -> piece.color == PieceColor.BLACK).size(), 2);
        assertEquals(board.getAvailableMovementsOfCondition(piece -> piece.color == PieceColor.WHITE).size(), 2);
    }

    public void testGetAvailableMovementsOfPiece() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        board.insertPiece(new PiecePawn(PieceColor.BLACK), 0, 0);
        board.insertPiece(new PiecePawn(PieceColor.WHITE), 7, 0);

        assertEquals(board.getAvailableMovementsOfPiece(new Movement(0, 0)).size(), 2);
        assertEquals(board.getAvailableMovementsOfPiece(new Movement(0, 1)).size(), 0);
    }

    public void testCallEvent() throws Exception {
        Board board = new Board();
        board.initialize();

        board.getPiece(0, 0).events.put("TEST", new Vector<Function<Board, Piece>>());
        board.getPiece(0, 0).events.get("TEST").add((Board _board) -> new Piece());

        board.callEvent(board.getPiece(0, 0), "TEST");

        assertEquals(board.getPiece(0, 0).name, "UNKNOWN");
    }

    public void testMovePiece() throws Exception {
    }

    public void testGenerateBoard() throws Exception {
    }

    public void testPrintBoard() throws Exception {
        Board board = new Board();
        board.initialize();

        OutputStream mockStream = new ByteArrayOutputStream();

        board.printBoard(mockStream);

        assertEquals(mockStream.toString().length(), 162);
    }

    public void testPrintBoardWithAvailableMovements() throws Exception {
        Board board = new Board();
        board.initialize();

        OutputStream mockStream = new ByteArrayOutputStream();

        board.printBoardWithAvailableMovements(new Movement(1, 0), mockStream);

        assertEquals(mockStream.toString().length(), 162);
    }

    public void testGetAllPieces() throws Exception {
        Board board = new Board();
        board.initializeSpace();

        board.insertPiece(new Piece(), 0, 0);

        List<Piece> pieces = board.getPieces();

        assertEquals(pieces.size(), 1);
        assertEquals(pieces.get(0).name, "UNKNOWN");
    }

}