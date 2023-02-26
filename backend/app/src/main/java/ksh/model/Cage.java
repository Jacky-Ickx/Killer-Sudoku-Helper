package ksh.model;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;

/**
 * This class is used to handle the Cages of a Killer Sudoku.
 */
public class Cage {
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

    /** Cages representing normal Sudoku rules regarding rows */
    public static final Cage[] rowCages = new Cage[9];
    /** Cages representing normal Sudoku rules regarding columns */
    public static final Cage[] columnCages = new Cage[9];
    /** Cages representing normal Sudoku rules regarding nonets */
    public static final Cage[] nonetCages = new Cage[9];

    static { // init cages to represent normal sudoku rules
        for (int y = 0; y < 9; y++) {
            rowCages[y] = new Cage(45, Position.allPositions[y]);
        }

        final Position[][] nonets = new Position[9][9];
        for (int x = 0; x < 9; x++) {
            final Position[] column = new Position[9];
            for (int y = 0; y < 9; y++) {
                column[y] = Position.allPositions[y][x];
                nonets[(3 * (y / 3)) + (x / 3)][(3 * (y % 3)) + (x % 3)] = Position.allPositions[y][x];
            }
            columnCages[x] = new Cage(45, column);
        }

        for (int i = 0; i < 9; i++) {
            nonetCages[i] = new Cage(45, nonets[i]);
        }
    }

    /** target sum of cage */
    private final int sum;
    /** positions of cells that make up the cage */
    private final Position[] cells;

    // TODO: add handling for possible combinations

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
     * check whether another cage is completely contained in this cage.
     * 
     * used to check, whether a cage is completely contained in a specific region.
     * 
     * @param other cage to check
     * @return whether other is completely contained in this
     */
    public boolean containsCage(final Cage other) {
        final var cellList = Arrays.asList(this.cells);
        for (final var cell : other.cells) {
            if (!cellList.contains(cell)) return false;
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
     * get target sum of cage
     * 
     * @return target sum of cage
     */
    public int getSum() {
        return this.sum;
    }

    /**
     * get covered cells of cage
     * 
     * @return covered positions
     */
    public Position[] getCells() {
        return this.cells;
    }

    /**
     * checks whether a list of cages (array of cages) fulfills general static criteria needed for a valid killer sudoku
     * <br>
     * this validation does not guarantee validity, it just excludes obvious invalidity
     * 
     * @param cages list (array) of cages to check
     * @throws IllegalArgumentException when list of cages is obviously not valid or null
     */
    public static void validateCageList(final Cage[] cages) throws IllegalArgumentException {
        if (cages == null) throw new IllegalArgumentException("cages can't be null");

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
        for (int y = 0; y < 9; y++) {
            grid[y] = row.clone();
        }

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
