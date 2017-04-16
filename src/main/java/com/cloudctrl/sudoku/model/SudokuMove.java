package com.cloudctrl.sudoku.model;

import com.sun.istack.internal.NotNull;

/**
 * Created by jan on 16-04-17.
 */
public class SudokuMove {

    private SudokuCell cell;
    private int value;

    SudokuMove(@NotNull SudokuCell cell, int value) {
        this.cell = cell;
        this.value = value;
    }

    public SudokuCell getCell() {
        return cell;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SudokuMove that = (SudokuMove) o;

        if (value != that.value) return false;
        return cell.equals(that.cell);
    }

    @Override
    public int hashCode() {
        int result = cell.hashCode();
        result = 31 * result + value;
        return result;
    }
}
