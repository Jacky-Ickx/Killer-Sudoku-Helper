package ksh.services;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import ksh.model.KillerSudoku;
import ksh.model.StartingGrid;

@Service
public class KillerSudokuService {
    private final HashMap<String, KillerSudoku> games = new HashMap<>();

    public void createGame(final String id, final StartingGrid killerSudoku) {
        final int[][] startingGrid = new int[9][9];
        final var cells = killerSudoku.getGrid();

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (y >= cells.length || x >= cells[y].length) {
                    startingGrid[y][x] = ksh.model.Cell.NO_VALUE;
                    continue;
                }
                startingGrid[y][x] = cells[y][x];
            }
        }

        this.games.put(id, new KillerSudoku(startingGrid, killerSudoku.getCages()));
        return;
    }

    public KillerSudoku getGameById(final String id) {
        return this.games.get(id);
    }
}
