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

/**
 * This class is used to handle the Cages of a Killer Sudoku.
 */
class Cage {
    /** possible cage-sums for every possible cage-size */
    // @formatter:off
    private static final int[][] possibleSums = {
        { 1, 9 },
        { 3, 17 },
        { 6, 24 },
        { 10, 30 },
        { 15, 35 },
        { 21, 39 },
        { 28, 42 },
        { 36, 44 },
        { 45, 45 }
    };
    // @formatter:on

    /** target sum of cage */
    private final int sum;
    /** positions of cells that make up the cage */
    private final Position[] cells;

    /**
     * Basic constructor
     * 
     * @param sum   target sum of the cage
     * @param cells positions of cells that make up the cage
     * @throws IllegalArgumentException when no cells or more than 9 cells are given, when target sum is not achievable,
     *                                  when there are cells that are not adjacent to any other cell
     */
    public Cage(final int sum, final Position[] cells) throws IllegalArgumentException {
        // check if amount of cells is in [1, 9]
        if (cells.length <= 0 || cells.length > 9) {
            final String errorMessage = MessageFormat.format("amount of cells must be in [1, 9]; got {0} cells", cells.length);
            throw new IllegalArgumentException(errorMessage);
        }

        // check if sum is possible 
        if (sum < possibleSums[cells.length - 1][0] || sum > possibleSums[cells.length - 1][1]) {
            final String errorMessage = MessageFormat.format("sum {0} not possible for {1}-cell cage", sum, cells.length);
            throw new IllegalArgumentException(errorMessage);
        }

        // check for adjacency of cells
        if (cells.length > 1) for (final var cell : cells) {
            if (cell.adjacentTo(cells)) continue;

            final String errorMessage = MessageFormat.format("all cells need to be adjacent to at least one other cell; cell {0} is not adjacent to any other given cell", cell);
            throw new IllegalArgumentException(errorMessage);
        }

        this.sum = sum;
        this.cells = cells;
    }

    /**
     * checks, whether cage is filled and target sum is reached.
     * 
     * @param grid killer sudoku grid to check against
     * @return whether cage is filled and target sum is reached
     * @throws IllegalArgumentException when grid is not of size 9x9 or null
     */
    public boolean sumReached(final Cell[][] grid) throws IllegalArgumentException {
        KillerSudoku.validateGridDimensions(grid);

        int sum = 0;
        for (final var cell : this.cells) {
            if (grid[cell.y][cell.x].getValue() == Cell.NO_VALUE) return false; // cage not completely filled
            sum += grid[cell.y][cell.x].getValue();
        }
        return (sum == this.sum);
    }

    /**
     * checks, whether all values of the cells in a cage are unique (disregarding unfilled cells)
     * 
     * @param grid killer sudoku grid to check against
     * @return whether all values are unique
     * @throws IllegalArgumentException when grid is not of size 9x9 or null
     */
    public boolean cellsUnique(final Cell[][] grid) throws IllegalArgumentException {
        KillerSudoku.validateGridDimensions(grid);

        final var values = new HashSet<Integer>();
        for (final var cell : this.cells) {
            if (grid[cell.y][cell.x].getValue() == Cell.NO_VALUE) continue; // cage not completely filled
            if (!values.add(grid[cell.y][cell.x].getValue())) return false;
        }

        return true;
    }

    /**
     * get size of cage
     * 
     * @return amount of cells in cage
     */
    public int getSize() {
        return this.cells.length;
    }

    /**
     * checks whether a list of cages (array of cages) fulfills general static criteria needed for a valid killer sudoku
     * <br>
     * this validation does not guarantee validity, it just excludes obvious invalidity
     * 
     * @param cages list (array) of cages to check
     * @throws IllegalArgumentException when list of cages is obviously not valid
     */
    public static void validateCageList(final Cage[] cages) throws IllegalArgumentException {
        final int cells = Arrays.stream(cages).mapToInt((cage) -> cage.getSize()).sum();
        if (cells != 81) {
            final String errorMessage = MessageFormat.format("all cages must span exactly 81 cells cumulatively; given cells span {0} cells", cells);
            throw new IllegalArgumentException(errorMessage);
        }

        final int sum = Arrays.stream(cages).mapToInt((cage) -> cage.sum).sum();
        if (sum != 405) {
            final String errorMessage = MessageFormat.format("sum of target sum of all cages must equal 405; given cells have a cumulative target sum of {0}", sum);
            throw new IllegalArgumentException(errorMessage);
        }

        final int NOT_COVERED = 0;
        final int COVERED = 1;

        final int[][] grid = new int[9][9];
        final int[] row = new int[9];
        Arrays.fill(row, NOT_COVERED);
        Arrays.fill(grid, row);

        for (final var cage : cages) {
            for (final var cell : cage.cells) {
                grid[cell.y][cell.x] = COVERED;
            }
        }

        final int cellsCovered = Arrays.stream(grid).flatMapToInt(Arrays::stream).sum();
        if (cellsCovered != 81) {
            throw new IllegalArgumentException("cages do not span entire grid");
        }

        // TODO: logging (debug: sucessfully validated)
    }
}

/**
 * This class is used to have better Typing for Positions in a Sudoku-Grid. {x: 0, y: 0} denotes the top left corner and
 * {x: 8, y: 8} denotes the bottom right corner.
 */
class Position {
    /** horizontal position in range [0, 8] */
    public final int x;
    /** vertical position in range [0, 8] */
    public final int y;

    /**
     * Basic Constructor
     * 
     * @param x horizontal index of Position
     * @param y vertical index of Position
     * @throws IllegalArgumentException when indices are outside of expected range
     */
    public Position(final int x, final int y) throws IllegalArgumentException {
        if (x < 0 || x >= 9 || y < 0 || y >= 9) {
            final String errorMessage = MessageFormat.format("x and y must be in [0, 8]; got x = {0}, y = {1}", x, y);
            throw new IllegalArgumentException(errorMessage);
        }

        this.x = x;
        this.y = y;
    }

    /**
     * Check whether this Position is directly adjacent to another Position. excluding diagonal adjacency
     * 
     * @param other Position to check against
     * @return whether this Position is adjacent to other
     */
    public boolean adjacentTo(final Position other) {
        final int absoluteDifference = Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
        return (absoluteDifference == 1);
    }

    /**
     * Check whether this Position is directly adjacent to a list of other Positions. excluding diagonal adjacency
     * 
     * @param others Positions to check against
     * @return whether this Position is adjacent to other
     */
    public boolean adjacentTo(final Position[] others) {
        for (final var other : others) {
            if (this.adjacentTo(other)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("{x: %d, y: %d}", this.x, this.y);
    }

}

/**
 * This Class is used to handle the Cells of a (Killer) Sudoku Grid.
 */
class Cell {
    /** constant indicating an empty value */
    public static final int NO_VALUE = 0;

    /** value of the cell */
    private int value;
    /** list of possible valueos of the cell */
    private ArrayList<Integer> possibleValues;
    /** set of pencil marks of the cell */
    private final HashSet<Integer> pencilMarks;

    /**
     * Basic constructor
     */
    public Cell() {
        this.value = NO_VALUE;
        this.pencilMarks = new HashSet<>();
        this.resetPossibleValues();
    }

    /**
     * gets all possible values of the cell
     * 
     * @return possible values
     */
    public ArrayList<Integer> getPossibleValues() {
        return this.possibleValues;
    }

    /**
     * sets possible values of the cell to all values from 1-9
     */
    public void resetPossibleValues() {
        this.possibleValues = new ArrayList<>(IntStream.rangeClosed(1, 9).boxed().toList());
    }

    /**
     * removes a possible value from the cell
     * 
     * @param value value to remove from possibilities
     */
    public void removePossibleValue(final Integer value) {
        this.possibleValues.remove(value);
    }

    /**
     * gets all pencil marks of the cell
     * 
     * @return pencil marks
     */
    public HashSet<Integer> getPencilMarks() {
        return this.pencilMarks;
    }

    /**
     * removes a pencil mark from the cell
     * 
     * @param value pencil mark to remove
     */
    public void removePencilMark(final Integer value) {
        this.pencilMarks.remove(value);
    }

    /**
     * adds pencil mark to the cell
     * 
     * @param value pencil mark to add
     */
    public void addPencilMark(final Integer value) {
        this.unsetValue();
        this.pencilMarks.add(value);
    }

    /**
     * gets value of the cell
     * 
     * @return value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * removes the value of a cell
     */
    public void unsetValue() {
        this.value = NO_VALUE;
    }

    /**
     * sets the value of the cell and clears all pencil marks
     * 
     * @param value value to be set
     */
    public void setValue(final int value) {
        this.pencilMarks.clear();
        this.value = value;
    }
}
