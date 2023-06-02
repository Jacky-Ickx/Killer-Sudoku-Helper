package ksh.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This Class is used to handle the Cells of a (Killer) Sudoku Grid.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "possibleValues", "value" })
public class Cell {
	/** constant indicating an empty value */
	public static final int NO_VALUE = 0;

	/** values of the cell */
	private final HashSet<Integer> values = new HashSet<Integer>();
	/** list of possible values of the cell */
	private ArrayList<Integer> possibleValues;
	/** indicates wether singular value of cell is pencil mark or not */
	private boolean isPencilMark;

	/**
	 * Basic constructor
	 */
	public Cell() {
		this.isPencilMark = false;
		this.resetPossibleValues();
	}

	// TODO: Test value ranges

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
	 * gets pencilMark status of the cell
	 * 
	 * @return pencilMark status
	 */
	public boolean getIsPencilMark() {
		return this.isPencilMark;
	}

	/**
	 * sets pencilMark status of the cell
	 * 
	 * @param isPencilMark status to set
	 */
	public void setIsPencilMark(final boolean isPencilMark) {
		this.isPencilMark = isPencilMark;
	}

	public int[] getValues() {
		return this.values.stream().mapToInt(Integer::intValue).toArray();
	}

	/**
	 * gets value of the cell
	 * 
	 * @return value
	 */
	public int getValue() {
		if (this.values.size() != 1 || this.isPencilMark == true) {
			return NO_VALUE;
		}
		for (final int value : this.values) {
			return value;
		}
		return NO_VALUE;
	}

	/**
	 * adds a value to the cell
	 * 
	 * @param value value to be set
	 */
	public void addValue(final int value) {
		if (value == NO_VALUE || value < 1 || value > 9) return;
		this.values.add(value);
	}

	/**
	 * removes a value from the cell
	 * 
	 * @param value value to remove
	 */
	public void removeValue(final int value) {
		this.values.remove(value);
	}

	/**
	 * removes all values from cell
	 */
	public void deleteValues() {
		this.values.clear();
	}
}