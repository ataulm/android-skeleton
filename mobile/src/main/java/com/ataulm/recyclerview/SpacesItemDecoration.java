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
        applyDefaultVerticalOffsets(outRect, verticalSpacing);

        Grid grid = Grid.newInstance(parent.getAdapter().getItemCount(), spanSizeLookup);
        int maxSpansInRow = spanSizeLookup.getSpanCount();
        int numberOfGaps = maxSpansInRow - 1;
        int majorSpace = (int) (1f * numberOfGaps / maxSpansInRow * horizontalSpacing);
        int halfSpace = (int) (horizontalSpacing * 0.5);
        int minorSpace = horizontalSpacing - majorSpace;

        int position = parent.getChildPosition(view);
        adjustOffsets(grid, position, outRect, minorSpace, halfSpace, majorSpace);

        updateDebugInfo(grid, position, (ItemView) view);
    }

    private static void applyDefaultVerticalOffsets(Rect outRect, int verticalSpacing) {
        outRect.top = verticalSpacing / 2;
        outRect.bottom = verticalSpacing / 2;
    }

    private static void adjustOffsets(Grid grid, int itemPosition, Rect outRect, int minorItemMarginSpace, int halfSpace, int majorItemMarginSpace) {
        adjustHorizontalOffsets(grid, itemPosition, outRect, minorItemMarginSpace, halfSpace, majorItemMarginSpace);

        if (grid.itemIsInFirstRow(itemPosition)) {
            outRect.top = 0; // TODO
        }
        if (grid.itemIsInLastRow(itemPosition)) {
            outRect.bottom = 0; // TODO
        }
    }

    private static void adjustHorizontalOffsets(Grid grid, int itemPosition, Rect outRect, int minorItemMarginSpace, int halfSpace, int majorItemMarginSpace) {
        if (grid.itemIsInFirstColumn(itemPosition) && grid.itemIsInLastColumn(itemPosition)) {
            outRect.left = 0;
            outRect.right = 0;
            return;
        }

        if (grid.itemIsInFirstColumn(itemPosition)) {
            outRect.left = 0;
            outRect.right = majorItemMarginSpace;
            return;
        }

        if (grid.itemIsInLastColumn(itemPosition)) {
            outRect.left = majorItemMarginSpace;
            outRect.right = 0;
            return;
        }

        if (grid.itemIsNextToItemInFirstColumn(itemPosition)) {
            outRect.left = minorItemMarginSpace;
        } else {
            outRect.left = halfSpace;
        }

        if (grid.itemIsNextToItemInLastColumn(itemPosition)) {
            outRect.right = minorItemMarginSpace;
        } else {
            outRect.right = halfSpace;
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
