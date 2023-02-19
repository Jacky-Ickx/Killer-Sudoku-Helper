package ksh.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    void constructorFiltersParameters() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(-1, 0);
            }
        }, "exceeding the lower x boundary should throw an IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(0, -1);
            }
        }, "exceeding the lower y boundary should throw an IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(9, 0);
            }
        }, "exceeding the upper x boundary should throw an IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(0, 9);
            }
        }, "exceeding the upper y boundary should throw an IllegalArgumentException");

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(0, 0);
            }
        }, "using exact lower boundary should not throw an exception");

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(8, 8);
            }
        }, "using exact upper boundary should not throw an exception");
    }

    @Test
    void testAdjacentTo() {
        final Position pos1 = new Position(0, 0);
        final Position pos2 = new Position(1, 0);
        final Position pos3 = new Position(1, 1);
        final Position pos4 = new Position(8, 8);

        final Position[] adjacentPositions = { pos1, pos2, pos3 };

        // test pos1
        assertTrue(pos1.adjacentTo(pos2), "(0, 0) should be adjacent to (1, 0)");
        assertFalse(pos1.adjacentTo(pos3), "(0, 0) should not be adjacent to (1, 1)");
        assertFalse(pos1.adjacentTo(pos4), "(0, 0) should not be adjacent to (8, 8)");

        assertTrue(pos1.adjacentTo(adjacentPositions), "(0, 0) should be adjacent to [(0, 0), (1, 0), (1, 1)]");

        // test pos2
        assertTrue(pos2.adjacentTo(pos1), "(1, 0) should be adjacent to (0, 0)");
        assertTrue(pos2.adjacentTo(pos3), "(1, 0) should be adjacent to (1, 1)");
        assertFalse(pos2.adjacentTo(pos4), "(1, 0) should not be adjacent to (8, 8)");

        assertTrue(pos2.adjacentTo(adjacentPositions), "(1, 0) should be adjacent to [(0, 0), (1, 0), (1, 1)]");

        // test pos3 / pos4
        assertFalse(pos3.adjacentTo(pos4), "(1, 1) should not be adjacent to (8, 8)");

        assertTrue(pos3.adjacentTo(adjacentPositions), "(1, 1) should be adjacent to [(0, 0), (1, 0), (1, 1)]");
        assertFalse(pos4.adjacentTo(adjacentPositions), "(8, 8) should not be adjacent to [(0, 0), (1, 0), (1, 1)]");
    }
}