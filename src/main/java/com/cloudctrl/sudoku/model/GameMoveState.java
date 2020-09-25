package com.cloudctrl.sudoku.model;

import java.util.Map;

import com.cloudctrl.sudoku.utils.CollUtils;

public class GameMoveState extends GameActiveState {
	
    protected final Move lastMove;
    protected final Map<Cell, Integer> solvedCells;
	
    public GameMoveState(GameState prevState, Move move) {
        super(prevState.getBoard().processMove(prevState.getOptionsPerCell(), move), prevState);
       	if (!prevState.getOptionsPerCell().containsKey(move.getCell())) {
       		throw new IllegalArgumentException("Cell not open, invalid move: " + move);
       	}
       	if (!prevState.getOptions(move.getCell()).contains(move.getValue())) {
       		throw new IllegalArgumentException("Value not possible, invalid move: " + move);
       	}
        this.lastMove = move;
        this.solvedCells = CollUtils.copyWith(prevState.getSolvedCells(), move.getCell(), move.getValue());
    }
    
    @Override
    public Move getLastMove() {
        return this.lastMove;
    }
    
    @Override
    public Map<Cell, Integer> getSolvedCells() {
        return this.solvedCells;
    }

}
