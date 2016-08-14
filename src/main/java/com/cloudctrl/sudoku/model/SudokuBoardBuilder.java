package com.cloudctrl.sudoku.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 14-8-2016.
 */
public class SudokuBoardBuilder {

    private List<SudokuBox> boxes = new ArrayList<>();

    public static SudokuBoard default9x9() {
        SudokuBoardBuilder builder = new SudokuBoardBuilder();
        builder.initStandard(9, 9);
        return builder.newBoard();
    }

    public void addBox(String aName, SudokuCell fromCell, SudokuCell toCell) {

        if ((toCell.x() - fromCell.x() + 1) * (toCell.y() - fromCell.y() + 1) != 9) {
            throw new RuntimeException("Invalid box");
        }

        List<SudokuCell> cells = new ArrayList<>(9);
        for (int y = fromCell.y(); y <= toCell.y(); y++) {
            for (int x = fromCell.x(); x <= toCell.x(); x++) {
                cells.add(new SudokuCell(x, y));
            }
        }
        boxes.add(new SudokuBox(aName, cells.toArray(new SudokuCell[9])));
    }

    public void initStandard(int maxX, int maxY) {
        for (int i = 0; i < maxX; i++) {
            this.addBox("row-" + Integer.toString(i+1), new SudokuCell(1, i+1), new SudokuCell(maxX, i+1));
        }
        for (int i = 0; i < maxX; i++) {
            this.addBox("column-" + Integer.toString(i+1), new SudokuCell(i+1, 1), new SudokuCell(i+1, maxY));
        }
        for (int y = 0; y < maxY; y = y + 3) {
            for (int x = 0; x < maxX; x = x + 3) {
                this.addBox("box-" + Integer.toString(x+1) + "x" + Integer.toString(y+1),
                        new SudokuCell(x+1, y+1),
                        new SudokuCell(x+3, y+3));
            }
        }
    }

    public SudokuBoard newBoard() {
        return new SudokuBoard(boxes.toArray(new SudokuBox[boxes.size()]));
    }
}
