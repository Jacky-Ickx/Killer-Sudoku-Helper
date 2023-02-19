package ksh.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    void validConstruction_withinBoundaries() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(1, 5);
            }
        }, "valid position should not throw an exception");
    }

    @Test
    void validConstruction_onLowerBoundaries() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(0, 0);
            }
        }, "using exact lower boundary should not throw an exception");
    }

    @Test
    void validConstruction_onUpperBoundaries() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(8, 8);
            }
        }, "using exact upper boundary should not throw an exception");
    }

    @Test
    void invalidConstruction_exceedingLowerXBoundary() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(-1, 0);
            }
        }, "exceeding the lower x boundary should throw an IllegalArgumentException");
    }

    @Test
    void invalidConstruction_exceedingLowerYBoundary() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(0, -1);
            }
        }, "exceeding the lower y boundary should throw an IllegalArgumentException");
    }

    @Test
    void invalidConstruction_exceedingUpperXBoundary() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(9, 0);
            }
        }, "exceeding the upper x boundary should throw an IllegalArgumentException");
    }

    @Test
    void invalidConstruction_exceedingUpperYBoundary() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Position(0, 9);
            }
        }, "exceeding the upper y boundary should throw an IllegalArgumentException");
    }

    final static Position pos1 = new Position(0, 0);
    final static Position pos2 = new Position(1, 0);
    final static Position pos3 = new Position(1, 1);
    final static Position pos4 = new Position(8, 8);

    final static Position[] adjacentPositions = { pos1, pos2, pos3 };

    @Test
    void pos1_adjacentTo_pos2() {
        assertTrue(pos1.adjacentTo(pos2), "(0, 0) should be adjacent to (1, 0)");
    }

    @Test
    void pos1_notAdjacentTo_pos3() {
        assertFalse(pos1.adjacentTo(pos3), "(0, 0) should not be adjacent to (1, 1)");
    }

    @Test
    void pos1_notAdjacentTo_pos4() {
        assertFalse(pos1.adjacentTo(pos4), "(0, 0) should not be adjacent to (8, 8)");
    }

    @Test
    void pos1_adjacentTo_list() {
        assertTrue(pos1.adjacentTo(adjacentPositions), "(0, 0) should be adjacent to [(0, 0), (1, 0), (1, 1)]");
    }

    @Test
    void pos2_adjacentTo_pos1() {
        assertTrue(pos2.adjacentTo(pos1), "(1, 0) should be adjacent to (0, 0)");
    }

    @Test
    void pos2_adjacentTo_pos3() {
        assertTrue(pos2.adjacentTo(pos3), "(1, 0) should be adjacent to (1, 1)");
    }

    @Test
    void pos2_notAdjacentTo_pos4() {
        assertFalse(pos2.adjacentTo(pos4), "(1, 0) should not be adjacent to (8, 8)");
    }

    @Test
    void pos2_adjacentTo_list() {
        assertTrue(pos2.adjacentTo(adjacentPositions), "(1, 0) should be adjacent to [(0, 0), (1, 0), (1, 1)]");
    }

    @Test
    void pos3_notAdjacentTo_pos4() {
        assertFalse(pos3.adjacentTo(pos4), "(1, 1) should not be adjacent to (8, 8)");

    }

    @Test
    void pos3_adjacentTo_list() {
        assertTrue(pos3.adjacentTo(adjacentPositions), "(1, 1) should be adjacent to [(0, 0), (1, 0), (1, 1)]");
    }

    @Test
    void pos4_notAdjacentTo_list() {
        assertFalse(pos4.adjacentTo(adjacentPositions), "(8, 8) should not be adjacent to [(0, 0), (1, 0), (1, 1)]");
    }
}