package ksh;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

// IMPORTANT: empty cells in grid are currently assumed to be 0 (by Cage::checkSum)
// TODO: logging
/**
 * This class represents a killer sudoku.
 */
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
     * @throws IllegalArgumentException when list of cages is obviously not valid
     */
    private KillerSudoku(final Cage[] cages) throws IllegalArgumentException {
        Cage.validateCageList(cages);
        this.cages = cages;
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

        final Object[] convertedStartingGrid = Arrays.stream(startingGrid).map(row -> IntStream.of(row).boxed().toArray()).toArray();
        validateGridDimensions(Arrays.copyOf(convertedStartingGrid, convertedStartingGrid.length, Integer[][].class));

        this.startingGrid = startingGrid;

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                this.grid[y][x].setValue(startingGrid[y][x]);
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

        final Object[] convertedSolvedGrid = Arrays.stream(solvedGrid).map(row -> IntStream.of(row).boxed().toArray()).toArray();
        validateGridDimensions(Arrays.copyOf(convertedSolvedGrid, convertedSolvedGrid.length, Integer[][].class));

        this.solvedGrid = solvedGrid;
    }

    /**
     * gets all possible values of a given cell
     * 
     * @param x horizontal index of cell
     * @param y vertical index of cell
     * @return possible values
     */
    public ArrayList<Integer> getPossibleValues(final int x, final int y) {
        return this.grid[y][x].getPossibleValues();
    }

    /**
     * sets possible values of a given cell to all values from 1-9
     * 
     * @param x horizontal index of cell
     * @param y vertical index of cell
     */
    public void resetPossibleValues(final int x, final int y) {
        this.grid[y][x].resetPossibleValues();
    }

    /**
     * removes a possible value from a given cell
     * 
     * @param x     horizontal index of cell
     * @param y     vertical index of cell
     * @param value value to remove from possibilities
     */
    public void removePossibleValue(final int x, final int y, final Integer value) {
        this.grid[y][x].removePossibleValue(value);
    }

    /**
     * gets all pencil marks of a given cell
     * 
     * @param x horizontal index of cell
     * @param y vertical index of cell
     * @return pencil marks
     */
    public HashSet<Integer> getPencilMarks(final int x, final int y) {
        return this.grid[y][x].getPencilMarks();
    }

    /**
     * removes a pencil mark from a given cell <br>
     * returns early if the indicated cell is given in the starting grid
     * 
     * @param x     horizontal index of cell
     * @param y     vertical index of cell
     * @param value pencil mark to remove
     */
    public void removePencilMark(final int x, final int y, final Integer value) {
        if (this.startingGrid != null && this.startingGrid[y][x] != Cell.NO_VALUE) return;
        this.grid[y][x].removePencilMark(value);
    }

    /**
     * adds pencil mark to a given cell <br>
     * returns early if the indicated cell is given in the starting grid
     * 
     * @param x     horizontal index of cell
     * @param y     vertical index of cell
     * @param value pencil mark to add
     */
    public void addPencilMark(final int x, final int y, final Integer value) {
        if (this.startingGrid != null && this.startingGrid[y][x] != Cell.NO_VALUE) return;
        this.grid[y][x].addPencilMark(value);
    }

    /**
     * gets value of a given cell
     * 
     * @param x horizontal index of cell
     * @param y vertical index of cell
     * @return value
     */
    public int getValue(final int x, final int y) {
        return this.grid[y][x].getValue();
    }

    /**
     * removes the value of a cell <br>
     * returns early if the indicated cell is given in the starting grid
     * 
     * @param x horizontal index of cell
     * @param y vertical index of cell
     */
    public void unsetValue(final int x, final int y) {
        if (this.startingGrid != null && this.startingGrid[y][x] != Cell.NO_VALUE) return;
        this.grid[y][x].unsetValue();
    }

    /**
     * sets the value of a given cell <br>
     * returns early if the indicated cell is given in the starting grid
     * 
     * @param x     horizontal index of cell
     * @param y     vertical index of cell
     * @param value value to be set
     */
    public void setValue(final int x, final int y, final int value) {
        if (this.startingGrid != null && this.startingGrid[y][x] != Cell.NO_VALUE) return;
        this.grid[y][x].setValue(value);
    }

    /**
     * Method used for input validation in other methods. <br>
     * Checks, whether grid exists and is of size 9x9.
     * 
     * @param grid killer sudoku grid to check
     * @throws IllegalArgumentException when grid is not of size 9x9 or null
     */
    public static <T> void validateGridDimensions(final T[][] grid) throws IllegalArgumentException {
        try {
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
        catch (final NullPointerException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
