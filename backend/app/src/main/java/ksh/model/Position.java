package ksh.model;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to have better Typing for Positions in a Sudoku-Grid. {x: 0, y: 0} denotes the top left corner and
 * {x: 8, y: 8} denotes the bottom right corner.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {
    /** 9x9 array of Positions corresponding to indices of array */
    public static Position[][] allPositions = new Position[9][9];
    static {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                allPositions[y][x] = new Position(x, y);
            }
        }
    }

    /** horizontal position in range [0, 8] */
    public final int x;
    /** vertical position in range [0, 8] */
    public final int y;

    /**
     * Basic Constructor
     * 
     * @param x horizontal index of Position
     * @param y vertical index of Position
     * @throws IllegalArgumentException when indices are outside of expected range
     */
    @JsonCreator
    public Position(@JsonProperty("x") final int x, @JsonProperty("y") final int y) throws IllegalArgumentException {
        if (x < 0 || x >= 9 || y < 0 || y >= 9) {
            final String errorMessage = MessageFormat.format("x and y must be in [0, 8]; got x = {0}, y = {1}", x, y);
            throw new IllegalArgumentException(errorMessage);
        }

        this.x = x;
        this.y = y;
    }

    /**
     * Check whether this Position is directly adjacent to another Position. excluding diagonal adjacency
     * 
     * @param other Position to check against
     * @return whether this Position is adjacent to other
     */
    public boolean adjacentTo(final Position other) {
        final int absoluteDifference = Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
        return (absoluteDifference == 1);
    }

    /**
     * Check whether this Position is directly adjacent to a list of other Positions. excluding diagonal adjacency
     * 
     * @param others Positions to check against
     * @return whether this Position is adjacent to other
     */
    public boolean adjacentTo(final Position[] others) {
        for (final var other : others) {
            if (this.adjacentTo(other)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(final Object other) {
        if (other != null && other instanceof final Position otherPosition) return (this.x == otherPosition.x && this.y == otherPosition.y);
        return false;
    }

    @Override
    public String toString() {
        return String.format("{x: %d, y: %d}", this.x, this.y);
    }

    /**
     * gets x-coordinate of this position
     * 
     * @return x-coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * gets y-coordinate of this position
     * 
     * @return y-coordinate
     */
    public int getY() {
        return this.y;
    }
}
