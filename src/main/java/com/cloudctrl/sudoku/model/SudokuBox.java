package com.cloudctrl.sudoku.model;

import javax.sound.midi.Soundbank;

import com.google.common.collect.ImmutableSet;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBox {

    private String name;
    private ImmutableSet<SudokuCell> cells;

    public SudokuBox(String name, ImmutableSet<SudokuCell> cells) {
        this.name = name;
        this.cells = cells;
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

    private static ImmutableSet<SudokuCell> createCells(SudokuCell minCell, SudokuCell maxCell) {
        ImmutableSet.Builder<SudokuCell> builder = ImmutableSet.<SudokuCell>builder();
        for (int ypos = minCell.y(); ypos <= maxCell.y(); ypos++) {
            for (int xpos = minCell.x(); xpos <= maxCell.x(); xpos++) {
                builder.add(new SudokuCell(xpos, ypos));
            }
        }
        return builder.build();
    }

    public void forCells(Collection<SudokuCell> skipList, Consumer<SudokuCell> action) {
        cells.stream().forEach((c) -> {
            if (!(skipList.contains(c))) {
                action.accept(c);
            }
        });
    }

    public Set<Integer> possibleValues(SudokuCell aCell, Set<Integer> values, SudokuGameBase game) {
        Set<Integer> result = new HashSet<>(values);
        cells.forEach((eachCell) -> {
            if (eachCell != aCell) {
                game.valueIfKnown(eachCell, (value) -> { result.remove(value); });
            }
        });
        return result;
    }
}