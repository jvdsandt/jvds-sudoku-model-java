package com.cloudctrl.sudoku.model;

import java.util.Objects;

/**
 * Data class that represents a Move.
 */
public class Move implements Comparable<Move> {

    enum Reason {
        ONLY_OPTION, ONLY_PLACE, GUESS, UNKNOWN
    }

    private final Cell cell;
    private final int value;
    private final Reason reason;

    Move(Cell cell, int value, Reason r) {
        this.cell = cell;
        this.value = value;
        this.reason = r;
    }

    public Cell getCell() {
        return cell;
    }

    public int getValue() {
        return value;
    }

    public Reason getReason() {
        return reason;
    }
    
    public boolean canBeWrong() {
    	return reason != Reason.ONLY_OPTION && reason != Reason.ONLY_PLACE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move that = (Move) o;
        return Objects.equals(value, that.value) && Objects.equals(cell, that.cell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cell, value);
    }

    @Override
    public String toString() {
        return "SudokuMove{" +
                "cell=" + cell +
                ", value=" + value +
                ", reason=" + reason +
                '}';
    }

	@Override
	public int compareTo(Move o) {
		return cell.compareTo(o.cell);
	}
}
