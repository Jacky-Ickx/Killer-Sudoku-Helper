package ksh.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to handle the Cages of a Killer Sudoku.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "possibleCombinations", "setCombination" })
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

	/** Set of all possible combinations for this cage */
	private Set<Set<Integer>> possibleCombinations;
	/** inidicates, whether solver has found the correct combination for this cage? */
	private final boolean setCombination;

	// TODO: add handling for possible combinations

	/**
	 * Basic constructor
	 * 
	 * @param sum   target sum of the cage
	 * @param cells positions of cells that make up the cage
	 * @throws IllegalArgumentException when no cells or more than 9 cells are given, when target sum is not achievable,
	 *                                  when there are cells that are not adjacent to any other cell
	 */
	@JsonCreator
	public Cage(@JsonProperty("sum") final int sum, @JsonProperty("cells") final Position[] cells) throws IllegalArgumentException {
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
		this.possibleCombinations = null;
		this.setCombination = false;

		this.setPossibilities();
	}

	/**
	 * calculate and set possible combinations of cell values for this cage
	 */
	private void setPossibilities() {
		this.possibleCombinations = calculatePossiblities(this.cells.length, this.sum, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
	}

	/**
	 * calculate all possible combinations of cell values for a cage of given size, sum and set of available numbers
	 * 
	 * @param size             size of cage
	 * @param sum              sum of cage
	 * @param availableNumbers numbers available for cage
	 * @return set of possible combinations (which are in turn a set of numbers)
	 */
	@SuppressWarnings("unchecked")
	private static Set<Set<Integer>> calculatePossiblities(final int size, final int sum, final ArrayList<Integer> availableNumbers) {
		if (size == 1) {
			final Set<Set<Integer>> possibilities = new HashSet<Set<Integer>>();
			possibilities.add(new HashSet<Integer>(Arrays.asList(sum)));
			return (availableNumbers.contains(sum) ? possibilities : null);
		}
		final int remainingSize = size - 1;
		final Set<Set<Integer>> allPossibilities = new HashSet<Set<Integer>>();

		for (final int number : availableNumbers) {
			final int remainingSum = sum - number;

			if (remainingSum < possibleSums[remainingSize - 1][0] || remainingSum > possibleSums[remainingSize - 1][1]) continue;

			final ArrayList<Integer> remainingNumbers = (ArrayList<Integer>) availableNumbers.clone();
			remainingNumbers.remove((Integer) number);

			final Set<Set<Integer>> possibilities = calculatePossiblities(remainingSize, remainingSum, remainingNumbers);
			if (possibilities != null) for (final var possibility : possibilities) {
				possibility.add(number);
				allPossibilities.add(possibility);
			}
		}

		return allPossibilities;
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
