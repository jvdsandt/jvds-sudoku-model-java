package com.cloudctrl.sudoku.model;

import java.util.List;

import com.cloudctrl.sudoku.model.data.OpenSudokuGamesReader;
import junit.framework.TestCase;

/**
 * Created by jan on 16-04-17.
 */
public class OpenSudokuGamesTest extends TestCase {

    public void testParsing() throws Exception {
        List<SudokuGame> games = new OpenSudokuGamesReader().read(getClass().getResourceAsStream("/easy.opensudoku"));

        assertEquals(100, games.size());

        /* "379000014060010070080009005435007000090040020000800436900700080040080050850000249" */
        SudokuGame game = games.get(0);
        assertEquals(3, game.valueAt(1, 1));
        assertEquals(2, game.valueAt(7, 9));
        assertEquals(4, game.valueAt(8, 9));
        assertEquals(9, game.valueAt(9,9));

        /* "072000000916080000345020070090504086008201300460807020080050213000070859000000460" */
        game = games.get(99);
        assertEquals(9, game.valueAt(1, 2));
        assertEquals(6, game.valueAt(8, 9));
        assertEquals(-1, game.valueAt(9,9));
    }
}
