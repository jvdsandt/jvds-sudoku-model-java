package com.cloudctrl.sudoku.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloudctrl.sudoku.utils.CollUtils;

public class AutoPlayer {

    private final SudokuGame game;

    private List<SudokuGameState> steps;
    private List<Move> badGuesses;

    public AutoPlayer(SudokuGame game) {
        super();
        this.game = game;
        this.steps = new ArrayList<>();
        this.steps.add(new SudokuInitialState(this.game));
        this.badGuesses = new ArrayList<>();
    }

    public SudokuGameState getCurrentStep() {
        return steps.get(steps.size()-1);
    }

    public boolean isSolved() {
        return getCurrentStep().isSolved();
    }

    public Map<Cell, Set<Integer>> getCurrentOptions() {
        return getCurrentStep().getOptionsPerCell();
    }

    public List<Move> getBadGuesses() {
		return badGuesses;
	}
    
    public boolean hasBadGuesses() {
    	return !badGuesses.isEmpty();
    }

	public SudokuGameActiveState doNextMove() {
        var move = getCurrentStep().getFirstSingleOptionMove();
        if (move != null) {
            return newMove(move);
        }
        move = getCurrentStep().getOnlyPlaceMove();
        if (move != null) {
            return newMove(move);
        }
        return newMove(getCurrentStep().takeGuess());
    }

    public SudokuGameActiveState newMove(Move move) {
        var newState = new SudokuGameActiveState(getCurrentStep(), move);
        if (newState.hasValidOptions()) {
        	steps.add(newState);
        	return newState;
        }
        return goBackAndMove();
    }

    public SudokuGameActiveState goBackAndMove() {
        var state = getCurrentStep();
        while (!state.getLastMove().canBeWrong()) {
            state = state.getPreviousState();
        }
        var invalidMove = state.getLastMove();
        badGuesses.add(invalidMove);
        
        SudokuGameState newState = new SudokuGameActiveState(state.getPreviousState(), state.getPreviousState().getLastMove(), invalidMove);
        this.steps.add(newState);
        return doNextMove();
    }
    
    public String toHistoryString() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 1; i < steps.size(); i++) {
    		var move = steps.get(i).getLastMove();
    		sb.append(steps.get(i).getSolvedCells().size()).append(" | ");
    		sb.append(move);
    		if (badGuesses.contains(move)) {
    			sb.append(" | BAD");
    		}
    		sb.append("\n");
    	}
    	return sb.toString();
    }
}
