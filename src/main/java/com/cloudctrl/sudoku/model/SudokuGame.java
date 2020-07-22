package com.cloudctrl.sudoku.model;

import com.cloudctrl.sudoku.model.builder.SudokuGameBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by jan on 16-08-16.
 */
public class SudokuGame extends SudokuGameBase {

    private final SudokuBoard board;
    private final Map<SudokuCell, Integer> fixedCells;

    public static SudokuGame fromArray(int[][] cells) {
        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.initFromArray(cells);
        return builder.newGame();
    }

    public SudokuGame(SudokuBoard board, Map<SudokuCell, Integer> fixedCells) {
        this.board = board;
        this.fixedCells = Map.copyOf(fixedCells);
    }

    @Override
    public SudokuGame getGame() { return this; }

    @Override
    public SudokuBoard getBoard() { return board; }

    public int valueAt(SudokuCell cell) {
        return fixedCells.getOrDefault(cell, -1);
    }

    public Map<SudokuCell, Integer> valuesFor(Collection<SudokuCell> cells) {
        var map = new HashMap<SudokuCell, Integer>();
        cells.stream().forEach((c) -> {
            int value = valueAt(c);
            if (value != -1) {
                map.put(c, value);
            }
        });
        return map;
    }

    public Map<SudokuCell, Set<Integer>> findOpenCellValues() {
        var cellOptions = new HashMap<SudokuCell, Set<Integer>>();
        board.forBoxes((eachBox) -> {
            Map<SudokuCell, Integer> filledCells = valuesFor(eachBox.getCells());
            Set<Integer> openValues = new HashSet<>(board.allValues());
            openValues.removeAll(filledCells.values());
            eachBox.forCells(filledCells.keySet(), (eachOpenCell) -> {
                Set<Integer> possibleValues = new HashSet<>(openValues);
                possibleValues.retainAll(cellOptions.getOrDefault(eachOpenCell, board.allValues()));
                if (possibleValues.isEmpty()) {
                    throw new RuntimeException("unsolvable");
                }
                cellOptions.put(eachOpenCell, possibleValues);
            });
        });
        return cellOptions;
    }

    public void forOpenCells(Consumer<SudokuCell> action) {
        board.forRelevantCells((eachCell) -> {
            if (!fixedCells.containsKey(eachCell)) {
                action.accept(eachCell);
            }
        });
    }

    @Override
    public Map<SudokuCell, Set<Integer>> getOptionsPerCell() {
        if (optionsPerCell == null) {
            Map<SudokuCell, Set<Integer>> map = new HashMap<>();
            forOpenCells((eachCell) -> {
                map.put(eachCell, board.possibleValues(eachCell, this));
            });
            optionsPerCell = Map.copyOf(map);
        }
        return optionsPerCell;
    }
}