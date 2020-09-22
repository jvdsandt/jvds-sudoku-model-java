package com.cloudctrl.sudoku.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloudctrl.sudoku.utils.CollUtils;

public class AutoPlayer {

    private SudokuGame game;

    private List<SudokuGameState> steps;

    public AutoPlayer(SudokuGame game) {
        super();
        this.game = game;
        this.steps = new ArrayList<>();
        this.steps.add(new SudokuInitialState(game));
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

    private Move getFirstSingleOption(Map<Cell, Set<Integer>> options) {
        return options.entrySet().stream()
                .filter(e -> e.getValue().size() == 1)
                .map(e -> new Move(e.getKey(), e.getValue().iterator().next(), Move.Reason.ONLY_OPTION))
                .findFirst()
                .orElse(null);
    }

    public SudokuGameActiveState doNextMove() {
        var options = getCurrentOptions();
        var move = getFirstSingleOption(options);
        if (move != null) {
            return newMove(move);
        }
        for (var b : game.getBoard().getBoxes()) {
            var bmove = b.findMove(options);
            if (bmove != null) {
                return newMove(bmove);
            }
        }
        return newMove(takeGuess());
    }

    public SudokuGameActiveState newMove(Move move) {
        var options = getCurrentStep().getOptionsPerCell();
        var newOptions = game.getBoard().processMove(options, move);
        if (newOptions.entrySet().stream().anyMatch(e -> e.getValue().isEmpty())) {
            return goBackAndMove();
        }
        var newState = new SudokuGameActiveState(newOptions, getCurrentStep(), move);
        steps.add(newState);
        return newState;
    }

    private Move takeGuess() {
        Cell cell = null;
        Set<Integer> values = null;
        for (var entry : getCurrentStep().getOptionsPerCell().entrySet()) {
            if (values == null || values.size() > entry.getValue().size()) {
                cell = entry.getKey();
                values = entry.getValue();
            }
        }
        return new Move(cell, values.iterator().next(), Move.Reason.GUESS);
    }

    public SudokuGameActiveState goBackAndMove() {
        var state = getCurrentStep();
        while (state.getLastMove().getReason() != Move.Reason.GUESS) {
            state = state.getPreviousState();
        }
        var invalidMove = state.getLastMove();
        var newOptions = new HashMap<>(state.getPreviousState().getOptionsPerCell());
        var newValues = newOptions.get(invalidMove.getCell());
        newOptions.put(invalidMove.getCell(), CollUtils.copyWithout(newValues, invalidMove.getValue()));
        SudokuGameState newState = new SudokuGameActiveState(newOptions, getCurrentStep(), state.getPreviousState().getLastMove());
        this.steps.add(newState);
        return doNextMove();
    }
}
