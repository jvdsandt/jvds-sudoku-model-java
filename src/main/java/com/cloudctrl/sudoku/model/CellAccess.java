package com.cloudctrl.sudoku.model;

import java.util.function.Consumer;

public interface CellAccess {

    int valueAt(Cell cell);

    default int valueAt(int xpos, int ypos) {
        return valueAt(new Cell(xpos, ypos));
    }

    default void valueIfKnown(Cell aCell, Consumer<Integer> action) {
        int val = valueAt(aCell);
        if (val != -1) {
            action.accept(val);
        }
    }
}
