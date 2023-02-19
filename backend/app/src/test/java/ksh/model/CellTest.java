package ksh.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
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
    void initializeWithNoPencilMarks() {
        final Cell cell = new Cell();
        final var emptySet = new HashSet<Integer>();

        assertTrue(cell.getPencilMarks().equals(emptySet), "Cell should be initialized with no pencil marks");
    }

    @Test
    void setPencilMark() {
        final Cell cell = new Cell();
        final var setWith1 = new HashSet<Integer>();
        setWith1.add(1);

        cell.addPencilMark(1);
        assertTrue(cell.getPencilMarks().equals(setWith1), "Cell should have pencil mark 1");
    }

    @Test
    void ignoreDuplicatePencilMark() {
        final Cell cell = new Cell();
        final var setWith1 = new HashSet<Integer>();
        setWith1.add(1);

        cell.addPencilMark(1);
        cell.addPencilMark(1);
        assertTrue(cell.getPencilMarks().equals(setWith1), "Cell should not have pencil mark 1 duplicated");
    }

    @Test
    void removePencilMark() {
        final Cell cell = new Cell();
        final var setWith1 = new HashSet<Integer>();
        setWith1.add(1);

        cell.addPencilMark(1);
        cell.addPencilMark(9);
        cell.removePencilMark(9);
        assertTrue(cell.getPencilMarks().equals(setWith1), "Cell should have pencil mark 1");
    }

    @Test
    void resetPencilMarksWhenSettingValue() {
        final Cell cell = new Cell();
        final var emptySet = new HashSet<Integer>();

        cell.addPencilMark(1);
        cell.setValue(1);
        assertTrue(cell.getPencilMarks().equals(emptySet), "Pencil marks should be cleared when setting a value");
    }

    @Test
    void initializeWithNoValue() {
        final Cell cell = new Cell();

        assertEquals(Cell.NO_VALUE, cell.getValue(), "Cell should be initialized with NO_VALUE");
    }

    @Test
    void setValue() {
        final Cell cell = new Cell();

        cell.setValue(5);
        assertEquals(5, cell.getValue(), "Cell should have value 5");
    }

    @Test
    void resetValueWhenAddingPencilMark() {
        final Cell cell = new Cell();
        cell.setValue(5);

        cell.addPencilMark(1);
        assertEquals(Cell.NO_VALUE, cell.getValue(), "adding a pencil mark should clear the value");
    }
}