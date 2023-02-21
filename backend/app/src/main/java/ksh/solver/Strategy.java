package ksh.solver;

import ksh.model.Cage;
import ksh.model.Cell;

public interface Strategy {
    Step apply(final Cage[] cages, final Cell[][] grid);
}
