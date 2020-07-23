package com.cloudctrl.sudoku.model.data;

import java.util.List;

import com.cloudctrl.sudoku.model.SudokuGame;

/**
 * Created by Jan on 21-8-2016.
 */
public class SudokuGames {

    public static List<SudokuGame> getEasyGames() {
        return getOpenSudokuGames("/easy.opensudoku");
    }

    public static List<SudokuGame> getMediumGames() {
        return getOpenSudokuGames("/medium.opensudoku");
    }

    public static List<SudokuGame> getHardGames() {
        return getOpenSudokuGames("/hard.opensudoku");
    }

    public static List<SudokuGame> getVeryHardGames() {
        return getOpenSudokuGames("/very_hard.opensudoku");
    }

    private static List<SudokuGame> getOpenSudokuGames(String fname) {
        try {
            return new OpenSudokuGamesReader().read(SudokuGames.class.getResourceAsStream(fname));
        } catch (Exception ex) {
            throw new RuntimeException("Failed", ex);
        }
    }

    public final static SudokuGame SIMPLE_GAME = SudokuGame.fromArray(
            new int[][] {
                    {0, 0, 0, 2, 6, 0, 7, 0, 1},
                    {6, 8, 0, 0, 7, 0, 0, 9, 0},
                    {1, 9, 0, 0, 0, 4, 5, 0, 0},
                    {8, 2, 0, 1, 0, 0, 0, 4, 0},
                    {0, 0, 4, 6, 0, 2, 9, 0, 0},
                    {0, 5, 0, 0, 0, 3, 0, 2, 8},
                    {0, 0, 9, 3, 0, 0, 0, 7, 4},
                    {0, 4, 0, 0, 5, 0, 0, 3, 6},
                    {7, 0, 3, 0, 1, 8, 0, 0, 0} });


}
