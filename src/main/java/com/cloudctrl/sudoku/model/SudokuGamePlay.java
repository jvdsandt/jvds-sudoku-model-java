package com.cloudctrl.sudoku.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jan on 16-04-17.
 */
public class SudokuGamePlay extends SudokuGameBase {

    protected final SudokuGame game;
    protected final SudokuGameBase previousPlay;
    protected final Map<SudokuCell, Integer> solvedCells;
    protected final SudokuMove lastMove;

    protected SudokuGamePlay(SudokuGameBase prevGame, SudokuMove move, Map<SudokuCell, Set<Integer>> options) {
        this.game = prevGame.getGame();
        this.previousPlay = prevGame;
        var newSolved = new HashMap<SudokuCell, Integer>(prevGame.solvedCells());
        newSolved.put(move.getCell(), move.getValue());
        this.solvedCells = Collections.unmodifiableMap(newSolved);
        this.optionsPerCell = options;
        this.lastMove = move;
    }

    @Override
    public Map<SudokuCell, Integer> solvedCells() {
        return this.solvedCells;
    }

    @Override
    public SudokuGame getGame() {
        return game;
    }

    public int numberOfCellsToSolve() {
        return game.numberOfCellsToSolve() - solvedCells.size();
    }

    @Override
    public int valueAt(SudokuCell cell) {
        int val = solvedCells.getOrDefault(cell, -1);
        if (val == -1) {
            val = game.valueAt(cell);
        }
        return val;
    }

    public String toString() {
        var sb = new StringBuilder(100);
        var board = getBoard();
        for (int y = 1; y <= board.maxY(); y++) {
            for (int x = 1; x <= board.maxX(); x++) {
                var c = new SudokuCell(x, y);
                if (game.valueAt(c) > 0) {
                    sb.append("[").append(game.valueAt(c)).append("]");
                } else if (solvedCells.containsKey(c)) {
                    sb.append("{").append(this.valueAt(c)).append("}");
                } else {
                     sb.append(getOptionsPerCell().get(c));
                }
                if (x == board.maxX()) {
                    sb.append("\n");
                } else {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

}
