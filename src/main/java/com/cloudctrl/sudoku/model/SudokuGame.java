package com.cloudctrl.sudoku.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jan on 16-08-16.
 */
public class SudokuGame {

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

    public int valueAt(SudokuCell cell) {
        return fixedCells.getOrDefault(cell, -1);
    }
    public int valueAt(int xpos, int ypos) {
        return valueAt(new SudokuCell(xpos, ypos));
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
}