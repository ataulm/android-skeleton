package com.ataulm.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ataulm.basic.ItemView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private final int horizontalSpacing;
    private final int verticalSpacing;
    private final SpanSizeLookup spanSizeLookup;

    public SpacesItemDecoration(int horizontalSpacing, int verticalSpacing, SpanSizeLookup spanSizeLookup) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.spanSizeLookup = spanSizeLookup;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        applyDefaultOffsets(outRect, horizontalSpacing, verticalSpacing);

        Grid grid = Grid.newInstance(parent.getAdapter().getItemCount(), spanSizeLookup);
        int position = parent.getChildPosition(view);
        adjustOffsets(grid, position, outRect);

        updateDebugInfo(grid, position, (ItemView) view);
    }

    private static void applyDefaultOffsets(Rect outRect, int horizontalSpacing, int verticalSpacing) {
        outRect.left = horizontalSpacing / 2;
        outRect.top = verticalSpacing / 2;
        outRect.right = horizontalSpacing / 2;
        outRect.bottom = verticalSpacing / 2;
    }

    private static void adjustOffsets(Grid grid, int itemPosition, Rect outRect) {
        if (grid.itemIsInFirstColumn(itemPosition)) {
            outRect.left = 0;
        }
        if (grid.itemIsInLastColumn(itemPosition)) {
            outRect.right = 0;
        }
        if (grid.itemIsInFirstRow(itemPosition)) {
            outRect.top = 0;
        }
        if (grid.itemIsInLastRow(itemPosition)) {
            outRect.bottom = 0;
        }
    }

    private static void updateDebugInfo(Grid grid, int position, ItemView view) {
        StringBuilder place = new StringBuilder();
        if (grid.itemIsInFirstColumn(position)) {
            place.append("fc,");
        }

        if (grid.itemIsInLastColumn(position)) {
            place.append("lc,");
        }

        if (grid.itemIsInFirstRow(position)) {
            place.append("fr,");
        }

        if (grid.itemIsInLastRow(position)) {
            place.append("lr,");
        }

        if (place.length() > 0) {
            view.update(place.substring(0, place.length() - 1));
        } else {
            view.update("");
        }
    }

}
