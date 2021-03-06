package com.cloudctrl.sudoku.model;

/**
 * Created by Jan on 14-8-2016.
 */
public class Cell implements Comparable<Cell> {

    private final int x;
    private final int y;

    public Cell(int theX, int theY) {
        if (theX < 1 || theY < 1){
            throw new IllegalArgumentException("Invalid cell");
        }
        x = theX;
        y = theY;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return "SudokuCell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell that = (Cell) o;

        if (x != that.x) return false;
        return y == that.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

	@Override
	public int compareTo(Cell o) {
		return y == o.y ? x - o.x : y - o.y;
	}
}
