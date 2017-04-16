package com.cloudctrl.sudoku.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by jan on 16-04-17.
 */
public abstract class SudokuGameBase {

    protected Map<SudokuCell, Set<Integer>>  optionsPerCell;

    public Map<SudokuCell, Set<Integer>> getOptionsPerCell() {
        return optionsPerCell;
    }

    public Set<Integer> getOptionsPerCell(SudokuCell aCell) {
        return getOptionsPerCell().get(aCell);
    }

    public abstract int valueAt(SudokuCell cell);

    public int valueAt(int xpos, int ypos) {
        return valueAt(new SudokuCell(xpos, ypos));
    }

    public void valueIfKnown(SudokuCell aCell, Consumer<Integer> action) {
        int val = valueAt(aCell);
        if (val != -1) {
            action.accept(val);
        }
    }

}
