package com.ataulm.basic;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

class ContentViewSetter {

    private final LayoutInflater layoutInflater;
    private final FrameLayout contentFrame;

    ContentViewSetter(LayoutInflater layoutInflater, FrameLayout contentFrame) {
        this.layoutInflater = layoutInflater;
        this.contentFrame = contentFrame;
    }

    void setContentView(@LayoutRes int layout) {
        contentFrame.removeAllViews();
        layoutInflater.inflate(layout, contentFrame, true);
        // TODO: if the `layout` is merge tags, what happens if attachToRoot == false?
    }

}
