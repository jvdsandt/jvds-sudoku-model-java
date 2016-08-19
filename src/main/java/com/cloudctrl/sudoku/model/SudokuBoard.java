package com.cloudctrl.sudoku.model;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoard {

    private SudokuBox[] boxes;
    private int maxX;
    private int maxY;

    public SudokuBoard(SudokuBox[] boxes) {
        this.boxes = boxes;
        maxX = Arrays.stream(boxes).mapToInt(b -> b.maxX()).max().getAsInt();
        maxY = Arrays.stream(boxes).mapToInt(b -> b.maxY()).max().getAsInt();
    }

    public SudokuBox[] getBoxes() {
        return boxes;
    }

    public boolean canAdd(SudokuCell cell, int value, Map<SudokuCell, Integer> fixedCells) {
        for(int i = 0; i < boxes.length; i++) {
           if (!boxes[i].canAdd(cell, value, fixedCells)) {
               return false;
           }
        }
        return true;
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }
}
