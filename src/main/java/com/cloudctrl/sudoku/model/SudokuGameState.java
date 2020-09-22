package com.cloudctrl.sudoku.model;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by jan on 16-04-17.
 */
public abstract class SudokuGameState implements CellAccess {

    protected final Map<Cell, Set<Integer>> optionsPerCell;

    public SudokuGameState(Map<Cell, Set<Integer>> optionsPerCell) {
        super();
        this.optionsPerCell = optionsPerCell;
    }

    public Map<Cell, Set<Integer>> getOptionsPerCell() {
        return optionsPerCell;
    }

    public Set<Integer> getOptionsPerCell(Cell aCell) {
        return getOptionsPerCell().get(aCell);
    }

    public Map<Cell, Integer> solvedCells() {
        return Collections.emptyMap();
    }

    public abstract SudokuGame getGame();

    public abstract SudokuGameState getPreviousState();

    public int numberOfCellsToSolve() {
        return optionsPerCell.size();
    };

    public boolean isSolved() {
        return numberOfCellsToSolve() == 0;
    }

    public Board getBoard() {
        return getGame().getBoard();
    }

    public abstract int valueAt(Cell cell);

    public Move getLastMove() {
        throw new IllegalStateException("No lastMove available");
    }
}
