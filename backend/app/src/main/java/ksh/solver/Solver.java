package ksh.solver;

import java.util.ArrayList;

import ksh.model.KillerSudoku;
import ksh.model.Position;

public class Solver {
    private final KillerSudoku sudoku;

    public Solver(final KillerSudoku sudoku) {
        this.sudoku = sudoku;
    }

    public Step getNextStep() {
        // TODO: Error Detection
        try {
            final var errors = new ArrayList<Position>();

            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    if (this.sudoku.checkCell(x, y) == false) errors.add(new Position(x, y));
                }
            }

            if (!errors.isEmpty()) {
                return new Step();
            }
        }
        catch (final IllegalStateException e) {
            // TODO: what to do in this spot?
        }

        final var strats = Strategies.STRATEGIES; // make following code more readable

        // choose strategy
        final Strategy strategy = strats.get(Level.BRUTEFORCE)[0];
        // apply strategy
        final Step step = strategy.apply(this.sudoku.getCages(), this.sudoku.getGrid());
        // evaluate and return step
        return step;
    }
}
