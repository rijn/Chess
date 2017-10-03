package chessGame.model;

import chessGame.util.Constant;
import chessGame.util.Movement;

import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Chess board class
 */
public class Board {
    /**
     * Single slot for board.
     */
    public class Space {
        /**
         * Current piece
         */
        public Piece piece;

        /**
         * Occupy current space with piece
         *
         * @param piece
         */
        public void occupy(Piece piece) {
            this.piece = piece;
        }
    }

    Space[][] board = new Space[Constant.BOARD_SIZE_X][Constant.BOARD_SIZE_Y];

    /**
     * @details Initialize everything.
     */
    public Board() {
        initializeSpace();
        initializePieces();
    }

    /**
     * Allocate memory space
     */
    public void initializeSpace () {
        for (int x = 0; x < Constant.BOARD_SIZE_X ; x ++) {
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y ++) {
                board[x][y] = new Space();
            }
        }
    }

    /**
     * Get piece by their location
     *
     * @param x Location on axis x
     * @param y Location on axis y
     * @return Piece Target piece
     */
    public Piece getPiece(int x, int y) {
        return board[x][y].piece;
    }

    /**
     * Get piece by the location
     *
     * @param loc Movement style
     * @return Piece Target piece
     */
    public Piece getPiece(Movement loc) {
        if (loc == null) return null;
        if (!loc.valid()) return null;
        return board[loc.x][loc.y].piece;
    }

    /**
     * Find a piece from the board by its name and color.
     *
     * @param name Name of the piece that are looking for
     * @param color Color of the piece
     * @return Piece Null if nothing found
     */
    public Piece getPiece(String name, PieceColor color) {
        for (int x = 0; x < Constant.BOARD_SIZE_X ; x ++) {
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y ++) {
                if (getPiece(x, y) != null && getPiece(x, y).name.equals(name) && getPiece(x, y).color == color) {
                    return getPiece(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Get all pieces
     *
     * @return List<Piece> All the pieces
     */
    public List<Piece> getPieces() {
        List<Piece> result = new Vector<Piece>();
        for (int x = 0; x < Constant.BOARD_SIZE_X; x++) {
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y++) {
                if (getPiece(x, y) != null) {
                    result.add(getPiece(x, y));
                }
            }
        }
        return result;
    }

    /**
     * Insert a piece into the board.
     * Will also write this initial location into the piece log.
     *
     * @param piece The piece that wish to be inserted
     * @param x Location on axis x
     * @param y Location on axis y
     */
    public void insertPiece(Piece piece, int x, int y) {
        Movement movement = new Movement(x, y);
        piece.moveTo(movement);
        board[movement.x][movement.y].occupy(piece);
    }

    public void insertPiece(Piece piece, Movement movement) {
        insertPiece(piece, movement.x, movement.y);
    }

    /**
     * Initialize Pieces.
     */
    public void initializePieces () {
        insertPiece(new PieceRook(PieceColor.WHITE), 0, 0);
        insertPiece(new PieceKnight(PieceColor.WHITE), 0, 1);
        insertPiece(new PieceBishop(PieceColor.WHITE), 0, 2);
        insertPiece(new PieceQueen(PieceColor.WHITE), 0, 3);
        insertPiece(new PieceKing(PieceColor.WHITE), 0, 4);
        insertPiece(new PieceBishop(PieceColor.WHITE), 0, 5);
        insertPiece(new PieceKnight(PieceColor.WHITE), 0, 6);
        insertPiece(new PieceRook(PieceColor.WHITE), 0, 7);
        for (int y = 0; y < 8; y ++) {
            insertPiece(new PiecePawn(PieceColor.WHITE), 1, y);
        }

        insertPiece(new PieceRook(PieceColor.BLACK), 7, 0);
        insertPiece(new PieceKnight(PieceColor.BLACK), 7, 1);
        insertPiece(new PieceBishop(PieceColor.BLACK), 7, 2);
        insertPiece(new PieceQueen(PieceColor.BLACK), 7, 3);
        insertPiece(new PieceKing(PieceColor.BLACK), 7, 4);
        insertPiece(new PieceBishop(PieceColor.BLACK), 7, 5);
        insertPiece(new PieceKnight(PieceColor.BLACK), 7, 6);
        insertPiece(new PieceRook(PieceColor.BLACK), 7, 7);
        for (int y = 0; y < 8; y ++) {
            insertPiece(new PiecePawn(PieceColor.BLACK), 6, y);
        }

//        // custom piece
//        insertPiece(new PieceZebra(PieceColor.BLACK), 2, 2);
//        insertPiece(new PieceTnt(PieceColor.BLACK), 2, 5);
//        insertPiece(new PieceZebra(PieceColor.WHITE), 5, 5);
//        insertPiece(new PieceTnt(PieceColor.WHITE), 5, 2);
    }

    /**
     * Function that take a filter as an argument, which can filter the pieces by particular name or particular color,
     * and return all possible movement in the board.
     *
     * @param filter Function that filter out the unwanted pieces
     * @return List<Movement> All available movements
     */
    public List<Movement> getAvailableMovementsOfCondition(Function<Piece, Boolean> filter) {
        Set<Movement> allAvailableMovements = new HashSet<Movement>();
        // go through all slots to find possible movements
        for (int x = 0; x < Constant.BOARD_SIZE_X; x ++) {
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y ++) {
                Movement movement = new Movement(x, y);
                Piece piece = getPiece(movement);
                // apply the filter if it is applicable
                if (piece == null || (filter != null && !filter.apply(piece))) continue;
                // Join all the results
                allAvailableMovements.addAll(piece.getAvailableMovements(movement, this));
            }
        }
        return allAvailableMovements.stream().collect(Collectors.toList());
    }

    /**
     * Take an location as the argument and return all possible movements of the piece on that location.
     *
     * @param pieceLoc The location of the piece that wish to be inspected
     * @return List<Movement> All available movements
     */
    public List<Movement> getAvailableMovementsOfPiece(Movement pieceLoc) {
        // If current location do not have piece, return empty list
        if (this.getPiece(pieceLoc) == null) return new Vector<Movement>();
        return this.getPiece(pieceLoc).getAvailableMovements(pieceLoc, this)
                .stream()
                // filter by check if it can occupy other pieces
                .filter(m -> this.getPiece(pieceLoc).willOccupyWhenConflict(this.getPiece(m)))
                .collect(Collectors.toList());
    }

    /**
     * Event caller for subscriber design pattern.
     * Go through every registered events for particular event name. If the fn supply a piece, then substitute current
     * one with it.
     *
     * @param piece The piece that you will to call
     * @param eventName
     */
    public void callEvent(Piece piece, String eventName) {
        if (!piece.events.containsKey(eventName)) return;
        piece.events.get(eventName).stream().forEach(fn -> {
            Piece returnVal = fn.apply(this);
            if (returnVal == null) return;

            // deep copy the movement log and its color
            returnVal.moveLog = piece.moveLog.stream().collect(Collectors.toList());
            returnVal.color = piece.color;

            Movement currentMovement = piece.currentMovement();
            board[currentMovement.x][currentMovement.y].piece = returnVal;
        });
    }

    /**
     * MoveController piece on board. Take two movements as the arguments.
     * Will also triger the beforeMove and afterMove events.
     *
     * @param from The location of source
     * @param to The location of target
     */
    public void movePiece(Movement from, Movement to) {
        callEvent(getPiece(from), "BEFORE_MOVE");

        removePiece(to);

        board[to.x][to.y].piece = board[from.x][from.y].piece;
        board[from.x][from.y].piece = null;

        if (this.getPiece(to) == null) return;

        this.getPiece(to).moveTo(to);

        callEvent(getPiece(to), "AFTER_MOVE");
    }

    /**
     * Remove piece
     *
     * @param to Coordinate of piece
     */
    public void removePiece(Movement to) {
        if (!to.valid()) return;

        if (board[to.x][to.y].piece != null) {
            callEvent(getPiece(to), "BE_CAPTURED");
        }
        board[to.x][to.y].piece = null;
    }

    /**
     * Generate a printable board. Return the board with an 2-d array of strings.
     *
     * @return String[][] The string array that represent the whole board
     */
    public String[][] generateBoard() {
        String[][] printableBoard = new String[Constant.BOARD_SIZE_X][Constant.BOARD_SIZE_Y];
        for (int x = 0; x < Constant.BOARD_SIZE_X; x ++) {
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y ++) {
                printableBoard[x][y] = board[x][y].piece != null ? String.valueOf(board[x][y].piece.shortName) : " ";
            }
        }
        return printableBoard;
    }

    /**
     * Print the board string array.
     *
     * @param board The string array that represent the whole board
     * @param outStream
     */
    public void printBoard(String[][] board, OutputStream outStream) {
        try {
            outStream.write("  ".getBytes());
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y++) {
                outStream.write(y + 97);
            }
            outStream.write("\r\n ┌".getBytes());
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y++) {
                outStream.write("─".getBytes());
            }
            outStream.write("┐\r\n".getBytes());
            for (int x = 0; x < Constant.BOARD_SIZE_X; x++) {
                outStream.write((String.valueOf(x + 1) + "│").getBytes());
                for (int y = 0; y < Constant.BOARD_SIZE_Y; y++) {
                    outStream.write(board[x][y].getBytes());
                }
                outStream.write(("│" + String.valueOf(x+1) + "\r\n").getBytes());
            }
            outStream.write(" └".getBytes());
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y++) {
                outStream.write("─".getBytes());
            }
            outStream.write("┘\r\n  ".getBytes());
            for (int y = 0; y < Constant.BOARD_SIZE_Y; y++) {
                outStream.write(y + 97);
            }
            outStream.write("\r\n".getBytes());
        } catch (Exception e) {}
    }

    /**
     * Print original board with no other notation.
     *
     * @param outStream
     */
    public void printBoard(OutputStream outStream) {
        printBoard(generateBoard(), outStream);
    }

    /**
     * Print board with available movements noted on the board.
     *
     * @param pieceLoc The location of the piece that you wish to show
     * @param outStream
     */
    public void printBoardWithAvailableMovements(Movement pieceLoc, OutputStream outStream) {
        String[][] printableBoard = generateBoard();
        getAvailableMovementsOfPiece(pieceLoc).stream()
                .forEach(m -> {
                    printableBoard[m.x][m.y] = "⁕";
                });
        printBoard(printableBoard, outStream);
    }
}
