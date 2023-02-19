package ksh.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

/**
 * This Class is used to handle the Cells of a (Killer) Sudoku Grid.
 */
public class Cell {
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