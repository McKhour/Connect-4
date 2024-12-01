package org.example;

import java.util.Arrays;

public final class GameBoard {
    /**
     * It just works.
     */
    private final char[][] board;

    /**
     * Constructs a GameBoard with the specified dimensions.
     *
     * @param rows    the number of rows
     * @param columns the number of columns
     */
    public GameBoard(final int rows, final int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Rows and columns "
                    + "must be positive integers.");
        }
        this.board = new char[rows][columns];
        for (char[] row : this.board) {
            Arrays.fill(row, ' ');
        }
    }

    /**
     * Constructs a GameBoard with an existing board state.
     *
     * @param table the 2D char array representing the board state
     */
    public GameBoard(final char[][] table) {
        if (table == null || table.length == 0 || table[0].length == 0) {
            throw new
                    IllegalArgumentException("Board cannot be null or empty.");
        }
        this.board = copyBoard(table);
    }

    /**
     * Displays the current state of the game board.
     */
    public void display() {
        System.out.println(" 0 1 2 3 4 5 6");
        System.out.println("---------------");
        for (char[] row : board) {
            System.out.print("|");
            for (char cell : row) {
                System.out.print(cell);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println(" 0 1 2 3 4 5 6");
    }

    /**
     * Returns the current state of the board.
     *
     * @return a copy of the board
     */
    public char[][] getBoard() {
        return copyBoard(this.board);
    }

    /**
     * Returns a copy of the provided board.
     *
     * @param sourceBoard the board to copy
     * @return a deep copy of the board
     */
    private char[][] copyBoard(final char[][] sourceBoard) {
        char[][] copy = new char[sourceBoard.length][];
        for (int i = 0; i < sourceBoard.length; i++) {
            copy[i] = Arrays.copyOf(sourceBoard[i], sourceBoard[i].length);
        }
        return copy;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameBoard)) {
            return false;
        }
        GameBoard gameBoard = (GameBoard) o;
        return Arrays.deepEquals(this.board, gameBoard.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GameBoard:\n");
        for (char[] row : board) {
            builder.append(Arrays.toString(row)).append("\n");
        }
        return builder.toString();
    }
}
