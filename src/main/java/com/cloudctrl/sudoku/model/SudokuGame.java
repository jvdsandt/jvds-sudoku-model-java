package com.cloudctrl.sudoku.model;

import com.cloudctrl.sudoku.model.builder.SudokuGameBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

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

    private SudokuBoard board;
    private Map<SudokuCell, Integer> fixedCells;

    public static SudokuGame fromArray(int[][] cells) {
        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.initFromArray(cells);
        return builder.newGame();
    }

    public SudokuGame(SudokuBoard board, Map<SudokuCell, Integer> fixedCells) {
        this.board = board;
        this.fixedCells = ImmutableMap.copyOf(fixedCells);
    }

    @Override
    public SudokuGame getGame() { return this; }

    @Override
    public SudokuBoard getBoard() { return board; }

    public int valueAt(SudokuCell cell) {
        return fixedCells.getOrDefault(cell, -1);
    }

    public Map<SudokuCell, Integer> valuesFor(Collection
                                                      <SudokuCell> cells) {
        ImmutableMap.Builder b = ImmutableMap.<SudokuCell, Integer>builder();
        cells.stream().forEach((c) -> {
            int value = valueAt(c);
            if (value != -1) {
                b.put(c, value);
            }
        });
        return b.build();
    }

    public Map<SudokuCell, Set<Integer>> findOpenCellValues() {
        Map<SudokuCell, Set<Integer>> cellOptions = new HashMap<>();
        board.forBoxes((eachBox) -> {
            Map<SudokuCell, Integer> filledCells = valuesFor(eachBox.getCells());
            Set<Integer> openValues = new HashSet<>(board.allValues());
            openValues.removeAll(filledCells.values());
            eachBox.forCells(filledCells.keySet(), (eachOpenCell) -> {
                Set<Integer> possibleValues = Sets.intersection(
                        cellOptions.getOrDefault(eachOpenCell, board.allValues()),
                        openValues);
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
            optionsPerCell = ImmutableMap.copyOf(map);
        }
        return optionsPerCell;
    }
}