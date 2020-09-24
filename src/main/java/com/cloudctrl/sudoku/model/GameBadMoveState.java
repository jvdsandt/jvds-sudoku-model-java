package com.cloudctrl.sudoku.model;

import java.util.Map;
import java.util.Set;

import com.cloudctrl.sudoku.utils.CollUtils;

public class GameBadMoveState extends GameActiveState {
	
    protected final Move badMove;
    
    private static Map<Cell, Set<Integer>> withoutBadMove(Map<Cell, Set<Integer>> options, Move badMove) {
        var values = options.get(badMove.getCell());
        if (values == null || !values.contains(badMove.getValue())) {
        	throw new IllegalArgumentException("Cannot process bad move " + badMove);
        }
        // remove the bad move as a valid option
        options.put(badMove.getCell(), CollUtils.copyWithout(values, badMove.getValue()));
    	return options;
    }
	
    public GameBadMoveState(GameState prevState, Move badMove) {
        super(withoutBadMove(prevState.getOptionsPerCell(), badMove), prevState);
        this.badMove = badMove;
    }

	@Override
	public Move getLastMove() {
		return previousState.getLastMove();
	}
 
    @Override
    public Map<Cell, Integer> getSolvedCells() {
        return previousState.getSolvedCells();
    }

    @Override
    public boolean isBadMoveState() {
    	return true;
    }
    
	public Move getBadMove() {
		return badMove;
	}
}
