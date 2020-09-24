package com.cloudctrl.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jan on 16-04-17.
 */
public abstract class SudokuGameState implements CellAccess {

    protected final Map<Cell, Set<Integer>> optionsPerCell;

    public SudokuGameState(Map<Cell, Set<Integer>> optionsPerCell) {
        super();
        this.optionsPerCell = optionsPerCell;
    }

    public abstract SudokuGame getGame();

    public abstract SudokuGameState getPreviousState();

    public abstract int valueAt(Cell cell);

    public abstract Move getLastMove();
    
    public Map<Cell, Integer> getSolvedCells() {
    	return Collections.emptyMap();
    }

    public Map<Cell, Set<Integer>> getOptionsPerCell() {
        return optionsPerCell;
    }

    public Set<Integer> getOptionsPerCell(Cell aCell) {
        return getOptionsPerCell().get(aCell);
    }

    public int numberOfCellsToSolve() {
        return optionsPerCell.size();
    };

    public boolean isSolved() {
        return numberOfCellsToSolve() == 0;
    }
    
    public boolean isPossibleMove(Cell c, int value) {
    	return optionsPerCell.containsKey(c) &&
    			optionsPerCell.get(c).contains(value);
    }

    public Board getBoard() {
        return getGame().getBoard();
    }

    /**
     * All open cells should have at least one option. Otherwise
     * we are in an invalid state!
     */
    public boolean hasValidOptions() {
        return optionsPerCell.values().stream().allMatch(s -> !s.isEmpty());
    }

    /**
     * If there is a cell that can only contain a single value
     * than answer a move to fill this cell. Otherwise answer nil.
     */
    public Move getFirstSingleOptionMove() {
        return optionsPerCell.entrySet().stream()
                .filter(e -> e.getValue().size() == 1)
                .map(e -> new Move(e.getKey(), e.getValue().iterator().next(), Move.Reason.ONLY_OPTION))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * If there is a cell which is the only place where a value can be,
     * than answer a move to fill this cell. Otherwise answer nil.
     */
    public Move getOnlyPlaceMove() {
        for (var b : getBoard().getBoxes()) {
            var move = b.findMove(optionsPerCell);
            if (move != null) {
                return move;
            }
        }
        return null;
    }
    
    public List<Move> getPossibleMoves() {
    	var moves = optionsPerCell.entrySet().stream()
                .filter(e -> e.getValue().size() == 1)
                .map(e -> new Move(e.getKey(), e.getValue().iterator().next(), Move.Reason.ONLY_OPTION))
                .collect(Collectors.toList());
        for (var b : getBoard().getBoxes()) {
        	moves.addAll(b.findMoves(optionsPerCell));
        }
        // remove duplicates
        var cells = new HashSet<Cell>();
        var uniqueMoves = new ArrayList<Move>();
        for (Move m : moves) {
        	if (!cells.contains(m.getCell())) {
        		uniqueMoves.add(m);
        		cells.add(m.getCell());
        	}
        }
        Collections.sort(uniqueMoves);
        return uniqueMoves;
    }
    
    public Move takeGuess() {
        Cell cell = null;
        Set<Integer> values = null;
        for (var entry : optionsPerCell.entrySet()) {
            if (!entry.getValue().isEmpty() && (values == null || values.size() > entry.getValue().size())) {
                cell = entry.getKey();
                values = entry.getValue();
            }
        }
        return new Move(cell, values.iterator().next(), Move.Reason.GUESS);
    }
    
    @Override
    public String toString() {
        var sb = new StringBuilder(100);
        var board = getBoard();
        for (int y = 1; y <= board.maxY(); y++) {
            for (int x = 1; x <= board.maxX(); x++) {
                var c = new Cell(x, y);
                if (getGame().valueAt(c) > 0) {
                    sb.append("-").append(getGame().valueAt(c)).append("-");
                } else if (getSolvedCells().containsKey(c)) {
                    sb.append("<").append(this.valueAt(c)).append(">");
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
    
    public String asNumberLine() {
        var sb = new StringBuilder(100);
        var board = getBoard();
        for (int y = 1; y <= board.maxY(); y++) {
            for (int x = 1; x <= board.maxX(); x++) {
            	var v = valueAt(x, y);
            	sb.append(v > 0 ? v : 0);
            }
        }
        return sb.toString();
    }
    
    
}
