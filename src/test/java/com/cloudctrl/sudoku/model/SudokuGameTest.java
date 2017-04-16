package com.cloudctrl.sudoku.model;

import com.cloudctrl.sudoku.model.builder.SudokuGameBuilder;
import com.google.common.collect.ImmutableSet;
import junit.framework.TestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuGameTest extends TestCase {

    public void testCreateStandardGame() {

        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.fix(2, 1, 7);
        builder.fix(5,1, 4);

        SudokuGame game = builder.newGame();

        assertEquals(7, game.valueAt(2, 1));
        assertEquals(4, game.valueAt(5, 1));
        assertEquals(-1, game.valueAt(1, 1));
    }

    public void testCreateInvalidGame() {

        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.fix(2, 1, 7);
        try {
            builder.fix(2, 1, 8);
            fail("Illegal argument expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
        try {
            builder.fix(3, 1, 7);
            fail("Illegal argument expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
        try {
            builder.fix(10, 1, 7);
            fail("Illegal argument expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
        builder.fix(2, 1, 7);
    }

    public void testCreateSimpleGame() {
        SudokuGame game = SudokuGames.SIMPLE_GAME;
        Map<SudokuCell, Set<Integer>> openCellValues = game.findOpenCellValues();

    }

    public void testPossibleValues() {

        SudokuGame game = SudokuGames.SIMPLE_GAME;

        assertEquals(ImmutableSet.of(3, 4, 5), game.getOptionsPerCell(new SudokuCell(1, 1)));
        assertEquals(ImmutableSet.of(2, 5, 9), game.getOptionsPerCell(new SudokuCell(9, 9)));
    }

}
