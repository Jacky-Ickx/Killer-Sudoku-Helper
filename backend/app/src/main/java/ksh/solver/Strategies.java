package ksh.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.javatuples.Pair;

import ksh.model.Cage;
import ksh.model.Cell;
import ksh.model.Position;

enum Level {
	EASY, ADVANCED, COMPLEX, BRUTEFORCE
}

public final class Strategies {
	public static final EnumMap<Level, Strategy[]> STRATEGIES = new EnumMap<>(Level.class);

	// @formatter:off
    static {
        STRATEGIES.put(Level.EASY, new Strategy[] {
            Strategies::ruleOf45
        });

        STRATEGIES.put(Level.BRUTEFORCE, new Strategy[] {
            Strategies::bruteforce
        });
    };
    // @formatter:on

	private Strategies() {

	}

	@SuppressWarnings("unchecked")
	private static Step ruleOf45(final Cage[] cages, final Cell[][] grid) {
		final Pair<Integer, List<Position>>[] rows = new Pair[9];
		final Pair<Integer, List<Position>>[] columns = new Pair[9];
		final Pair<Integer, List<Position>>[] nonets = new Pair[9];

		for (int i = 0; i < 9; i++) {
			rows[i] = new Pair<>(45, new ArrayList<>(Arrays.asList(Cage.rowCages[i].getCells())));
			columns[i] = new Pair<>(45, new ArrayList<>(Arrays.asList(Cage.columnCages[i].getCells())));
			nonets[i] = new Pair<>(45, new ArrayList<>(Arrays.asList(Cage.nonetCages[i].getCells())));
		}

		/*
		 * check if a cage is fully contained in any region. if so, remove its target sum from remaining sum in region. also
		 * remove its positions from remaining positions in region.
		 */
		for (final var cage : cages) {
			for (int i = 0; i < 9; i++) {
				if (Cage.rowCages[i].containsCage(cage)) {
					rows[i] = rows[i].setAt0(rows[i].getValue0() - cage.getSum());

					final var temp = rows[i].getValue1();
					temp.removeAll(Arrays.asList(cage.getCells()));
					rows[i] = rows[i].setAt1(temp);
				}

				if (Cage.columnCages[i].containsCage(cage)) {
					columns[i] = columns[i].setAt0(columns[i].getValue0() - cage.getSum());

					final var temp = columns[i].getValue1();
					temp.removeAll(Arrays.asList(cage.getCells()));
					columns[i] = columns[i].setAt1(temp);
				}

				if (Cage.nonetCages[i].containsCage(cage)) {
					nonets[i] = nonets[i].setAt0(nonets[i].getValue0() - cage.getSum());

					final var temp = nonets[i].getValue1();
					temp.removeAll(Arrays.asList(cage.getCells()));
					nonets[i] = nonets[i].setAt1(temp);
				}
			}
		}

		/*
		 * check if rule of 45 is applicable
		 * 
		 * check if remaining positions already have a value. if so, remove value and position from remaining sum/positions in
		 * region.
		 */
		for (int i = 0; i < 9; i++) {
			if (rows[i].getValue1().size() > 0) {
				int remainingSum = rows[i].getValue0();
				final var cellList = rows[i].getValue1();

				final var itr = cellList.listIterator();

				while (itr.hasNext()) {
					final var cell = itr.next();
					final int value = grid[cell.y][cell.x].getValue();

					if (value != Cell.NO_VALUE) {
						remainingSum -= value;
						itr.remove();
					}
				}
				if (cellList.size() == 1) {
					// @formatter:off
                    return new Step("Rule of 45", 
                                    false,
                                    i, 
                                    null, 
                                    null, 
                                    cellList.toArray(new Position[0]), 
                                    new Effect("set", remainingSum)
                    );
                    // @formatter:on
				}
			}

			if (columns[i].getValue1().size() > 0) {
				int remainingSum = columns[i].getValue0();
				final var cellList = columns[i].getValue1();

				final var itr = cellList.listIterator();

				while (itr.hasNext()) {
					final var cell = itr.next();
					final int value = grid[cell.y][cell.x].getValue();

					if (value != Cell.NO_VALUE) {
						remainingSum -= value;
						itr.remove();
					}
				}
				if (cellList.size() == 1) {
					// @formatter:off
                    return new Step("Rule of 45", 
                                    false,
                                    null, 
                                    i, 
                                    null, 
                                    cellList.toArray(new Position[0]), 
                                    new Effect("set", remainingSum)
                    );
                    // @formatter:on
				}
			}

			if (nonets[i].getValue1().size() > 0) {
				int remainingSum = nonets[i].getValue0();
				final var cellList = nonets[i].getValue1();

				final var itr = cellList.listIterator();

				while (itr.hasNext()) {
					final var cell = itr.next();
					final int value = grid[cell.y][cell.x].getValue();

					if (value != Cell.NO_VALUE) {
						remainingSum -= value;
						itr.remove();
					}
				}
				if (cellList.size() == 1) {
					// @formatter:off
                    return new Step("Rule of 45", 
                                    false,
                                    null, 
                                    null, 
                                    i, 
                                    cellList.toArray(new Position[0]), 
                                    new Effect("set", remainingSum)
                    );
                    // @formatter:on
				}
			}
		}
		return null;
	}

	private static Step bruteforce(final Cage[] cages, final Cell[][] grid) {
		return null;
	}
}
