package com.cloudctrl.sudoku.model;

import java.util.Map;

/**
 * Created by jan on 16-08-16.
 */
public class SudokuGame {

    private SudokuBoard board;
    private Map<SudokuCell, Integer> fixedCells;

    public SudokuGame(SudokuBoard board, Map<SudokuCell, Integer> fixedCells) {
        this.board = board;
        this.fixedCells = fixedCells;
    }

    public int valueAt(SudokuCell cell) {
        return fixedCells.getOrDefault(cell, -1);
    }
    public int valueAt(int xpos, int ypos) {
        return valueAt(new SudokuCell(xpos, ypos));
    }
}
