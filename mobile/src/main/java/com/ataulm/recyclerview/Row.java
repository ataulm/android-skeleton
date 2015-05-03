package com.ataulm.recyclerview;

import java.util.ArrayList;
import java.util.List;

final class Row {

    private final List<Cell> cells = new ArrayList<>();
    private final int rowSize;

    Row(int rowSize) {
        this.rowSize = rowSize;
    }

    boolean canFit(Cell cell) {
        return filledSpans() + cell.size <= rowSize;
    }

    void add(Cell cell) {
        cells.add(cell);
    }

    private int spanStart(int adapterPosition) {
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).position == adapterPosition) {
                return i;
            }
        }
        return -1;
    }

    int filledSpans() {
        int spanCount = 0;
        for (Cell cell : cells) {
            spanCount += cell.size;
        }
        return spanCount;
    }

    boolean hasCell(int adapterPosition) {
        return cellAt(adapterPosition) != null;
    }

    private Cell cellAt(int adapterPosition) {
        for (Cell cell : cells) {
            if (cell.position == adapterPosition) {
                return cell;
            }
        }
        return null;
    }

    boolean itemStartsInFirstColumn(int adapterPosition) {
        return spanStart(adapterPosition) == 0;
    }

    boolean itemEndsInLastColumn(int adapterPosition) {
        Cell cell = cellAt(adapterPosition);
        if (cell == null) {
            throw outOfBounds(adapterPosition);
        }
        return spanStart(adapterPosition) + cell.size == rowSize;
    }

    boolean itemIsNextToItemInFirstColumn(int adapterPosition) {
        return hasCell(adapterPosition - 1) && itemStartsInFirstColumn(adapterPosition - 1);
    }

    boolean itemIsNextToItemInLastColumn(int adapterPosition) {
        return hasCell(adapterPosition + 1) && itemEndsInLastColumn(adapterPosition + 1);
    }

    static IndexOutOfBoundsException outOfBounds(int adapterPosition) {
        return new IndexOutOfBoundsException("Couldn't find adapterPosition: " + adapterPosition);
    }

    final static class Cell {

        final int position;
        final int size;

        Cell(int position, int size) {
            this.position = position;
            this.size = size;
        }

    }

}
