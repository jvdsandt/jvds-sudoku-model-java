package com.cloudctrl.sudoku.model;

import java.util.Map;
import java.util.Set;

import com.cloudctrl.sudoku.utils.CollUtils;

public class GameBadMoveState extends GameActiveState {
	
    protected final Move badMove;
    
    private static Map<Cell, Set<Integer>> withoutBadMove(Map<Cell, Set<Integer>> options, Move badMove) {
        var values = options.get(badMove.cell());
        if (values == null || !values.contains(badMove.value())) {
        	throw new IllegalArgumentException("Cannot process bad move " + badMove);
        }
        // remove the bad move as a valid option
        options.put(badMove.cell(), CollUtils.copyWithout(values, badMove.value()));
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
