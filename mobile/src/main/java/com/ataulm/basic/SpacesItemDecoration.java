package com.ataulm.basic;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private enum Column {
        ONLY,
        LEFT,
        LEFT_ADJACENT,
        MIDDLE,
        RIGHT_ADJACENT,
        RIGHT
    }

    private final int horizontalSpacing;
    private final int verticalSpacing;

    private final int spanCount;

    SpacesItemDecoration(int horizontalSpacing, int verticalSpacing) {
        this(horizontalSpacing, verticalSpacing, 1);
    }

    SpacesItemDecoration(int horizontalSpacing, int verticalSpacing, int spanCount) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildPosition(view);
        applyColumnInsets(position, spanCount, outRect, horizontalSpacing);
        outRect.top = verticalSpacing / 2;
        outRect.bottom = verticalSpacing / 2;
    }

    private static void applyColumnInsets(int position, int spanCount, Rect outRect, int horizontalSpacing) {
        Column column = getColumn(position, spanCount);
        int spacingThird = (int) (horizontalSpacing / 3f + 0.5f);
        int spacingHalf = (int) (horizontalSpacing / 2f + 0.5f);

        switch (column) {
            case ONLY:
                outRect.left = 0;
                outRect.right = 0;
                break;
            case LEFT:
                outRect.left = 0;
                outRect.right = 2 * spacingThird;
                break;
            case LEFT_ADJACENT:
                outRect.left = spacingThird;
                outRect.right = spacingHalf;
                break;
            case MIDDLE:
                outRect.left = spacingHalf;
                outRect.right = spacingHalf;
                break;
            case RIGHT_ADJACENT:
                outRect.left = spacingHalf;
                outRect.right = spacingThird;
                break;
            case RIGHT:
                outRect.left = 2 * spacingThird;
                outRect.right = 0;
                break;
            default:
                throw new RuntimeException("Missing case! " + column);
        }
    }

    private static Column getColumn(int position, int spanCount) {
        if (spanCount == 1) {
            return Column.ONLY;
        }


        if (position % spanCount == 0) {
            return Column.LEFT;
        } else if (getColumn(position - 1, spanCount) == Column.LEFT) {
            return Column.LEFT_ADJACENT;
        } else if (getColumn(position + 1, spanCount) == Column.RIGHT) {
            return Column.RIGHT_ADJACENT;
        } else if ((position - (spanCount - 1)) % spanCount == 0) {
            return Column.RIGHT;
        }
        return Column.MIDDLE;
    }

}
