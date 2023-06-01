package ksh.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;

class CellTest {
    @Test
    void initializeWithAllPossibleValues() {
        final Cell cell = new Cell();
        assertTrue(CollectionUtils.isEqualCollection(cell.getPossibleValues(), new ArrayList<>(IntStream.rangeClosed(1, 9).boxed().toList())), "Cell should be initialized with all possible values");
    }

    @Test
    void removePossibleValue() {
        final Cell cell = new Cell();
        cell.removePossibleValue(9);
        assertTrue(CollectionUtils.isEqualCollection(cell.getPossibleValues(), new ArrayList<>(IntStream.rangeClosed(1, 8).boxed().toList())), "possible value 9 should have been removed");
    }

    @Test
    void initializeWithNoValues() {
        final Cell cell = new Cell();
        final int[] emptyArray = {};

        assertTrue(cell.getValues().equals(emptyArray), "Cell should be initialized with no values");
    }

    @Test
    void addValue() {
        final Cell cell = new Cell();
        final int[] arrayWith1 = { 1 };

        cell.addValue(1);
        assertTrue(cell.getValues().equals(arrayWith1), "Cell should have value 1");
    }

    @Test
    void ignoreDuplicateValue() {
        final Cell cell = new Cell();
        final int[] arrayWith1 = { 1 };

        cell.addValue(1);
        cell.addValue(1);
        assertTrue(cell.getValues().equals(arrayWith1), "Cell should not have value 1 duplicated");
    }

    @Test
    void removeValue() {
        final Cell cell = new Cell();
        final int[] arrayWith1 = { 1 };

        cell.addValue(1);
        cell.addValue(9);
        cell.removeValue(9);
        assertTrue(cell.getValues().equals(arrayWith1), "Cell should have value 1");
    }

    @Test
    void singularValue() {
        final Cell cell = new Cell();

        cell.addValue(5);
        assertEquals(5, cell.getValue(), "Cell should have value 5");
    }

    @Test
    void noValueWhenIsPencilMark() {
        final Cell cell = new Cell();
        cell.addValue(5);

        cell.addValue(1);
        cell.setIsPencilMark(true);
        assertEquals(Cell.NO_VALUE, cell.getValue(), "getValue() should return NO_VALUE when isPencilMark is true");
    }
}