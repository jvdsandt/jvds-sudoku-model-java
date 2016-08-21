package com.cloudctrl.sudoku.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoard {

    private static ImmutableSet<Integer> ALL_VALUES = ImmutableSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    private ImmutableSet<SudokuBox> boxes;
    private int maxX;
    private int maxY;

    public SudokuBoard(Collection<SudokuBox> boxes) {
        this.boxes = ImmutableSet.copyOf(boxes);
        maxX = boxes.stream().mapToInt(b -> b.maxX()).max().getAsInt();
        maxY = boxes.stream().mapToInt(b -> b.maxY()).max().getAsInt();
    }

    public Set<SudokuBox> getBoxes() {
        return boxes;
    }

    public boolean includes(SudokuCell cell) {
        for (SudokuBox box : boxes) {
            if (box.includes(cell)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAdd(SudokuCell cell, int value, Map<SudokuCell, Integer> fixedCells) {
        return boxes.stream().allMatch((b) -> b.canAdd(cell, value, fixedCells));
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }

    public ImmutableSet<Integer> allValues() {
        return ALL_VALUES;
    }

    public void forBoxes(Consumer<SudokuBox> action) {
        boxes.stream().forEach(action);
    }
}
