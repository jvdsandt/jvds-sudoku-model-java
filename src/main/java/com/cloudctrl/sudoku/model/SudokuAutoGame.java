package com.cloudctrl.sudoku.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jan on 16-04-17.
 */
public class SudokuAutoGame extends SudokuGamePlay {

    protected boolean guessed;

    public SudokuAutoGame(SudokuGameBase prevGame, SudokuMove move, Map<SudokuCell, Set<Integer>> options) {
        super(prevGame, move, options);
        this.guessed = move.getReason() == SudokuMove.Reason.GUESS;
    }

    public SudokuGamePlay goBackAndMove() {
        if (!guessed) {
            return previousPlay.goBackAndMove();
        }
        var newOptions = new HashMap<>(previousPlay.getOptionsPerCell());
        var newValues = newOptions.get(lastMove.getCell());
        newOptions.put(lastMove.getCell(), CollUtils.copyWithout(newValues, lastMove.getValue()));
        return new SudokuAutoGame(previousPlay, lastMove, newOptions);
    }
}
