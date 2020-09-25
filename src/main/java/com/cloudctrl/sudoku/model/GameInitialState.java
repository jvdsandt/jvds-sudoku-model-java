package com.cloudctrl.sudoku.model;

public class GameInitialState extends GameState {

    private final SudokuGame game;

    public GameInitialState(SudokuGame game) {
        super(game.calcOptionsPerCell(game));
        this.game = game;
    }

    @Override
    public SudokuGame getGame() {
        return this.game;
    }

    @Override
    public GameState getPreviousState() {
        throw new IllegalStateException("No previous state");
    }
    
    @Override
    public int valueAt(Cell cell) {
        return game.valueAt(cell);
    }

    @Override
    public Move getLastMove() {
        throw new IllegalStateException("No lastMove available");
    }
    
    @Override
    public boolean isInitialState() {
    	return true;
    }
}
