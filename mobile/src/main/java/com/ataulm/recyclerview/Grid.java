package com.ataulm.recyclerview;

import java.util.ArrayList;
import java.util.List;

final class Grid {

    private final List<Row> rows = new ArrayList<>();

    static Grid newInstance(int adapterCount, SpanSizeLookup spanSizeLookup) {
        Grid grid = new Grid();

        int maxSpans = spanSizeLookup.getSpanCount();
        Row tempRow = new Row(maxSpans);
        for (int i = 0; i < adapterCount; i++) {
            Row.Cell cell = new Row.Cell(i, spanSizeLookup.getSpanSize(i));
            if (!tempRow.canFit(cell)) {
                grid.add(tempRow);
                tempRow = new Row(maxSpans);
            }
            tempRow.add(cell);
        }
        grid.add(tempRow);

        return grid;
    }

    private Grid() {
        // use the mu'fn factory method
    }

    void add(Row row) {
        rows.add(row);
    }

    boolean itemIsInFirstRow(int adapterPosition) {
        return rowIndexWith(adapterPosition) == 0;
    }

    boolean itemIsInLastRow(int adapterPosition) {
        return rowIndexWith(adapterPosition) == rows.size() - 1;
    }

    boolean itemIsInFirstColumn(int adapterPosition) {
        Row row = findRowWith(adapterPosition);
        return row.itemStartsInFirstColumn(adapterPosition);
    }

    boolean itemIsInLastColumn(int adapterPosition) {
        Row row = findRowWith(adapterPosition);
        return row.itemEndsInLastColumn(adapterPosition);
    }

    private int rowIndexWith(int adapterPosition) {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).hasCell(adapterPosition)) {
                return i;
            }
        }
        throw Row.outOfBounds(adapterPosition);
    }

    private Row findRowWith(int adapterPosition) {
        for (Row row : rows) {
            if (row.hasCell(adapterPosition)) {
                return row;
            }
        }
        throw Row.outOfBounds(adapterPosition);
    }

}
