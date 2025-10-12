package com.cloudctrl.sudoku.model;

/**
 * Data class that represents a Move.
 */
public record Move(Cell cell, int value, Reason reason) implements Comparable<Move> {

    enum Reason {
        ONLY_OPTION, ONLY_PLACE, GUESS, MANUAL
    }

    public boolean canBeWrong() {
    	return reason != Reason.ONLY_OPTION && reason != Reason.ONLY_PLACE;
    }

	@Override
	public int compareTo(Move o) {
		return cell.compareTo(o.cell);
	}
}
