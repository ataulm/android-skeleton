package com.ataulm.basic;

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
        int position = parent.getChildPosition(view);

        outRect.left = horizontalSpacing / 2;
        outRect.top = verticalSpacing / 2;
        outRect.right = horizontalSpacing / 2;
        outRect.bottom = verticalSpacing / 2;

        StringBuilder place = new StringBuilder();
        if (itemIsInFirstColumn(position)) {
            outRect.left = 0;
            place.append("fc,");
        }
        if (itemIsInLastColumn(position)) {
            outRect.right = 0;
            place.append("lc,");
        }
        if (itemIsInFirstRow(position)) {
            outRect.top = 0;
            place.append("fr,");
        }
        if (itemIsInLastRow(position, parent.getAdapter().getItemCount())) {
            outRect.bottom = 0;
            place.append("lr,");
        }
        if (place.length() > 0) {
            ((ItemView) view).update(place.substring(0, place.length() - 1));
        } else {
            ((ItemView) view).update("");
        }
    }

    private boolean itemIsInFirstRow(int position) {
        int spanCount = spanSizeLookup.getSpanCount();
        int spanTally = 0;
        for (int i = 0; i <= position; i++) {
            spanTally += spanSizeLookup.getSpanSize(i);
            if (spanTally > spanCount) {
                return false;
            }
        }
        return true;
    }

    private boolean itemIsInLastRow(int position, int itemCount) {
        int spanCount = spanSizeLookup.getSpanCount();

        int rowWithItem = 0;
        int rowCount = itemCount > 0 ? 1 : 0;
        int spansInLatestKnownRow = 0;

        for (int i = 0; i < itemCount; i++) {
            int spanSize = spanSizeLookup.getSpanSize(i);
            if (spansInLatestKnownRow + spanSize > spanCount) {
                rowCount++;
                spansInLatestKnownRow = spanSize;
            } else {
                spansInLatestKnownRow += spanSize;
            }
            if (i == position) {
                rowWithItem = rowCount;
            }
        }
        return rowWithItem == rowCount;
    }

    private boolean itemIsInFirstColumn(int position) {
        int spanCount = spanSizeLookup.getSpanCount();
        int spanTally = 0;
        for (int i = 0; i <= position; i++) {
            if (i == position && spanTally == 0) {
                return true;
            }
            spanTally += spanSizeLookup.getSpanSize(i);
            if (spanTally == spanCount) {
                spanTally = 0;
            }
        }
        return false;
    }

    private boolean itemIsInLastColumn(int position) {
        int spanCount = spanSizeLookup.getSpanCount();
        int spansInLatestKnownRow = 0;
        for (int i = 0; i <= position; i++) {
            spansInLatestKnownRow += spanSizeLookup.getSpanSize(i);
            if (i == position && spansInLatestKnownRow == spanCount) {
                return true;
            }
            if (spansInLatestKnownRow == spanCount) {
                spansInLatestKnownRow = 0;
            }
        }
        return false;
    }

    public interface SpanSizeLookup {

        int getSpanSize(int position);

        int getSpanCount();

    }

}
