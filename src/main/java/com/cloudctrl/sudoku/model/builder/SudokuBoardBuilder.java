package com.cloudctrl.sudoku.model.builder;

import java.util.ArrayList;
import java.util.List;

import com.cloudctrl.sudoku.model.Board;
import com.cloudctrl.sudoku.model.Box;
import com.cloudctrl.sudoku.model.Cell;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoardBuilder {

    private List<Box> boxes = new ArrayList<>();

    public static Board default9x9() {
        SudokuBoardBuilder builder = new SudokuBoardBuilder();
        builder.initStandard(9, 9);
        return builder.newBoard();
    }

    public void addBox(String aName, Cell fromCell, Cell toCell) {

        if ((toCell.x() - fromCell.x() + 1) * (toCell.y() - fromCell.y() + 1) != 9) {
            throw new RuntimeException("Invalid box");
        }
        boxes.add(new Box(aName, fromCell, toCell));
    }

    public void initStandard(int maxX, int maxY) {
        for (int i = 0; i < maxX; i++) {
            this.addBox("row-" + Integer.toString(i+1), new Cell(1, i+1), new Cell(maxX, i+1));
        }
        for (int i = 0; i < maxX; i++) {
            this.addBox("column-" + Integer.toString(i+1), new Cell(i+1, 1), new Cell(i+1, maxY));
        }
        for (int y = 0; y < maxY; y = y + 3) {
            for (int x = 0; x < maxX; x = x + 3) {
                this.addBox("box-" + Integer.toString(x+1) + "x" + Integer.toString(y+1),
                        new Cell(x+1, y+1),
                        new Cell(x+3, y+3));
            }
        }
    }

    public Board newBoard() {
        return new Board(boxes);
    }
}
