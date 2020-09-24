package com.cloudctrl.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloudctrl.sudoku.model.Move.Reason;

public class AutoPlayer {

    private final SudokuGame game;

    private List<GameState> steps;

    public AutoPlayer(SudokuGame game) {
        super();
        this.game = game;
        this.steps = new ArrayList<>();
        this.steps.add(new GameInitialState(this.game));
    }

    public GameState getCurrentStep() {
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
    
    public GameActiveState doManualMove(Cell c, int value) {
    	var move = new Move(c, value, Reason.MANUAL);
        var newState = new GameMoveState(getCurrentStep(), move);
       	steps.add(newState);
       	return newState;
    }

	public GameActiveState doAutoMove() {
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

    private GameActiveState processMove(Move move) {
        var newState = new GameMoveState(getCurrentStep(), move);
        if (newState.hasValidOptions()) {
        	steps.add(newState);
        	return newState;
        }
        return goBackAndMove();
    }

    private GameActiveState goBackAndMove() {
        var state = getCurrentStep();
        while (state.isBadMoveState() || !state.getLastMove().canBeWrong()) {
            state = state.getPreviousState();
            if (state.isInitialState()) {
            	throw new InvalidGameException("Unsolvable game");
            }
        }
        var invalidMove = state.getLastMove();
        
        GameState newState = new GameBadMoveState(state.getPreviousState(), invalidMove);
        this.steps.add(newState);
        return doAutoMove();
    }
    
    public String toHistoryString() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 1; i < steps.size(); i++) {
    		var move = steps.get(i).getLastMove();
    		sb.append(steps.get(i).getSolvedCells().size()).append(" | ");
    		sb.append(move);
    		sb.append("\n");
    	}
    	return sb.toString();
    }
}
