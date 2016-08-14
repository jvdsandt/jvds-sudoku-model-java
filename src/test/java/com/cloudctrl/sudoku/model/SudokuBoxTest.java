package com.cloudctrl.sudoku.model;

import junit.framework.TestCase;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoxTest extends TestCase {

    public void testMaxX() {
        SudokuBox box = new SudokuBox("test",
                new SudokuCell[] {
                        new SudokuCell(1, 1),
                        new SudokuCell(2, 4)});
        assertEquals(2, box.maxX());
        assertEquals(4, box.maxY());
    }
}
