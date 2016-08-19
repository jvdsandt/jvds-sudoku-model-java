package com.cloudctrl.sudoku.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBox {

    private String name;
    private SudokuCell[] cells;

    public SudokuBox(String name, SudokuCell[] cells) {
        this.name = name;
        this.cells = cells;
    }

    public SudokuBox(String name, SudokuCell minCell, SudokuCell maxCell) {
        this(name, createCells(minCell, maxCell));
    }

    public int maxX() {
        return Arrays.stream(cells).mapToInt(cell -> cell.x()).max().getAsInt();
    }

    public int maxY() {
        return Arrays.stream(cells).mapToInt(cell -> cell.y()).max().getAsInt();
    }

    public boolean includes(SudokuCell aCell) {
        return Arrays.asList(cells).contains(aCell);
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

    private static SudokuCell[] createCells(SudokuCell minCell, SudokuCell maxCell) {
        List<SudokuCell> cells = new ArrayList<>(9);
        for (int ypos = minCell.y(); ypos <= maxCell.y(); ypos++) {
            for (int xpos = minCell.x(); xpos <= maxCell.x(); xpos++) {
                cells.add(new SudokuCell(xpos, ypos));
            }
        }
        return cells.toArray(new SudokuCell[cells.size()]);
    }
}