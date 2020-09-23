package com.cloudctrl.sudoku.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.cloudctrl.sudoku.utils.CollUtils;

/**
 * Created by Jan on 14-8-2016.
 */
public class Board {

    private static Set<Integer> ALL_VALUES = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    private final Set<Box> boxes;
    private final int maxX;
    private final int maxY;

    public Board(Collection<Box> boxes) {
        this.boxes = Set.copyOf(boxes);
        this.maxX = boxes.stream().mapToInt(b -> b.maxX()).max().getAsInt();
        this.maxY = boxes.stream().mapToInt(b -> b.maxY()).max().getAsInt();
    }

    public Set<Box> getBoxes() {
        return boxes;
    }

    public boolean includes(Cell cell) {
        return boxes.stream().anyMatch((box) -> box.includes(cell));
    }

    public boolean canAdd(Cell cell, int value, Map<Cell, Integer> fixedCells) {
        return boxes.stream().allMatch(b -> b.canAdd(cell, value, fixedCells));
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }

    public void forBoxes(Consumer<Box> action) {
        boxes.stream().forEach(action);
    }

    public void forBoxes(Cell cell, Consumer<Box> action) {
        boxes.stream().
                filter(eachBox -> eachBox.includes(cell)).
                forEach(action);
    }

    /*
        relevantCellsDo: aBlock
     */
    public void forRelevantCells(Consumer<Cell> action) {
        var cellSet = new HashSet<>();
        forBoxes(eachBox -> {
            eachBox.getCells().forEach(eachCell -> {
                if (!cellSet.contains(eachCell)) {
                    action.accept(eachCell);
                    cellSet.add(eachCell);
                }
            });
        });
    }

    public Set<Cell> getRelevantCells() {
        var cellSet = new HashSet<Cell>();
        forBoxes(eachBox -> cellSet.addAll(eachBox.getCells()));
        return cellSet;
    }

    private Set<Cell> cellsSharingBox(Cell cell) {
        var cellsSet = new HashSet<Cell>();
        boxes.forEach((eachBox) -> {
            if (eachBox.includes(cell)) {
                eachBox.getCells().forEach((eachCell) -> {
                    if (eachCell != cell && !(cellsSet.contains(eachCell))) {
                        cellsSet.add(eachCell);
                    }
                });
            }
        });
        return cellsSet;
    }

    public Set<Integer> possibleValues(Cell cell, CellAccess game) {
        Set<Integer> values = ALL_VALUES;
        for (Box eachBox : boxes) {
            if (eachBox.includes(cell)) {
                values = eachBox.possibleValues(cell, values, game);
            }
        }
        return values;
    }

    public Map<Cell, Set<Integer>> processMove(Map<Cell, Set<Integer>> optionsPerCell, Move move) {
        var newOptions = CollUtils.copyWithout(optionsPerCell, move.getCell());
        for (Cell c : cellsSharingBox(move.getCell())) {
            var values = newOptions.get(c);
            if (values != null) {
                newOptions.put(c, CollUtils.copyWithout(values, move.getValue()));
            }
        }
        return newOptions;
    }
}
