package com.cloudctrl.sudoku.model;

import junit.framework.TestCase;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuGameTest extends TestCase {

    public void testCreateStandardGame() {

        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.fix(2, 1, 7);
        builder.fix(5,1, 4);



    }
}
