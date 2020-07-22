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

    public static SudokuAutoGame newFrom(SudokuGameBase prevGame, SudokuMove move, boolean guessed) {

        Map<SudokuCell, Set<Integer>> options = new HashMap<>(prevGame.getOptionsPerCell());
        options.remove(move.getCell());

        for(SudokuCell eachCell : prevGame.getBoard().cellsSharingBox(move.getCell())) {
            Set<Integer> vals = new HashSet<>(options.get(eachCell));
            vals.remove(move.getValue());
            if (vals.isEmpty()) {
                return null;
            }
            options.put(eachCell, vals);
        };

        return new SudokuAutoGame(prevGame, move, guessed, options);
    }

    public static SudokuAutoGame newFromInvalid(SudokuGameBase prevGame, SudokuMove invalidMove) {

        Map<SudokuCell, Set<Integer>> options = new HashMap<>(prevGame.getOptionsPerCell());
        options.put(
                invalidMove.getCell(),
                CollUtils.copyWithout(options.get(invalidMove.getCell()), invalidMove.getValue())
        );

        return new SudokuAutoGame(prevGame, invalidMove, false, options);
    }

    public SudokuAutoGame(SudokuGameBase prevGame, SudokuMove move, boolean guessed, Map<SudokuCell, Set<Integer>> options) {
        super(prevGame, move, options);
        this.guessed = guessed;
    }
}
