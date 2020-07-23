package com.cloudctrl.sudoku.model;

import java.util.ArrayList;
import java.util.List;

import com.cloudctrl.sudoku.model.data.SudokuGames;
import org.junit.Test;

public class SudokuGamePlayTest {

    @Test
    public void test() {

        List<SudokuGame> games = SudokuGames.getEasyGames();

        for (SudokuGame game : games) {
        }
    }

    @Test
    public void testFirstEasyGame() {
        SudokuGameBase game = SudokuGames.getEasyGames().get(0);
        List<SudokuGameBase> steps = new ArrayList<>();
        steps.add(game);
        while (!game.isSolved()) {
            System.out.println(game);
            game = game.doNextMove();
            steps.add(game);
        }
    }

    @Test
    public void testFirstVeryHardGame() {
        SudokuGameBase game = SudokuGames.getVeryHardGames().get(0);
        List<SudokuGameBase> steps = new ArrayList<>();
        steps.add(game);
        while (!game.isSolved()) {
            System.out.println(game);
            game = game.doNextMove();
            steps.add(game);
        }
    }
}
