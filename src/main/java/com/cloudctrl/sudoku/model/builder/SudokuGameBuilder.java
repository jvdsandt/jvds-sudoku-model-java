package com.cloudctrl.sudoku.model.builder;

import java.util.HashMap;
import java.util.Map;

import com.cloudctrl.sudoku.model.Board;
import com.cloudctrl.sudoku.model.Cell;
import com.cloudctrl.sudoku.model.SudokuGame;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuGameBuilder {

    private Board board;
    private Map<Cell, Integer> fixedCells;

    public static SudokuGame newGameFromNumberLine(String numberLine) {
        Board board = SudokuBoardBuilder.default9x9();
        if (numberLine.length() < board.maxX() * board.maxY()) {
            throw new IllegalArgumentException("Not enough numbers provided");
        }
        SudokuGameBuilder builder = new SudokuGameBuilder(board);
        builder.initFromNumberLine(numberLine);
        return builder.newGame();
    }

    public SudokuGameBuilder() {
        this(SudokuBoardBuilder.default9x9());
    }

    public SudokuGameBuilder(Board board) {
        this.board = board;
        this.reset();
    }

    public void reset() {
        fixedCells = new HashMap<>();
    }

    public void fix(Cell cell, int value) {
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
        this.fix(new Cell(xpos, ypos), value);
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

    public void initFromNumberLine(String numberLine) {
        for (int y = 0; y < board.maxY(); y++) {
            for (int x = 0; x < board.maxX(); x++) {
                char value = numberLine.charAt((y * board.maxX()) + x);
                if (value < '0' || value > '9') {
                    throw new IllegalArgumentException("Invalid cell value");
                }
                if (value != '0') {
                    fix(x + 1, y + 1, Character.digit(value, 10));
                }
            }
        }
    }

}
