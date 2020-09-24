package com.cloudctrl.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloudctrl.sudoku.model.Move.Reason;

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
    
    public boolean isMovePossible(Cell c, int value) {
    	return getCurrentStep().isPossibleMove(c, value);
    }
    
    public SudokuGameActiveState doManualMove(Cell c, int value) {
    	var move = new Move(c, value, Reason.UNKNOWN);
        var newState = new SudokuGameActiveState(getCurrentStep(), move);
       	steps.add(newState);
       	return newState;
    }

    public List<Move> getBadGuesses() {
		return badGuesses;
	}
    
    public boolean hasBadGuesses() {
    	return !badGuesses.isEmpty();
    }

	public SudokuGameActiveState doAutoMove() {
        var move = getCurrentStep().getFirstSingleOptionMove();
        if (move != null) {
            return processMove(move);
        }
        move = getCurrentStep().getOnlyPlaceMove();
        if (move != null) {
            return processMove(move);
        }
        return processMove(getCurrentStep().takeGuess());
    }
	
	public void solve() {
		while (!isSolved()) {
			doAutoMove();
		}
	}

    private SudokuGameActiveState processMove(Move move) {
        var newState = new SudokuGameActiveState(getCurrentStep(), move);
        if (newState.hasValidOptions()) {
        	steps.add(newState);
        	return newState;
        }
        return goBackAndMove();
    }

    private SudokuGameActiveState goBackAndMove() {
        var state = getCurrentStep();
        while (!state.getLastMove().canBeWrong()) {
            state = state.getPreviousState();
        }
        while (state.getLastMove().equals(state.getPreviousState().getLastMove())) {
        	state = state.getPreviousState();
        }
        var invalidMove = state.getLastMove();
        badGuesses.add(invalidMove);
        
        SudokuGameState newState = new SudokuGameActiveState(state.getPreviousState(), state.getPreviousState().getLastMove(), invalidMove);
        this.steps.add(newState);
        return doAutoMove();
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
