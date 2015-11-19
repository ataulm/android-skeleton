package com.ataulm.basic;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class CustomAppBarLayout extends AppBarLayout {

    public CustomAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static class ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {

        public ScrollingViewBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setOverlayTop(int overlayTop) {
            super.setOverlayTop(overlayTop);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            return dependency instanceof CustomAppBarLayout;
        }

    }

}
