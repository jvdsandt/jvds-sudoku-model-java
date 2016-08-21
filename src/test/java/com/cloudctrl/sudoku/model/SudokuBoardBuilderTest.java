package com.cloudctrl.sudoku.model;

import junit.framework.TestCase;

import java.util.Collections;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoardBuilderTest extends TestCase {

    public void testDefault9x9() {
        SudokuBoard board = SudokuBoardBuilder.default9x9();
        assertEquals(27, board.getBoxes().size());
        assertEquals(9, board.maxX());
        assertEquals(9, board.maxY());
    }

    public void testIncludes() {
        SudokuBoard board = SudokuBoardBuilder.default9x9();
        assertTrue(board.includes(new SudokuCell(9, 9)));
        assertFalse(board.includes(new SudokuCell(10, 10)));
    }

    public void testCanAdd() {
        SudokuBoard board = SudokuBoardBuilder.default9x9();
        board.canAdd(new SudokuCell(9, 9), 5, Collections.EMPTY_MAP);
    }
}
