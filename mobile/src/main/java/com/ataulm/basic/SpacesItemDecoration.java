package com.ataulm.basic;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class SpacesItemDecoration extends RecyclerView.ItemDecoration {

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
    }

}
