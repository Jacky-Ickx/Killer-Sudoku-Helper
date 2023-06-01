package ksh.model;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// IMPORTANT: empty cells in grid are currently assumed to be 0 (by Cage::checkSum)
// TODO: logging
/**
 * This class represents a killer sudoku.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "startingGrid", "solvedGrid" })
public class KillerSudoku {
    /** Cages of the killer sudoku */
    private final Cage[] cages;
    /** (Killer) Sudoku grid */
    private final Cell[][] grid = new Cell[9][9];
    /** optional starting grid for when some values are given */
    private int[][] startingGrid = null;
    /** grid with solution to the killer sudoku */
    private int[][] solvedGrid = null; // TODO: somehow implement this (maybe "class Solution{}")

    /**
     * Basic constructor
     * 
     * @param cages array of cages of the killer sudoku
     * @throws IllegalArgumentException when list of cages is obviously not valid or null
     */
    public KillerSudoku(final Cage[] cages) throws IllegalArgumentException {
        Cage.validateCageList(cages);
        this.cages = cages;

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                this.grid[y][x] = new Cell();
            }
        }
    }

    /**
     * Constructor for killer sudokus with given cells
     * 
     * @param startingGrid grid with given cells
     * @param cages        array of cages of the killer sudoku
     * @throws IllegalArgumentException when startingGrid is not of size 9x9 or null
     */
    public KillerSudoku(final int[][] startingGrid, final Cage[] cages) throws IllegalArgumentException {
        this(cages);

        validateIntGridDimensions(startingGrid);

        this.startingGrid = startingGrid;

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                this.grid[y][x].addValue(startingGrid[y][x]);
            }
        }
    }

    /**
     * gets current state of the (killer) sudoku grid
     * 
     * @return state of the grid
     */
    public Cell[][] getGrid() {
        return this.grid;
    }

    /**
     * gets all cages of the killer sudoku
     * 
     * @return cages of the killer sudoku
     */
    public Cage[] getCages() {
        return this.cages;
    }

    /**
     * sets the solved grid
     * 
     * @param solvedGrid grid with solution to the killer sudoku
     * @throws IllegalArgumentException when solvedGrid is not of size 9x9 or null
     */
    public void setSolvedGrid(final int[][] solvedGrid) throws IllegalArgumentException {
        if (this.solvedGrid != null) return;

        validateIntGridDimensions(solvedGrid);
        // TODO: further validation
        this.solvedGrid = solvedGrid;
    }

    /**
     * checks whether the cell at a given position is either unsolved or solved correctly
     * 
     * @param x horizontal position of the cell
     * @param y verticale position of the cell
     * @return whether cell has no value or the correct value
     * @throws IllegalStateException when the sudoku has not been solved yet
     */
    public boolean checkCell(final int x, final int y) throws IllegalStateException {
        try {
            return (this.grid[y][x].getValue() == Cell.NO_VALUE || this.grid[y][x].getValue() == this.solvedGrid[y][x]);
        }
        catch (final NullPointerException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Method used for input validation in other methods. <br>
     * Checks, whether grid exists and is of size 9x9.
     * 
     * @param grid killer sudoku grid to check
     * @throws IllegalArgumentException when grid is not of size 9x9 or null
     */
    public static <T> void validateGridDimensions(final T[][] grid) throws IllegalArgumentException {
        if (grid == null) throw new IllegalArgumentException("grid can't be null");

        // check grid size
        if (grid.length != 9) {
            final String errorMessage = MessageFormat.format("the given grid must be of size 9x9; got grid with a vertical size of {0}", grid.length);
            throw new IllegalArgumentException(errorMessage);
        }
        for (final var row : grid) {
            if (row.length != 9) {
                final String errorMessage = MessageFormat.format("the given grid must be of size 9x9; got a row with a horizontal size of {0}", row.length);
                throw new IllegalArgumentException(errorMessage);
            }
        }

        // TODO: logging (debug: sucessfully validated)
    }

    /**
     * Method used for input validation in other methods. <br>
     * Checks, whether grid exists and is of size 9x9.
     * 
     * Wraps validateGridDimensions(final T[][] grid)
     * 
     * @param grid killer sudoku grid to check
     * @throws IllegalArgumentException when grid is not of size 9x9 or null
     */
    public static void validateIntGridDimensions(final int[][] grid) throws IllegalArgumentException {
        if (grid == null) {
            validateGridDimensions(null);
            return;
        }

        final Integer[][] convertedGrid = new Integer[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < Math.min(grid[0].length, grid[y].length); x++) {
                convertedGrid[y][x] = (Integer) grid[y][x];
            }
        }
        validateGridDimensions(convertedGrid);
    }
}
