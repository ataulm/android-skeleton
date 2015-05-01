package com.ataulm.basic;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        if (spanSizeLookup.getSpanCount() == 1) {
            outRect.top = verticalSpacing / 2;
            outRect.bottom = verticalSpacing / 2;
            return;
        }
        int position = parent.getChildPosition(view);

        outRect.left = horizontalSpacing / 2;
        outRect.top = verticalSpacing / 2;
        outRect.right = horizontalSpacing / 2;
        outRect.bottom = verticalSpacing / 2;

        if (itemIsInFirstColumn(position)) {
            outRect.left = 0;
        }
        if (itemIsInLastColumn(position)) {
            outRect.right = 0;
        }
    }

    private boolean itemIsInFirstColumn(int position) {
        int spanCount = spanSizeLookup.getSpanCount();

        int spanTally = 0;
        for (int i = 0; i <= position; i++) {
            spanTally += spanSizeLookup.getSpanSize(i);
            if (spanTally == spanCount) {
                spanTally = 0;
            }
        }
        return spanTally - spanSizeLookup.getSpanSize(position) == 0;
    }

    private boolean itemIsInLastColumn(int position) {
        int spanCount = spanSizeLookup.getSpanCount();

        int spanTally = 0;
        for (int i = 0; i <= position; i++) {
            spanTally += spanSizeLookup.getSpanSize(i);
            if (spanTally == spanCount) {
                spanTally = 0;
            }
        }
        return spanTally == 0;
    }

    public interface SpanSizeLookup {

        int getSpanSize(int position);

        int getSpanCount();

    }

}
