package com.cloudctrl.sudoku.model;

public record Cell(int x, int y) implements Comparable<Cell> {

	@Override
	public int compareTo(Cell o) {
		return y == o.y ? x - o.x : y - o.y;
	}
}
