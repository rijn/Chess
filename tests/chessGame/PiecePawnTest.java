package chessGame;

import junit.framework.TestCase;

import java.io.*;

public class PiecePawnTest extends TestCase {
    public void testGetAvailableMovements() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        board.insertPiece(new PiecePawn(PieceColor.BLACK), 0, 0);
        assertEquals(board.getPiece(0, 0).getAvailableMovements(new Movement(0, 0), board).size(), 2);

        board.movePiece(new Movement(0, 0), new Movement(1, 0));
        assertEquals(board.getPiece(1, 0).getAvailableMovements(new Movement(1, 0), board).size(), 1);
    }

    public void testIfPawnCanOccupyOtherPieces() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        board.insertPiece(new PiecePawn(PieceColor.BLACK), 0, 1);
        board.movePiece(new Movement(0, 1), new Movement(1, 1));
        board.insertPiece(new PiecePawn(PieceColor.WHITE), 2, 0);
        board.insertPiece(new PiecePawn(PieceColor.WHITE), 2, 2);
        assertEquals(board.getPiece(1, 1).getAvailableMovements(new Movement(1, 1), board).size(), 3);
    }

    public void testPawnPromotionBishop() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        PiecePawn piece = new PiecePawn(PieceColor.BLACK);
        piece.systemIn = new ByteArrayInputStream(new String("B\r\n").getBytes());;
        board.insertPiece(piece, 0, 6);
        board.movePiece(new Movement(0, 6), new Movement(0, 7));
        assertTrue(board.getPiece(0, 7) instanceof PieceBishop);
    }

    public void testPawnPromotionKnight() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        PiecePawn piece = new PiecePawn(PieceColor.BLACK);
        piece.systemIn = new ByteArrayInputStream(new String("K\r\n").getBytes());;
        board.insertPiece(piece, 0, 6);
        board.movePiece(new Movement(0, 6), new Movement(0, 7));
        assertTrue(board.getPiece(0, 7) instanceof PieceKnight);
    }

    public void testPawnPromotionQueen() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        PiecePawn piece = new PiecePawn(PieceColor.BLACK);
        piece.systemIn = new ByteArrayInputStream(new String("Q\r\n").getBytes());;
        board.insertPiece(piece, 0, 6);
        board.movePiece(new Movement(0, 6), new Movement(0, 7));
        assertTrue(board.getPiece(0, 7) instanceof PieceQueen);
    }

    public void testPawnPromotionRook() throws Exception {
        Board board = new Board();
        board.initializeSpace();
        PiecePawn piece = new PiecePawn(PieceColor.BLACK);
        piece.systemIn = new ByteArrayInputStream(new String("R\r\n").getBytes());;
        board.insertPiece(piece, 0, 6);
        board.movePiece(new Movement(0, 6), new Movement(0, 7));
        assertTrue(board.getPiece(0, 7) instanceof PieceRook);
    }

}