package com.cloudctrl.sudoku.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jan on 16-04-17.
 */
public class CollUtils {

    public static <T> Set<T> copyWithout(Set<T> source, T elem) {
        Set<T> newSet = new HashSet<>(source);
        newSet.remove(elem);
        return newSet;
    }

    public static <T> Set<T> copyWith(Set<T> source, T elem) {
        Set<T> newSet = new HashSet<>(source);
        newSet.add(elem);
        return newSet;
    }

    public static <K, V> Map<K, V> copyWithout(Map<K, V> source, K key) {
        Map<K, V> newMap = new HashMap<>(source);
        newMap.remove(key);
        return newMap;
    }

    public static <K, V> Map<K, V> copyWith(Map<K, V> source, K key, V value) {
        Map<K, V> newMap = new HashMap<>(source);
        newMap.put(key, value);
        return newMap;
    }
}
