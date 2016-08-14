package com.cloudctrl.sudoku.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuGameBuilder {

    private SudokuBoard board;
    private Map<SudokuCell, Integer> fixedCells;

    public SudokuGameBuilder() {
        this(SudokuBoardBuilder.default9x9());
    }

    public SudokuGameBuilder(SudokuBoard board) {
        board = board;
        this.reset();
    }

    public void reset() {
        fixedCells = new HashMap<>();
    }

    public void fix(SudokuCell cell, int value) {
        fixedCells.put(cell, value);
    }

    public void fix(int xpos, int ypos, int value) {
        this.fix( new SudokuCell(xpos, ypos), value);
    }

}
