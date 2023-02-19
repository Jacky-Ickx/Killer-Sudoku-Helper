package ksh.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class CageTest {
    final static Position pos1 = new Position(7, 3);
    final static Position pos2 = new Position(8, 3);
    final static Position pos3 = new Position(8, 4);
    final static Position pos4 = new Position(7, 5);

    final static Position[] positions = { pos1, pos2, pos3 };
    final static Position[] singlePosition = { pos1 };

    final static Cage cage = new Cage(18, positions);

    @Test
    void validConstruction_sumWithinBoundaries() {
        // good cases
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(18, positions);
            }
        }, "valid Cage should not throw an exception");
    }

    @Test
    void validConstruction_sumOnLowerBoundary() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(24, positions);
            }
        }, "upper target-sum-boundary should not throw an exception");
    }

    @Test
    void validConstruction_sumOnUpperBoundary() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(6, positions);
            }
        }, "lower target-sum-boundary should not throw an exception");
    }

    @Test
    void validConstruction_singlePosition() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(5, singlePosition);
            }
        }, "single position should not throw an exception at the adjacency test");
    }

    @Test
    void invalidConstruction_sumExceedingLowerBoundary() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(5, positions);
            }
        }, "exceeding lower target-sum-boundary should throw an error");
    }

    @Test
    void invalidConstruction_sumExceedingUpperBoundary() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(25, positions);
            }
        }, "exceeding upper target-sum-boundary should throw an error");
    }

    @Test
    void invalidConstruction_emptyPositionList() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(18, new Position[0]);
            }
        }, "empty position list should throw an error");
    }

    @Test
    void invalidConstruction_tooManyPositions() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(18, new Position[10]);
            }
        }, "empty position list should throw an error");
    }

    @Test
    void invalidConstruction_positionsNotAdjacent() {
        final Position[] invalidPositionList = { pos1, pos2, pos3, pos4 };
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Cage(18, invalidPositionList);
            }
        }, "not fully adjacent position list should throw an error");
    }

    private static Cell[][] emptyGrid() {
        final Cell[][] grid = new Cell[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                grid[y][x] = new Cell();
            }
        }
        return grid;
    }

    @Test
    void cageUniqueWhenEmpty() {
        final Cell[][] grid = emptyGrid();

        assertTrue(cage.cellsUnique(grid), "empty cage should be unique");
    }

    @Test
    void cageSumNotReachedWhenEmpty() {
        final Cell[][] grid = emptyGrid();

        assertFalse(cage.sumReached(grid), "empty cage should not have its sum reached");
    }

    @Test
    void cageUniqueWhenPartiallyFilled() {
        final Cell[][] grid = emptyGrid();

        grid[3][7].setValue(8);
        grid[3][8].setValue(10);

        assertTrue(cage.cellsUnique(grid), "partially filled cage should be unique");
    }

    @Test
    void cageSumNotReachedWhenPartiallyFilled() {
        final Cell[][] grid = emptyGrid();

        grid[3][7].setValue(8);
        grid[3][8].setValue(10);

        assertFalse(cage.sumReached(grid), "partially filled cage should not have its sum reached");
    }

    @Test
    void cageUniqueWhenFilled() {
        final Cell[][] grid = emptyGrid();

        grid[3][7].setValue(8);
        grid[3][8].setValue(4);
        grid[4][8].setValue(6);

        assertTrue(cage.cellsUnique(grid), "cage should be unique");
    }

    @Test
    void cageSumReachedWhenFilled() {
        final Cell[][] grid = emptyGrid();

        grid[3][7].setValue(8);
        grid[3][8].setValue(4);
        grid[4][8].setValue(6);

        assertTrue(cage.sumReached(grid), "cage should have its sum reached");
    }

    @Test
    void cageNotUniqueWhenFilled() {
        final Cell[][] grid = emptyGrid();

        grid[3][7].setValue(8);
        grid[3][8].setValue(2);
        grid[4][8].setValue(8);

        assertFalse(cage.cellsUnique(grid), "cage should not be unique");
    }

    @Test
    void cageSumReachedWhenFilled2() {
        final Cell[][] grid = emptyGrid();

        grid[3][7].setValue(8);
        grid[3][8].setValue(2);
        grid[4][8].setValue(8);

        assertTrue(cage.sumReached(grid), "cage should have its sum reached");
    }

    @Test
    void cageUniqueWhenFilled2() {
        final Cell[][] grid = emptyGrid();

        grid[3][7].setValue(8);
        grid[3][8].setValue(4);
        grid[4][8].setValue(9);

        assertTrue(cage.cellsUnique(grid), "cage should be unique");
    }

    @Test
    void cageSumNotReachedWhenFilled() {
        final Cell[][] grid = emptyGrid();

        grid[3][7].setValue(8);
        grid[3][8].setValue(4);
        grid[4][8].setValue(9);

        assertFalse(cage.sumReached(grid), "cage should not have its sum reached");
    }

    private static Position[][] allPositions() {
        final Position[][] allPositions = new Position[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                allPositions[y][x] = new Position(x, y);
            }
        }
        return allPositions;
    }

    private static Cage[] basicValidCageList() {
        final var allPositions = allPositions();
        // @formatter:off
        final Cage[] cageList = new Cage[] {
            new Cage(45, allPositions[0]),
            new Cage(45, allPositions[1]),
            new Cage(45, allPositions[2]),
            new Cage(45, allPositions[3]),
            new Cage(45, allPositions[4]),
            new Cage(45, allPositions[5]),
            new Cage(45, allPositions[6]),
            new Cage(45, allPositions[7]),
            new Cage(45, allPositions[8]),
        };
        // @formatter:on
        return cageList;
    }

    @Test
    void validCageList() {
        final var cageList = basicValidCageList();

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                Cage.validateCageList(cageList);
            }
        }, "valid Cage list should not throw an exception");
    }

    @Test
    void inValidCageList_null() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Cage.validateCageList(null);
            }
        }, "null list should throw an error");
    }

    @Test
    void inValidCageList_SumToHigh() {
        final var allPositions = allPositions();
        final var cageList = basicValidCageList();

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
                    new Cage(44, Arrays.copyOfRange(allPositions[8], 0, 7)),
                    new Cage(2, Arrays.copyOfRange(allPositions[8], 8, 8))
                };
                // @formatter:on
                Cage.validateCageList(invalidSum);
            }
        }, "invalid sum should throw an error");
    }

    @Test
    void inValidCageList_TooFewCellsInCages() {
        final var allPositions = allPositions();
        final var cageList = basicValidCageList();

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Cage[] invalidAmountOfCells = cageList.clone();
                invalidAmountOfCells[8] = new Cage(44, Arrays.copyOfRange(allPositions[8], 0, 7));
                Cage.validateCageList(invalidAmountOfCells);
            }
        }, "invalid amount of cells in cages should throw an error");
    }

    @Test
    void inValidCageList_TooFewCellsCovered() {
        final var cageList = basicValidCageList();

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