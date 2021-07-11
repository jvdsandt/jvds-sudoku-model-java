package com.cloudctrl.sudoku.model;

import java.util.List;
import java.util.stream.Collectors;

import com.cloudctrl.sudoku.model.Move.Reason;

public class AutoPlayer2 {

    private final SudokuGame game;

    public AutoPlayer2(SudokuGame game) {
        super();
        this.game = game;
    }

    public GameState solve() {
		var gameState = new AutoplayPath(new GameInitialState(game)).getSolvedState();
		if (gameState == null || !gameState.isSolved()) {
		    throw new InvalidGameException("Invalid game");
        }
		return gameState;
	}

	static class AutoplayPath {

        private GameState currentState;
        private List<AutoplayPath> possiblePaths = null;

        AutoplayPath(GameState step) {
            super();
            this.currentState = step;
            while (!isSolved() && !isInvalid()) {
                doAutoMove();
            }
        }

        AutoplayPath(GameState step, Move nextMove) {
            this(new GameMoveState(step, nextMove));
        }

        boolean isSolved() {
            return currentState.isSolved() ||
                    (possiblePaths != null && possiblePaths.stream().anyMatch(t -> t.isSolved()));
        }

        boolean isInvalid() {
            return !currentState.hasValidOptions() ||
                    (possiblePaths != null && possiblePaths.stream().allMatch(t -> t.isInvalid()));
        }

        GameState getSolvedState() {
            if (possiblePaths != null) {
                for (var t : possiblePaths) {
                    var state = t.getSolvedState();
                    if (state != null) {
                        return state;
                    }
                }
            }
            return currentState.isSolved() ? currentState : null;
        }

        private void doAutoMove() {
            var cell = currentState.firstCellWithFewestValues();
            var values = currentState.getOptions(cell);
            if (values.size() == 1) {
                processMove(new Move(cell, values.iterator().next(), Reason.ONLY_OPTION));
                return;
            }
            var move = currentState.getOnlyPlaceMove();
            if (move != null) {
                processMove(move);
                return;
            }
            possiblePaths = values.stream()
                    .map(v -> new AutoplayPath(currentState, new Move(cell, v, Reason.GUESS)))
                    .collect(Collectors.toList());
        }

        private void processMove(Move move) {
            currentState = new GameMoveState(currentState, move);
        }
    }
}
