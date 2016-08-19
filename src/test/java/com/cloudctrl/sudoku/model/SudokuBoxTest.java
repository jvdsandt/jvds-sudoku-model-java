package com.cloudctrl.sudoku.model;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoxTest extends TestCase {

    public void testMaxX() {
        SudokuBox box = new SudokuBox("test",
                new SudokuCell(1, 1),
                new SudokuCell(2, 4));

        assertEquals(2, box.maxX());
        assertEquals(4, box.maxY());
    }

    public void testCanAdd() {
        SudokuBox box = new SudokuBox("test",
                new SudokuCell(1, 1),
                new SudokuCell(3, 3));
        Map<SudokuCell, Integer> fixedCells = new HashMap<>();
        fixedCells.put(new SudokuCell(2, 2), 9);

        assertTrue(box.canAdd(new SudokuCell(1, 1), 8, fixedCells));
        assertFalse(box.canAdd(new SudokuCell(1, 1), 9, fixedCells));
        assertTrue(box.canAdd(new SudokuCell(4, 4), 9, fixedCells));
    }
}
