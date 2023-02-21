package ksh.solver;

import ksh.model.KillerSudoku;

public class Solver {
    private final KillerSudoku sudoku;

    public Solver(final KillerSudoku sudoku) {
        this.sudoku = sudoku;
    }

    public Step getNextStep() {
        // choose strategy
        final Strategy strategy = Strategies.STRATEGIES.get(Strategies.Level.BRUTEFORCE)[0];
        // apply strategy
        final Step step = strategy.apply(this.sudoku.getCages(), this.sudoku.getGrid());
        // evaluate and return step
        return step;
    }
}
