package ksh.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class CageTest {
    @Test
    void constructorFiltersParameters() {
        final Position pos1 = new Position(7, 3);
        final Position pos2 = new Position(8, 3);
        final Position pos3 = new Position(8, 4);
        final Position pos4 = new Position(7, 5);

        final Position[] validPositionList = { pos1, pos2, pos3 };
        final Position[] validPositionList2 = { pos1 };
        final Position[] invalidPositionList = { pos1, pos2, pos3, pos4 };

        // good cases
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(18, validPositionList);
            }
        }, "valid Cage should not throw an exception");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(24, validPositionList);
            }
        }, "upper target-sum-boundary should not throw an exception");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(6, validPositionList);
            }
        }, "lower target-sum-boundary should not throw an exception");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(5, validPositionList2);
            }
        }, "single position should not throw an exception at the adjacency test");

        // bad cases
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(5, validPositionList);
            }
        }, "exceeding lower target-sum-boundary should throw an error");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(25, invalidPositionList);
            }
        }, "exceeding upper target-sum-boundary should throw an error");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(18, new Position[0]);
            }
        }, "empty position list should throw an error");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(18, invalidPositionList);
            }
        }, "not fully adjacent position list should throw an error");
    }

    @Test
    void testCageValidation() {
        // @formatter:off
        final Position[] positions = { 
            new Position(7, 3),
            new Position(8, 3),
            new Position(8, 4)
        };
        // @formatter:on

        final Cell[][] grid = new Cell[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                grid[y][x] = new Cell();
            }
        }

        final Cage cage = new Cage(18, positions);

        assertTrue(cage.cellsUnique(grid), "empty cage should be unique");
        assertFalse(cage.sumReached(grid), "empty cage should not have its sum reached");

        grid[3][7].setValue(8);
        grid[3][8].setValue(10);

        assertTrue(cage.cellsUnique(grid), "partially filled cage should be unique");
        assertFalse(cage.sumReached(grid), "partially filled cage should not have its sum reached");

        grid[3][8].setValue(4);
        grid[4][8].setValue(6);

        assertTrue(cage.cellsUnique(grid), "cage should be unique");
        assertTrue(cage.sumReached(grid), "cage should have its sum reached");

        grid[3][8].setValue(2);
        grid[4][8].setValue(8);

        assertFalse(cage.cellsUnique(grid), "cage should not be unique");
        assertTrue(cage.sumReached(grid), "cage should have its sum reached");

        grid[4][8].setValue(9);

        assertTrue(cage.cellsUnique(grid), "cage should be unique");
        assertFalse(cage.sumReached(grid), "cage should not have its sum reached");
    }

    @Test
    void testCageListValidation() {
        final Position[][] positions = new Position[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                positions[y][x] = new Position(x, y);
            }
        }

        // @formatter:off
        final Cage[] cageList = new Cage[] {
            new Cage(45, positions[0]),
            new Cage(45, positions[1]),
            new Cage(45, positions[2]),
            new Cage(45, positions[3]),
            new Cage(45, positions[4]),
            new Cage(45, positions[5]),
            new Cage(45, positions[6]),
            new Cage(45, positions[7]),
            new Cage(45, positions[8]),
        };
        // @formatter:on

        // good cases
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                Cage.validateCageList(cageList);
            }
        }, "valid Cage list should not throw an exception");

        // bad cases
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Cage.validateCageList(null);
            }
        }, "null list should throw an error");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                // @formatter:off
                final Cage[] invalidSum = new Cage[] {
                    cageList[0],
                    cageList[1],
                    cageList[2],
                    cageList[3],
                    cageList[4],
                    cageList[5],
                    cageList[6],
                    cageList[7],
                    new Cage(44, Arrays.copyOfRange(positions[8], 0, 7)),
                    new Cage(2, Arrays.copyOfRange(positions[8], 8, 8))
                };
                // @formatter:on
                Cage.validateCageList(invalidSum);
            }
        }, "invalid sum should throw an error");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Cage[] invalidAmountOfCells = cageList.clone();
                invalidAmountOfCells[8] = new Cage(44, Arrays.copyOfRange(positions[8], 0, 7));
                Cage.validateCageList(invalidAmountOfCells);
            }
        }, "invalid amount of cells in cages should throw an error");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Cage[] invalidCellsCovered = cageList.clone();
                invalidCellsCovered[8] = invalidCellsCovered[7];
                Cage.validateCageList(invalidCellsCovered);
            }
        }, "invalid amount of cells covered should throw an error");
    }
}