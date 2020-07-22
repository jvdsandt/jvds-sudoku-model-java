package com.cloudctrl.sudoku.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jan on 16-04-17.
 */
public class SudokuGamePlay extends SudokuGameBase {

    protected SudokuGame game;
    protected SudokuGameBase previousPlay;
    protected Map<SudokuCell, Integer> solvedCells;
    protected SudokuMove lastMove;

    protected SudokuGamePlay(SudokuGameBase prevGame, SudokuMove move, Map<SudokuCell, Set<Integer>> options) {
        this.game = prevGame.getGame();
        this.previousPlay = prevGame;
        this.solvedCells = new HashMap<SudokuCell, Integer>(prevGame.solvedCells());
        this.solvedCells.put(move.getCell(), move.getValue());
        this.solvedCells = Map.copyOf(this.solvedCells);
        this.optionsPerCell = options;
    }

    public SudokuGame getGame() {
        return game;
    }

    @Override
    public int valueAt(SudokuCell cell) {
        int val = solvedCells.getOrDefault(cell, -1);
        if (val == -1) {
            val = game.valueAt(cell);
        }
        return val;
    }
}
