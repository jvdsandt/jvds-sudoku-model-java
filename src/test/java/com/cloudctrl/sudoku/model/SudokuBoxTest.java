package com.cloudctrl.sudoku.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoxTest extends TestCase {

    @Test
    public void testMaxX() {
        SudokuBox box = new SudokuBox("test",
                new SudokuCell(1, 1),
                new SudokuCell(2, 4));

        assertEquals(2, box.maxX());
        assertEquals(4, box.maxY());
    }

    @Test
    public void testIncludes() {
        var b1 = new SudokuBox("b1", new SudokuCell(1,1), new SudokuCell(3,3));
        assertTrue(b1.includes(new SudokuCell(1, 1)));
        assertTrue(b1.includes(new SudokuCell(3, 3)));
        assertFalse(b1.includes(new SudokuCell(1, 4)));
        assertFalse(b1.includes(new SudokuCell(4, 1)));
    }

    @Test
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
