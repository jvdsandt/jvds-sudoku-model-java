package com.cloudctrl.sudoku.model;

import java.util.Map;
import java.util.Set;

import com.cloudctrl.sudoku.utils.CollUtils;

/**
 * Created by jan on 16-04-17.
 */
public class SudokuGameActiveState extends SudokuGameState {

    protected final SudokuGame game;
    protected final SudokuGameState previousState;
    protected final Map<Cell, Integer> solvedCells;
    protected final Move lastMove;

    private SudokuGameActiveState(Map<Cell, Set<Integer>> options, SudokuGameState prevState, Move move) {
        super(options);
        if (prevState.valueAt(move.getCell()) != move.getValue()) {
        	if (!prevState.getOptionsPerCell().containsKey(move.getCell())) {
        		throw new IllegalArgumentException("Cell not open, invalid move: " + move);
        	}
        	if (!prevState.getOptionsPerCell(move.getCell()).contains(move.getValue())) {
        		throw new IllegalArgumentException("Value not possible, invalid move: " + move);
        	}
        }
        this.game = prevState.getGame();
        this.previousState = prevState;
        this.solvedCells = CollUtils.copyWith(prevState.getSolvedCells(), move.getCell(), move.getValue());
        this.lastMove = move;
    }

    public SudokuGameActiveState(SudokuGameState prevState, Move move) {
        this(prevState.getBoard().processMove(prevState.getOptionsPerCell(), move), prevState, move);
    }
    
    public SudokuGameActiveState(SudokuGameState prevState, Move move, Move badMove) {
        this(prevState, move);
        var values = optionsPerCell.get(badMove.getCell());
        if (values == null || !values.contains(badMove.getValue())) {
        	throw new IllegalArgumentException("Cannot process bad move " + badMove);
        }
        // remove the bad move as a valid option
        optionsPerCell.put(badMove.getCell(), CollUtils.copyWithout(values, badMove.getValue()));
    }
    
    @Override
    public Map<Cell, Integer> getSolvedCells() {
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

    @Override
    public Move getLastMove() {
        return this.lastMove;
    }

}
