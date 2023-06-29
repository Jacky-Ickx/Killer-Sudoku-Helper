package ksh.solver;

import java.util.ArrayList;

import ksh.model.KillerSudoku;
import ksh.model.Position;

public class Solver {

	public Solver() {
	}

	public static Step getNextStep(final KillerSudoku sudoku) {
		// TODO: Error Detection
		try {
			final var errors = new ArrayList<Position>();

			for (int y = 0; y < 9; y++) {
				for (int x = 0; x < 9; x++) {
					if (sudoku.checkCell(x, y) == false) errors.add(new Position(x, y));
				}
			}

			if (!errors.isEmpty()) {
				// @formatter:off
                return new Step("Error Detection",
                                true,
                                null,
                                null,
                                null, 
                                (Position[]) errors.toArray(),
                                null
                );
                // @formatter:on
			}
		}
		catch (final IllegalStateException e) {
			// TODO: what to do in this spot?
		}

		final var strats = Strategies.STRATEGIES;

		// choose strategy
		final Strategy strategy = strats.get(Level.EASY)[0];
		// apply strategy
		final Step step = strategy.apply(sudoku.getCages(), sudoku.getGrid());
		// evaluate and return step
		return step;
	}
}
