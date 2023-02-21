package ksh.solver;

import java.util.EnumMap;

import ksh.model.Cage;
import ksh.model.Cell;

public final class Strategies {
    enum Level {
        EASY, BRUTEFORCE
    }

    public static final EnumMap<Level, Strategy[]> STRATEGIES = new EnumMap<>(Level.class);

    // @formatter:off
    static {
        STRATEGIES.put(Level.EASY, new Strategy[] {

        });

        STRATEGIES.put(Level.BRUTEFORCE, new Strategy[] {
            Strategies::bruteforce
        });
    };
    // @formatter:on

    private Strategies() {

    }

    private static Step bruteforce(final Cage[] cages, final Cell[][] grid) {
        return new Step();
    }
}
