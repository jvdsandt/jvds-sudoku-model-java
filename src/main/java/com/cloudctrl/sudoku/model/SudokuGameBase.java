package com.cloudctrl.sudoku.model;

import java.util.Collections;
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

    public Map<SudokuCell, Integer> solvedCells() {
        return Collections.EMPTY_MAP;
    }

    public abstract SudokuGame getGame();

    public SudokuBoard getBoard() { return getGame().getBoard(); }

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

    public SudokuMove getFirstSingleOption() {
        for(SudokuCell eachCell : getOptionsPerCell().keySet()) {
            Set<Integer> values = getOptionsPerCell().get(eachCell);
            if (values.size() == 1) {
                return new SudokuMove(eachCell, values.iterator().next());
            }
        }
        return null;
    }

}
