package com.cloudctrl.sudoku.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SudokuInitialState extends SudokuGameState {

    private final SudokuGame game;

    public SudokuInitialState(SudokuGame game) {
        super(calcOptionsPerCell(game));
        this.game = game;
    }

    private static Map<Cell, Set<Integer>> calcOptionsPerCell(SudokuGame game) {
        Map<Cell, Set<Integer>> map = new HashMap<>();
        game.forOpenCells((eachCell) -> {
            map.put(eachCell, game.getBoard().possibleValues(eachCell, game));
        });
        return map;
    }

    @Override
    public SudokuGame getGame() {
        return this.game;
    }

    @Override
    public SudokuGameState getPreviousState() {
        throw new IllegalStateException("No previous state");
    }
    @Override
    public int valueAt(Cell cell) {
        return game.valueAt(cell);
    }
}
