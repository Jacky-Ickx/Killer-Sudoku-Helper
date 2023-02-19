package ksh.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;

class CellTest {
    @Test
    void testPossibleValues() {
        final Cell cell = new Cell();
        assertTrue(CollectionUtils.isEqualCollection(cell.getPossibleValues(), new ArrayList<>(IntStream.rangeClosed(1, 9).boxed().toList())), "Cell should be initialized with all possible values");
        cell.removePossibleValue(9);
        assertTrue(CollectionUtils.isEqualCollection(cell.getPossibleValues(), new ArrayList<>(IntStream.rangeClosed(1, 8).boxed().toList())), "Cell should be initialized with all possible values");
    }

    @Test
    void testPencilMarks() {
        final Cell cell = new Cell();
        final var emptySet = new HashSet<Integer>();
        final var setWith1 = new HashSet<Integer>();
        setWith1.add(1);
        final var setWith1and9 = new HashSet<Integer>();
        setWith1and9.add(1);
        setWith1and9.add(9);

        assertTrue(cell.getPencilMarks().equals(emptySet), "Cell should be initialized with no pencil marks");

        cell.addPencilMark(1);
        assertTrue(cell.getPencilMarks().equals(setWith1), "Cell should have pencil mark 1");
        cell.addPencilMark(1);
        assertTrue(cell.getPencilMarks().equals(setWith1), "Cell should not have pencil mark 1 duplicated");

        cell.addPencilMark(9);
        assertTrue(cell.getPencilMarks().equals(setWith1and9), "Cell should have pencil mark 1 and 9");
        cell.removePencilMark(9);
        assertTrue(cell.getPencilMarks().equals(setWith1), "Cell should have pencil mark 1");

        cell.setValue(1);
        assertTrue(cell.getPencilMarks().equals(emptySet), "Pencil marks should be cleared when setting a value");
    }

    @Test
    void testValue() {
        final Cell cell = new Cell();

        assertEquals(cell.getValue(), Cell.NO_VALUE, "Cell should be initialized with NO_VALUE");

        cell.setValue(5);
        assertEquals(cell.getValue(), 5, "Cell should have value 5");

        cell.addPencilMark(1);
        assertEquals(cell.getValue(), Cell.NO_VALUE, "adding a pencil mark should clear the value");
    }
}