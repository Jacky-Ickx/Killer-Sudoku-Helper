package ksh;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class KillerSudokuTest {
    @Test
    void constructorFiltersParameters() {
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
                new KillerSudoku(cageList);
            }
        }, "valid Cage List should not throw an exception");

        final int[][] startingGrid = new int[9][9];
        for (int y = 0; y < 9; y++) {
            Arrays.fill(startingGrid[y], Cell.NO_VALUE);
        }

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(startingGrid, cageList);
            }
        }, "valid Starting Grid should not throw an exception");

        // bad cases
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(new int[8][9], cageList);
            }
        }, "invalid vertical dimension of starting grid should not throw an exception");

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new KillerSudoku(new int[9][8], cageList);
            }
        }, "invalid horizontal dimension of starting grid should not throw an exception");
    }

    @Test
    void testKillerSudokuAPI() {
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

        final KillerSudoku sudoku = new KillerSudoku(cageList);

        assertInstanceOf(Cell[][].class, sudoku.getGrid(), "getGrid() should return a 2D-array of Cells");
        assertTrue(sudoku.getCages().equals(cageList), "getCages() should return the cages, which were used when initializing the killer sudoku");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                sudoku.setSolvedGrid(new int[9][9]);
            }
        }, "setSolvedGrid() should accept a 9x9 grid");
    }

    @Test
    void testMaskedCellAPI() {
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

        final int[][] startingGrid = new int[9][9];
        for (int y = 0; y < 9; y++) {
            Arrays.fill(startingGrid[y], Cell.NO_VALUE);
        }

        startingGrid[0][0] = 1;
        startingGrid[8][8] = 1;

        final KillerSudoku sudoku = new KillerSudoku(startingGrid, cageList);

        // testing handling of value of cell
        assertEquals(sudoku.getValue(0, 0), 1, "value of given cell should be initialized with given value");
        sudoku.setValue(0, 0, 2);
        assertEquals(sudoku.getValue(0, 0), 1, "changing the value of a given cell should be impossible");

        assertEquals(sudoku.getValue(0, 1), Cell.NO_VALUE, "default value of cell should be Cell.NO_VALUE");
        sudoku.setValue(0, 1, 2);
        assertEquals(sudoku.getValue(0, 1), 2, "value should have been changed");
        assertEquals(sudoku.getValue(0, 2), Cell.NO_VALUE, "value of vertically adjacent cell should not have been changed");
        assertEquals(sudoku.getValue(1, 1), Cell.NO_VALUE, "value of horizontally adjacent cell should not have been changed");

        sudoku.unsetValue(0, 1);
        assertEquals(sudoku.getValue(0, 1), Cell.NO_VALUE, "value of cell should have been deleted");

        // TODO: further testing (possible values and pencil marks)
    }
}
