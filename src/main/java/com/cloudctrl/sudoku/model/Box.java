package com.cloudctrl.sudoku.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jan on 14-8-2016.
 */
public class Box {

    private final String name;
    private final Set<Cell> cells;

    public Box(String name, Set<Cell> cells) {
        this.name = name;
        this.cells = Set.copyOf(cells);
    }

    public Box(String name, Cell minCell, Cell maxCell) {
        this(name, createCells(minCell, maxCell));
    }

    private static Set<Cell> createCells(Cell minCell, Cell maxCell) {
        var cells = new HashSet<Cell>();
        for (int ypos = minCell.y(); ypos <= maxCell.y(); ypos++) {
            for (int xpos = minCell.x(); xpos <= maxCell.x(); xpos++) {
                cells.add(new Cell(xpos, ypos));
            }
        }
        return cells;
    }

    public String getName() {
		return name;
	}

	public int maxX() {
        return cells.stream().mapToInt(cell -> cell.x()).max().getAsInt();
    }

    public int maxY() {
        return cells.stream().mapToInt(cell -> cell.y()).max().getAsInt();
    }

    public Collection<Cell> getCells() {
        return cells;
    }

    public boolean includes(Cell aCell) {
        return cells.contains(aCell);
    }

    public boolean canAdd(Cell cell, int value, Map<Cell, Integer> fixedCells) {
        if (!includes(cell)) {
            return true;
        }
        return !cells.stream()
       		.anyMatch(eachCell -> fixedCells.getOrDefault(eachCell, -1) == value);
    }

    public void forCells(Collection<Cell> skipList, Consumer<Cell> action) {
        for (Cell c : cells) {
            if (!(skipList.contains(c))) {
                action.accept(c);
            }
        }
    }

    public Set<Integer> possibleValues(Cell aCell, Set<Integer> values, CellAccess game) {
        Set<Integer> result = new HashSet<>(values);
        cells.forEach((eachCell) -> {
            if (eachCell != aCell) {
                game.valueIfKnown(eachCell, (value) -> result.remove(value));
            }
        });
        return result;
    }

    private Stream<Move> findMovesStream(Map<Cell, Set<Integer>> options) {
        Map<Integer, Set<Cell>> cellsPerValue = new HashMap<>();
        for (Cell eachCell : cells) {
            var values = options.getOrDefault(eachCell, Collections.emptySet());
            for (Integer value : values) {
                if (cellsPerValue.containsKey(value)) {
                    cellsPerValue.get(value).add(eachCell);
                } else {
                    Set<Cell> cells = new HashSet<>();
                    cells.add(eachCell);
                    cellsPerValue.put(value, cells);
                }
            }
        }
        return cellsPerValue.entrySet().stream()
                .filter(e -> e.getValue().size() == 1)
                .map(e -> new Move(e.getValue().iterator().next(), e.getKey(), Move.Reason.ONLY_PLACE));
    }

    public Move findMove(Map<Cell, Set<Integer>> options) {
        return findMovesStream(options).findFirst().orElse(null);
    }

    public Set<Move> findMoves(Map<Cell, Set<Integer>> options) {
        return findMovesStream(options).collect(Collectors.toSet());
    }
}