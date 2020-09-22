package com.cloudctrl.sudoku.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jan on 16-04-17.
 */
public class SudokuGameActiveState extends SudokuGameState {

    protected final SudokuGame game;
    protected final SudokuGameState previousState;
    protected final Map<Cell, Integer> solvedCells;
    protected final Move lastMove;

    SudokuGameActiveState(Map<Cell, Set<Integer>> options, SudokuGameState prevState, Move move) {
        super(options);
        this.game = prevState.getGame();
        this.previousState = prevState;
        var newSolved = new HashMap<Cell, Integer>(prevState.solvedCells());
        newSolved.put(move.getCell(), move.getValue());
        this.solvedCells = Collections.unmodifiableMap(newSolved);
        this.lastMove = move;
    }

    public SudokuGameActiveState(SudokuGameState prevState, Move move) {
        this(prevState.getBoard().processMove(prevState.getOptionsPerCell(), move), prevState, move);
    }

    @Override
    public Map<Cell, Integer> solvedCells() {
        return this.solvedCells;
    }

    @Override
    public SudokuGame getGame() {
        return game;
    }

    @Override
    public SudokuGameState getPreviousState() {
        return this.previousState;
    }

    @Override
    public int valueAt(Cell cell) {
        int val = solvedCells.getOrDefault(cell, -1);
        if (val == -1) {
            val = game.valueAt(cell);
        }
        return val;
    }

    public Move getLastMove() {
        return this.lastMove;
    }

    public String toString() {
        var sb = new StringBuilder(100);
        var board = getBoard();
        for (int y = 1; y <= board.maxY(); y++) {
            for (int x = 1; x <= board.maxX(); x++) {
                var c = new Cell(x, y);
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
