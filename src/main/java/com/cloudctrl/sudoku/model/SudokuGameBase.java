package com.cloudctrl.sudoku.model;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by jan on 16-04-17.
 */
public abstract class SudokuGameBase {

    protected Map<SudokuCell, Set<Integer>> optionsPerCell;

    public Map<SudokuCell, Set<Integer>> getOptionsPerCell() {
        return optionsPerCell;
    }

    public Set<Integer> getOptionsPerCell(SudokuCell aCell) {
        return getOptionsPerCell().get(aCell);
    }

    public Map<SudokuCell, Integer> solvedCells() {
        return Collections.emptyMap();
    }

    public abstract SudokuGame getGame();

    public abstract int numberOfCellsToSolve();

    public boolean isSolved() {
        return numberOfCellsToSolve() == 0;
    }

    public SudokuBoard getBoard() {
        return getGame().getBoard();
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

    public SudokuMove getFirstSingleOption() {
        return getOptionsPerCell().entrySet().stream()
                .filter(e -> e.getValue().size() == 1)
                .map(e -> new SudokuMove(e.getKey(), e.getValue().iterator().next(), SudokuMove.Reason.ONLY_OPTION))
                .findFirst()
                .orElse(null);
    }

    public SudokuGamePlay doNextMove() {
        var move = getFirstSingleOption();
        if (move != null) {
            return newMove(move);
        }
        for (var b : getBoard().getBoxes()) {
            var bmove = b.findMove(getOptionsPerCell());
            if (bmove != null) {
                return newMove(bmove);
            }
        }
        return newMove(takeGuess());
    }

    public SudokuGamePlay newMove(SudokuMove move) {
        var newOptions = getBoard().processMove(getOptionsPerCell(), move);
        if (newOptions.entrySet().stream().anyMatch(e -> e.getValue().isEmpty())) {
            return goBackAndMove();
        }
        return new SudokuAutoGame(this, move, newOptions);
    }

    private SudokuMove takeGuess() {
        SudokuCell cell = null;
        Set<Integer> values = null;
        for (var entry : getOptionsPerCell().entrySet()) {
            if (values == null || values.size() > entry.getValue().size()) {
                cell = entry.getKey();
                values = entry.getValue();
            }
        }
        return new SudokuMove(cell, values.iterator().next(), SudokuMove.Reason.GUESS);
    }

    public SudokuGamePlay goBackAndMove() {
        throw new IllegalStateException("Cannot go back");
    }
}
