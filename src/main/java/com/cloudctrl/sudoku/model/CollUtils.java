package com.cloudctrl.sudoku.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jan on 16-04-17.
 */
public class CollUtils {

    public static <T> Set<T> copyWithout(Set<T> source, T elem) {
        if (source.contains(elem)) {
            Set<T> newSet = new HashSet<>(source);
            newSet.remove(elem);
            return newSet;
        }
        return source;
    }

    public static <T> Set<T> copyWith(Set<T> source, T elem) {
        if (source.contains(elem)) {
            return source;
        }
        Set<T> newSet = new HashSet<>(source);
        newSet.add(elem);
        return newSet;
    }

    public static <K, V> Map<K, V> copyWithout(Map<K, V> source, K key) {
        if (source.containsKey(key)) {
            Map<K, V> newSet = new HashMap<>(source);
            newSet.remove(key);
            return newSet;
        }
        return source;
    }

    public static <K, V> Map<K, V> copyWith(Map<K, V> source, K key, V value) {
        if (source.get(key) == value) {
            return source;
        }
        Map<K, V> newSet = new HashMap<>(source);
        newSet.put(key, value);
        return newSet;
    }
}
