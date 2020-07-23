package com.cloudctrl.sudoku.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBox {

    private final String name;
    private final Set<SudokuCell> cells;

    public SudokuBox(String name, Set<SudokuCell> cells) {
        this.name = name;
        this.cells = Set.copyOf(cells);
    }

    public SudokuBox(String name, SudokuCell minCell, SudokuCell maxCell) {
        this(name, createCells(minCell, maxCell));
    }

    public int maxX() {
        return cells.stream().mapToInt(cell -> cell.x()).max().getAsInt();
    }

    public int maxY() {
        return cells.stream().mapToInt(cell -> cell.y()).max().getAsInt();
    }

    public Collection<SudokuCell> getCells() {
        return cells;
    }

    public boolean includes(SudokuCell aCell) {
        return cells.contains(aCell);
    }

    public boolean canAdd(SudokuCell cell, int value, Map<SudokuCell, Integer> fixedCells) {
        if (!includes(cell)) {
            return true;
        }
        for (SudokuCell eachCell : cells) {
            if (fixedCells.getOrDefault(eachCell, -1) == value) {
                return false;
            }
        }
        return true;
    }

    private static Set<SudokuCell> createCells(SudokuCell minCell, SudokuCell maxCell) {
        var cells = new HashSet<SudokuCell>();
        for (int ypos = minCell.y(); ypos <= maxCell.y(); ypos++) {
            for (int xpos = minCell.x(); xpos <= maxCell.x(); xpos++) {
                cells.add(new SudokuCell(xpos, ypos));
            }
        }
        return cells;
    }

    public void forCells(Collection<SudokuCell> skipList, Consumer<SudokuCell> action) {
        for (SudokuCell c : cells) {
            if (!(skipList.contains(c))) {
                action.accept(c);
            }
        }
    }

    public Set<Integer> possibleValues(SudokuCell aCell, Set<Integer> values, SudokuGameBase game) {
        Set<Integer> result = new HashSet<>(values);
        cells.forEach((eachCell) -> {
            if (eachCell != aCell) {
                game.valueIfKnown(eachCell, (value) -> result.remove(value));
            }
        });
        return result;
    }

    public SudokuMove findMove(Map<SudokuCell, Set<Integer>> options) {
        Map<Integer, Set<SudokuCell>> cellsPerValue = new HashMap<>();
        for (SudokuCell eachCell : cells) {
            var values = options.getOrDefault(eachCell, Collections.emptySet());
            for (Integer value : values) {
                if (cellsPerValue.containsKey(value)) {
                    cellsPerValue.get(value).add(eachCell);
                } else {
                    Set<SudokuCell> cells = new HashSet<>();
                    cells.add(eachCell);
                    cellsPerValue.put(value, cells);
                }
            }
        }
        return cellsPerValue.entrySet().stream()
                .filter(e -> e.getValue().size() == 1)
                .map(e -> new SudokuMove(e.getValue().iterator().next(), e.getKey(), SudokuMove.Reason.ONLY_PLACE))
                .findFirst()
                .orElse(null);
    }
}