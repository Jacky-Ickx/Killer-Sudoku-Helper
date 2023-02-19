package ksh.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class KillerSudokuTest {
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

    private static int[][] basicValidStartingGrid() {
        final int[][] startingGrid = new int[9][9];
        for (int y = 0; y < 9; y++) {
            Arrays.fill(startingGrid[y], Cell.NO_VALUE);
        }
        return startingGrid;
    }

    @Test
    void validConstruction_cagesOnly() {
        final var cageList = basicValidCageList();

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(cageList);
            }
        }, "valid Cage List should not throw an exception");
    }

    @Test
    void validConstruction_cagesAndStartingGrid() {
        final var cageList = basicValidCageList();
        final var startingGrid = basicValidStartingGrid();

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(startingGrid, cageList);
            }
        }, "valid Starting Grid should not throw an exception");
    }

    @Test
    void invalidConstruction_startingGridTooSmallVertically() {
        final var cageList = basicValidCageList();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(new int[8][9], cageList);
            }
        }, "invalid vertical dimension of starting grid should throw an exception");
    }

    @Test
    void invalidConstruction_startingGridTooSmallHorizontally() {
        final var cageList = basicValidCageList();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(new int[9][8], cageList);
            }
        }, "invalid horizontal dimension of starting grid should throw an exception");
    }

    @Test
    void invalidConstruction_startingGridTooBigVertically() {
        final var cageList = basicValidCageList();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(new int[10][9], cageList);
            }
        }, "invalid vertical dimension of starting grid should throw an exception");
    }

    @Test
    void invalidConstruction_startingGridTooBigHorizontally() {
        final var cageList = basicValidCageList();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(new int[9][10], cageList);
            }
        }, "invalid horizontal dimension of starting grid should throw an exception");
    }

    @Test
    void invalidConstruction_startingGridNull() {
        final var cageList = basicValidCageList();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(null, cageList);
            }
        }, "null starting grid should throw an exception");
    }

    private static final Cage[] cageList = basicValidCageList();
    private static KillerSudoku killerSudoku = new KillerSudoku(cageList);

    @Test
    void getGridReturns2DCellArray() {
        assertInstanceOf(Cell[][].class, killerSudoku.getGrid(), "getGrid() should return a 2D-array of Cells");
    }

    @Test
    void getCagesReturnsInitialCageList() {
        assertTrue(killerSudoku.getCages().equals(cageList), "getCages() should return the cages, which were used when initializing the killer sudoku");
    }

    @Test
    void setSolvedGridAcceptsValidGrid() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                killerSudoku.setSolvedGrid(new int[9][9]);
            }
        }, "setSolvedGrid() should accept a 9x9 grid");
    }

    private KillerSudoku basicSudokuWithGivenCells() {
        final var cageList = basicValidCageList();

        final int[][] startingGrid = new int[9][9];
        for (int y = 0; y < 9; y++) {
            Arrays.fill(startingGrid[y], Cell.NO_VALUE);
        }

        startingGrid[0][0] = 1;
        startingGrid[8][8] = 1;

        return new KillerSudoku(startingGrid, cageList);
    }

    @Test
    void getGivenCellValue() {
        final var sudoku = this.basicSudokuWithGivenCells();
        // testing handling of value of cell
        assertEquals(1, sudoku.getValue(0, 0), "value of given cell should be initialized with given value");
    }

    @Test
    void givenCellValueNotChangable() {
        final var sudoku = this.basicSudokuWithGivenCells();

        sudoku.setValue(0, 0, 2);
        assertEquals(1, sudoku.getValue(0, 0), "changing the value of a given cell should be impossible");
    }

    @Test
    void getDefaultCellValue() {
        final var sudoku = this.basicSudokuWithGivenCells();

        assertEquals(Cell.NO_VALUE, sudoku.getValue(0, 1), "default value of cell should be Cell.NO_VALUE");
    }

    @Test
    void setCellValue() {
        final var sudoku = this.basicSudokuWithGivenCells();

        sudoku.setValue(0, 1, 2);
        assertEquals(2, sudoku.getValue(0, 1), "value should have been changed");
        assertEquals(Cell.NO_VALUE, sudoku.getValue(0, 2), "value of vertically adjacent cell should not have been changed");
        assertEquals(Cell.NO_VALUE, sudoku.getValue(1, 1), "value of horizontally adjacent cell should not have been changed");
    }

    @Test
    void deleteCellValue() {
        final var sudoku = this.basicSudokuWithGivenCells();

        sudoku.setValue(0, 1, 2);
        sudoku.unsetValue(0, 1);

        assertEquals(Cell.NO_VALUE, sudoku.getValue(0, 1), "value of cell should have been deleted");
    }

    // TODO: further testing (possible values and pencil marks)
}
