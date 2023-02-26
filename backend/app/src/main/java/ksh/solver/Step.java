package ksh.solver;

import ksh.model.Position;

/**
 * Message format for a Hint
 * 
 * @param strategy which startegy to apply
 * @param error    error found in current grid?
 * @param row      index of row used in strategy: int or null
 * @param column   index of column used in strategy: int or null
 * @param nonet    index of nonet used in strategy: int or null
 * @param cells    positions of cells affected by strategy
 * @param effect   effect of strategy
 */
public record Step(String strategy, Boolean error, Integer row, Integer column, Integer nonet, Position[] cells, Effect effect) {
}

/**
 * part of Step
 * 
 * @param type   type of hint: either "set" or "eliminate" // TODO: check this value
 * @param values values of hint: either int, int[] or int[][]
 */
record Effect(String type, Object values) {
}