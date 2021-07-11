package com.cloudctrl.sudoku;

import com.cloudctrl.sudoku.model.AutoPlayer2;
import com.cloudctrl.sudoku.model.SudokuGame;
import com.cloudctrl.sudoku.model.builder.SudokuGameBuilder;
import com.cloudctrl.sudoku.model.data.SudokuGames;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Sudoku expected");
        }
        for (String input : args) {
            solve(input);
        }
    }

    private static void solve(String input) {
        SudokuGame game = SudokuGameBuilder.newGameFromNumberLine(input);
        AutoPlayer2 player = new AutoPlayer2(game);
        var endState = player.solve();
        System.out.println(endState);
    }
}
