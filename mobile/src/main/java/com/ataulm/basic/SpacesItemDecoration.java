package com.ataulm.basic;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
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
        int position = parent.getChildPosition(view);

        outRect.left = horizontalSpacing / 2;
        outRect.top = verticalSpacing / 2;
        outRect.right = horizontalSpacing / 2;
        outRect.bottom = verticalSpacing / 2;
    }

    public interface SpanSizeLookup {

        int getSpanSize(int position);

        int getSpanCount();

    }

}
