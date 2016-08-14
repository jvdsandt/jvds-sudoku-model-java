package com.cloudctrl.sudoku.model;

import junit.framework.TestCase;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoardBuilderTest extends TestCase {

    public void testDefault9x9() {
        SudokuBoard board = SudokuBoardBuilder.default9x9();
        assertEquals(27, board.getBoxes().length);
        assertEquals(9, board.maxX());
        assertEquals(9, board.maxY());
    }
}
