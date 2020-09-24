package com.cloudctrl.sudoku.model;

import java.util.Map;
import java.util.Set;

/**
 * Created by jan on 16-04-17.
 */
public abstract class GameActiveState extends GameState {

    protected final SudokuGame game;
    protected final GameState previousState;

    protected GameActiveState(Map<Cell, Set<Integer>> options, GameState prevState) {
        super(options);
        this.game = prevState.getGame();
        this.previousState = prevState;
    }

    @Override
    public SudokuGame getGame() {
        return game;
    }

    @Override
    public GameState getPreviousState() {
        return this.previousState;
    }
}
