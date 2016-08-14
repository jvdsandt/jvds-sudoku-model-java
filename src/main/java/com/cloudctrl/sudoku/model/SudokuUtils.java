package com.cloudctrl.sudoku.model;

import java.util.Comparator;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuUtils {

    public static <T> T min(T[] arr, Comparator<? super T> comp) {
        T candidate = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (comp.compare(arr[i], candidate) < 0) {
                candidate = arr[i];
            }
        }
        return candidate;
    }

    public static <T> T max(T[] arr, Comparator<? super T> comp) {
        T candidate = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (comp.compare(arr[i], candidate) > 0) {
                candidate = arr[i];
            }
        }
        return candidate;
    }
}
