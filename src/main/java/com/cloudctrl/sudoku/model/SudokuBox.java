package com.cloudctrl.sudoku.model;

import java.util.Arrays;

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
        return Arrays.stream(cells).mapToInt(cell -> cell.x()).max().getAsInt();
    }

    public int maxY() {
        return Arrays.stream(cells).mapToInt(cell -> cell.y()).max().getAsInt();
    }
}