package com.cloudctrl.sudoku.model;

import java.util.List;

import com.cloudctrl.sudoku.model.data.SudokuGames;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AutoplayTest {

    @Test
    public void testEasyGames() {
        testGames(SudokuGames.getEasyGames());
    }

    @Test
    public void testMediumGames() {
        testGames(SudokuGames.getMediumGames());
    }

    @Test
    public void testHardGame29() {
        testGame(SudokuGames.getHardGames().get(29));
    }

    @Test
    public void testHardGames() {
        testGames(SudokuGames.getHardGames());
    }

    @Test
    public void testVeryHardGames() {
        testGames(SudokuGames.getVeryHardGames());
    }

    public void testGames(List<SudokuGame> games) {
        games.forEach(g -> testGame(g));
    }

    public void testGame(SudokuGame game) {
        AutoPlayer2 player = new AutoPlayer2(game);
        var endState = player.solve();
        assertTrue(endState.isSolved());
    }
}
