package com.cloudctrl.sudoku.model;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBox {

    private String name;
    private SudokuCell[] cells;

    public SudokuBox(String name, SudokuCell[] cells) {
        this.name = name;
        this.cells = cells;
    }

    public int maxX() {
        return SudokuUtils.max(cells, (SudokuCell o1, SudokuCell o2) -> o1.x() - o2.x()).x();
    }

    public int maxY() {
        return SudokuUtils.max(cells, (SudokuCell o1, SudokuCell o2) -> o1.y() - o2.y()).y();
    }
}