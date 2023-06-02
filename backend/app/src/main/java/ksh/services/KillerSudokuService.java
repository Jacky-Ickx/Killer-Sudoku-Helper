package ksh.services;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import ksh.model.KillerSudoku;
import ksh.model.StartingGrid;

/**
 * Service for managing active KillerSudoku Games
 */
@Service
public class KillerSudokuService {
    /** maps id to KillerSudoku object for every active game */
    private final HashMap<String, KillerSudoku> games = new HashMap<>();

    /**
     * creates a KillerSudoku from a StartingGrid and inserts it into the games map using the provided id
	 * 
	 * @param id id to use for insertion into games map
	 * @param killerSudoku starting grid to be used
	 */
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

	/**
	 * simple getter to get a KillerSudoku game associated with an id
	 * 
	 * @param id id of game
	 * @return KillerSudoku game
	 */
    public KillerSudoku getGameById(final String id) {
        return this.games.get(id);
    }
}
