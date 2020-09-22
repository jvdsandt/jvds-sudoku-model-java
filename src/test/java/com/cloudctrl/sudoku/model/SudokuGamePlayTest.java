package com.cloudctrl.sudoku.model;

import java.util.ArrayList;
import java.util.List;

import com.cloudctrl.sudoku.model.data.SudokuGames;
import org.junit.Test;

public class SudokuGamePlayTest {

    @Test
    public void testFirstEasyGame() {
        SudokuGame game = SudokuGames.getEasyGames().get(0);
        testGame(game);
    }

    @Test
    public void testFirstVeryHardGame() {
        SudokuGame game = SudokuGames.getVeryHardGames().get(0);
        testGame(game);
    }

    public void testGame(SudokuGame game) {
        AutoPlayer player = new AutoPlayer(game);
        while (!player.isSolved()) {
            player.doNextMove();
            System.out.println(player.getCurrentStep());
        }
    }
}
