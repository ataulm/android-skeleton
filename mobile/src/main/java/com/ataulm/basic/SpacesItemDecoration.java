package com.ataulm.basic;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private final int horizontalSpacing;
    private final int verticalSpacing;
    private final SpanSizeLookup spanSizeLookup;

    SpacesItemDecoration(int horizontalSpacing, int verticalSpacing, SpanSizeLookup spanSizeLookup) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.spanSizeLookup = spanSizeLookup;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Grid grid = Grid.newInstance(parent.getAdapter().getItemCount(), spanSizeLookup);

        int position = parent.getChildPosition(view);

        outRect.left = horizontalSpacing / 2;
        outRect.top = verticalSpacing / 2;
        outRect.right = horizontalSpacing / 2;
        outRect.bottom = verticalSpacing / 2;

        StringBuilder place = new StringBuilder();
        if (grid.itemIsInFirstColumn(position)) {
            outRect.left = 0;
            place.append("fc,");
        }

        if (grid.itemIsInLastColumn(position)) {
            outRect.right = 0;
            place.append("lc,");
        }

        if (grid.itemIsInFirstRow(position)) {
            outRect.top = 0;
            place.append("fr,");
        }

        if (grid.itemIsInLastRow(position)) {
            outRect.bottom = 0;
            place.append("lr,");
        }

        if (place.length() > 0) {
            ((ItemView) view).update(place.substring(0, place.length() - 1));
        } else {
            ((ItemView) view).update("");
        }
    }

    private static class Grid {

        private final List<Row> rows = new ArrayList<>();

        static Grid newInstance(int adapterCount, SpanSizeLookup spanSizeLookup) {
            Grid grid = new Grid();

            int maxSpans = spanSizeLookup.getSpanCount();
            Row tempRow = new Row(maxSpans);
            for (int i = 0; i < adapterCount; i++) {
                Cell cell = new Cell(i, spanSizeLookup.getSpanSize(i));
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

    private static class Row {

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

        static IndexOutOfBoundsException outOfBounds(int adapterPosition) {
            return new IndexOutOfBoundsException("Couldn't find adapterPosition: " + adapterPosition);
        }

    }

    private static class Cell {

        final int position;
        final int size;

        Cell(int position, int size) {
            this.position = position;
            this.size = size;
        }

    }

    public interface SpanSizeLookup {

        int getSpanSize(int position);

        int getSpanCount();

    }

}
