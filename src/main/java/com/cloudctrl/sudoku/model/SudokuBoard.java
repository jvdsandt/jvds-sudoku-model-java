package com.cloudctrl.sudoku.model;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoard {

    private SudokuBox[] boxes;
    private int maxX;
    private int maxY;

    public SudokuBoard(SudokuBox[] boxes) {
        this.boxes = boxes;
        maxX = SudokuUtils.max(boxes, (SudokuBox o1, SudokuBox o2) -> o1.maxX() - o2.maxX()).maxX();
        maxY = SudokuUtils.max(boxes, (SudokuBox o1, SudokuBox o2) -> o1.maxY() - o2.maxY()).maxY();
    }

    public SudokuBox[] getBoxes() {
        return boxes;
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }
}
