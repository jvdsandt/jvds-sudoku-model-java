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
        this.board = board;
        this.reset();
    }

    public void reset() {
        fixedCells = new HashMap<>();
    }

    public void fix(SudokuCell cell, int value) {
        if (fixedCells.containsKey(cell)) {
            if (fixedCells.get(cell) == value) {
                return;
            } else {
                throw new IllegalArgumentException("Cell already set to other value");
            }
        }
        if (!board.includes(cell)) {
            throw new IllegalArgumentException("Cell not part of board");
        }
        if (!board.canAdd(cell, value, fixedCells)) {
            throw new IllegalArgumentException("Cell creates invalid box");
        }
        fixedCells.put(cell, value);
    }

    public void fix(int xpos, int ypos, int value) {
        this.fix(new SudokuCell(xpos, ypos), value);
    }

    public SudokuGame newGame() {
        return new SudokuGame(board, fixedCells);
    }

    public void initFromArray(int[][] cells) {
        reset();
        for (int ypos = 0; ypos < cells.length; ypos++) {
            for (int xpos = 0; xpos < cells[ypos].length; xpos++) {
                if (cells[ypos][xpos] > 0) {
                    fix(xpos+1, ypos+1, cells[ypos][xpos]);
                }
            }
        }
    }

}
