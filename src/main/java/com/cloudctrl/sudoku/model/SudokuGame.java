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
public class SudokuGame implements CellAccess {

    private final Board board;
    private final Map<Cell, Integer> fixedCells;

    public static SudokuGame fromArray(int[][] cells) {
        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.initFromArray(cells);
        return builder.newGame();
    }

    public SudokuGame(Board board, Map<Cell, Integer> fixedCells) {
        this.board = board;
        this.fixedCells = Map.copyOf(fixedCells);
    }

    public Map<Cell, Integer> getFixedCells() {
        return fixedCells;
    }

    public Board getBoard() {
        return board;
    }

    public int valueAt(Cell cell) {
        return fixedCells.getOrDefault(cell, -1);
    }

    public int numberOfCellsToSolve() {
        return board.getRelevantCells().size() - fixedCells.size();
    }

    public Map<Cell, Set<Integer>> calcOptionsPerCell(CellAccess gameState) {
        Map<Cell, Set<Integer>> map = new HashMap<>();
        forOpenCells((eachCell) -> {
            map.put(eachCell, getBoard().possibleValues(eachCell, gameState));
        });
        return map;
    }

    public Map<Cell, Integer> valuesFor(Collection<Cell> cells) {
        var map = new HashMap<Cell, Integer>();
        cells.stream().forEach((c) -> {
            int value = valueAt(c);
            if (value != -1) {
                map.put(c, value);
            }
        });
        return map;
    }

    public Map<Cell, Set<Integer>> findOpenCellValues() {
        var cellOptions = new HashMap<Cell, Set<Integer>>();
        board.forBoxes((eachBox) -> {
            Map<Cell, Integer> filledCells = valuesFor(eachBox.getCells());
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

    public void forOpenCells(Consumer<Cell> action) {
        board.forRelevantCells((eachCell) -> {
            if (!fixedCells.containsKey(eachCell)) {
                action.accept(eachCell);
            }
        });
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        for (int y = 1; y <= board.maxY(); y++) {
            for (int x = 1; x <= board.maxX(); x++) {
                var v = valueAt(x, y);
                sb.append(v > 0 ? v : "?").append(' ');
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}