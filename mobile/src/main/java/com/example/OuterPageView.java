package com.example;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class OuterPageView extends ViewPager {

    public OuterPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
